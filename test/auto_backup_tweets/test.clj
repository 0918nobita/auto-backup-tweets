(ns auto-backup-tweets.test
  (:require [clojure.test :refer [deftest is testing run-tests]]
            [clj-http.client :as client]
            [auto-backup-tweets.core :as abtc]))

(def consumer-key "consumer-key")
(def consumer-secret "consumer-secret")

(deftest twitter-api
  (testing "Fetching access token"
    (with-redefs [client/post
                  (fn [_ _] {:body "{\"access_token\": \"this-is-access-token\"}"})]
      (is (= "this-is-access-token"
             (abtc/fetch-access-token consumer-key consumer-secret)))))

  (testing "Fetching specified tweet"
    (with-redefs [client/get
                  (fn [_ _] {:body "{\"text\": \"test tweet\"}"})]
      (is (= "test tweet" (abtc/fetch-tweet consumer-key consumer-secret))))))

(defn -main []
  (run-tests 'auto-backup-tweets.test))
