(ns auto-backup-tweets.test
  (:require [clojure.test :refer [deftest is testing run-tests]]
            [clj-http.client]
            [auto-backup-tweets.core :as abtc]))

(deftest twitter-api
  (testing "Fetching access token"
    (with-redefs [clj-http.client/post
                  (fn [_ _] {:body "{\"access_token\": \"this-is-access-token\"}"})]
      (is (= "this-is-access-token"
             (abtc/fetch-access-token "consumer-key" "consumer-secret"))))))

(defn -main []
  (run-tests 'auto-backup-tweets.test))
