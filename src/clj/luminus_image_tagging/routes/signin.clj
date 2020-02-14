(ns luminus-image-tagging.routes.signin
  (:require [luminus-image-tagging.layout :as layout]
            [luminus-image-tagging.db.core :as db]
            [clojure.java.io :as io]
            [luminus-image-tagging.middleware :as middleware]
            [ring.util.http-response :as response]
            [ring.util.response :as resp]
            [buddy.hashers :as hashers]
            [struct.core :as st]))


(defn render-sign-in [request]
  (layout/render request "signin.html"))

(defn render-reset-password [request]
  (layout/render request "reset-pass.html" {:username (get-in request [:session :identity])}))


(defn login-authenticate
  ""
  [request]
  (let [username (get-in request [:form-params "username"])
        password (get-in request [:form-params "password"])
        session (:session request)
        found-password (:pass (db/get-hashed-user-pw {:email username}))]
    (if (and found-password (hashers/check password found-password))
      (let [next-url (get-in request [:query-params :next] "/")
            updated-session (assoc session :identity username)]
        (-> (resp/redirect "/signin/in")
            (assoc :session updated-session)))
      (-> (resp/redirect "/signin/out")))))

(defn render-sign-out [request]
  (-> (resp/redirect "/debug")
      (assoc :session nil)))


(defn reset-password
  ""
  [request]
  (let [username (get-in request [:session :identity])
        password (get-in request [:form-params "password"])
        session (:session request)]
    (let [next-url (get-in request [:query-params :next] "/")
          updated-session (assoc session :identity username)]
      (db/update-user-pass! {:email username :pass (hashers/encrypt password)})
      (-> (resp/redirect "/signin/in")
          (assoc :session updated-session)))))
