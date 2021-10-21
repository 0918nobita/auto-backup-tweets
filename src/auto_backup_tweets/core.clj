(ns auto-backup-tweets.core
  (:require [clj-http.client :as client]
            [environ.core :refer [env]]
            [ring.util.codec :refer [url-encode]]
            [cheshire.core :refer [parse-string]]))

(def http-base-config
  {:cookie-policy :none
   :throw-entire-message? true})

(defn- fetch-access-token [consumer_key consumer_secret]
  (println "Fetching access token ...")
  (-> "https://api.twitter.com/oauth2/token"
      (client/post
       (merge
        http-base-config
        {:basic-auth (map url-encode [consumer_key consumer_secret])
         :content-type "application/x-www-form-urlencoded;charset=UTF-8"
         :form-params {"grant_type" "client_credentials"}}))
      (get :body)
      (parse-string)
      (get "access_token")))

(defn- fetch-tweet [access-token tweet-id]
  (println "Fetching specified tweet ...")
  (-> "https://api.twitter.com/1.1/statuses/show.json"
      (client/get
       (merge
        http-base-config
        {:oauth-token access-token
         :query-params {"id" tweet-id}}))
      (get :body)
      (parse-string)
      (get "text")))

(defn- fetch-user-timeline [access-token screen-name]
  (println "Fetching user timeline ...")
  (let [res-body (-> "https://api.twitter.com/1.1/statuses/user_timeline.json"
                     (client/get
                      (merge
                       http-base-config
                       {:oauth-token access-token
                        :query-params {"screen_name" screen-name
                                       "include_rts" false
                                       "count" 200}}))
                     (get :body)
                     (parse-string))]
    (map #(get % "text") res-body)))

(defn -main []
  (let [consumer-key     (env :twitter-consumer-key)
        consumer-secret  (env :twitter-consumer-secret)
        access-token     (fetch-access-token consumer-key consumer-secret)
        fetched-tweet    (future (fetch-tweet access-token "1448546361582915587"))
        fetched-timeline (future (fetch-user-timeline access-token "0918nobita"))]
    (println @fetched-tweet)
    (doseq [tweet @fetched-timeline]
      (println tweet))
    (shutdown-agents)))
