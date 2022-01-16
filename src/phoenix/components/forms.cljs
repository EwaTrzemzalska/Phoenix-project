(ns phoenix.components.forms
  (:require
   [phoenix.state :as state]))


(defn new-record-form []
  [:form
   {:class "field is-grouped"
    :on-submit (fn [e]
                 (.preventDefault e)
                 (let [amount (state/subscribe :get-amount-from-new-record)
                       name (state/subscribe :get-name-from-new-record)]
                   (when (and amount name)
                     (state/dispatch :add-record
                                     {:amount amount
                                      :name name})
                     (state/dispatch :clear-new-record))))}
   [:div.control
    [:label {:for "name"
             :class "label"}
     "Enter your name"]
    [:input {:type "text"
             :name "name"
             :id "name"
             :className "input"
             :required true
             :placeholder "John Doe"
             :value (state/subscribe :get-name-from-new-record)
             :on-change #(state/dispatch
                          :assoc-name (-> %
                                          .-target
                                          .-value))}]]
   [:div.control
    [:label {:for "amount"
             :className "label"}
     "Enter the amount"]
    [:input {:type "number"
             :name "amount"
             :id "amount"
             :class "input"
             :required true
             :placeholder 199
             :value (state/subscribe :get-amount-from-new-record)
             :on-change #(state/dispatch :assoc-amount (-> %
                                                           .-target
                                                           .-value))}]]
   [:div.field.is-grouped.is-align-items-end
    [:div.control
     [:button {:class "button is-link"}
      "Add record"]]]])
