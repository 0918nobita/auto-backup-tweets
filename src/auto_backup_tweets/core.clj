(ns auto-backup-tweets.core)

(require '[clj-http.client :as client])

(defn hello [x]
  (println (str "Hello, " x "!")))

(defn -main []
  (-> "https://zenn.dev"
      (client/get)
      (get :headers)
      (get :content-type)
      (println))
  (hello "world"))
