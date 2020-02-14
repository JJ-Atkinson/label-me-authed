(ns luminus-image-tagging.routes.home
  (:require
    [luminus-image-tagging.layout :as layout]
    [luminus-image-tagging.db.core :as db]
    [clojure.java.io :as io]
    [luminus-image-tagging.middleware :as middleware]
    [ring.util.response]
    [luminus-image-tagging.routes.signin :as signin]
    [luminus-image-tagging.routes.messages :as messages]
    [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html"))


(defn plain-text [text request]
  (-> (response/ok text)
      (response/header "Content-Type" "text/plain; charset=utf-8")))

(defn debug [request]
  (-> (response/ok (-> request :session :identity))
      (response/header "Content-Type" "text/plain; charset=utf-8")))

(defn home-routes []
  [""
   {:middleware [
                 middleware/wrap-formats]}
   ["/" {:get home-page}]

   ["/signin" {:get  signin/render-sign-in
               :post signin/login-authenticate}]

   ["/signin/in" {:get (partial plain-text "success")}]
   ["/signin/out" {:get (partial plain-text "failure")}]
   ["/signout" {:get signin/render-sign-out}]
   ["/resetpass" {:middleware [middleware/wrap-restricted]
                  :get        signin/render-reset-password
                  :post       signin/reset-password}]

   ["/debug" {:get debug}]

   ["/auth-only" {:middleware [middleware/wrap-restricted]
                  :get        (fn [_]
                                (-> (response/ok "authed")
                                    (response/header "Content-Type" "text/plain; charset=utf-8")))}]
   ["/docs" {:get (fn [_]
                    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]])

