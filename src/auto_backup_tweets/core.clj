(ns auto-backup-tweets.core
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [ring.util.codec :refer [url-encode]]
            [cheshire.core :refer [parse-string]]))

(defn- fetch-access-token [consumer_key consumer_secret]
  (-> "https://api.twitter.com/oauth2/token"
      (client/post
       {:basic-auth (map url-encode [consumer_key consumer_secret])
        :content-type "application/x-www-form-urlencoded;charset=UTF-8"
        :form-params {"grant_type" "client_credentials"}
        :cookie-policy :none
        :throw-entire-message? true})))

(defn- fetch-tweet [access-token tweet-id]
  (-> "https://api.twitter.com/1.1/statuses/show.json"
      (client/get {:oauth-token access-token
                   :query-params {"id" tweet-id}
                   :cookie-policy :none
                   :throw-entire-message? true})
      (get :body)
      (parse-string)
      (get "text")))

(defn -main []
  (let [consumer-key    (env :twitter-consumer-key)
        consumer-secret (env :twitter-consumer-secret)
        access-token    (-> (fetch-access-token consumer-key consumer-secret)
                            (get :body)
                            (parse-string)
                            (get "access_token"))]
    (println (fetch-tweet access-token "1448546361582915587"))))
