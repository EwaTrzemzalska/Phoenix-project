(ns phoenix.state
  (:require [reagent.core :as r]))

(def !handler-registry (atom {}))
(def !subscription-registry (atom {}))
(def !state (r/atom {}))

(defn register-handler
  "Registers a handler for future use with dispatch function."
  [handler-name handler]
  (swap! !handler-registry assoc handler-name handler)
  nil)

(defn dispatch
  "Calls previously registered handler with the current state 
   as a first argument, and the rest of args provided to dispatch.
   The result of calling the handler function will be set as the new app state."
  [handler-name & args]
  (let [handler (get @!handler-registry handler-name)]
    (if handler
      (reset! !state (apply handler @!state args))
      (js/console.warn "The handler " handler-name " doesn't exist."))
    nil))

(defn register-subscription
  "Registers a subscribe-fn for future use with subscribe function."
  [subscription-name subscribe-fn]
  (swap! !subscription-registry assoc subscription-name subscribe-fn)
  nil)

(defn subscribe 
  "Calls previously registered subscribe-fn with the current state
   as a first argument, and the rest of args provided to subscribe.
   Returns the result to the caller."
  [subscription-name & args]
  (let [subscribe-fn (get @!subscription-registry subscription-name)]
    (if subscribe-fn
      (apply subscribe-fn @!state args)
      (js/console.warn "This subscription " subscription-name " doesn't exist"))))
