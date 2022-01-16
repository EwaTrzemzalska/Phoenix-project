(ns phoenix.starter
  (:require [reagent.dom :as rdom]
            [phoenix.events :as events]
            [phoenix.subs :as subs]
            [phoenix.state :as state]
            [phoenix.style :as style]
            [phoenix.helpers :as helpers]
            [phoenix.components.charts :as charts]
            [phoenix.components.transactions :as transactions]
            [phoenix.components.forms :as forms]))


(defn app []
  [:div.container
   [forms/new-record-form]
   [:div.is-flex
    [:div
     [transactions/table-of-transactions]]
    [:div
     [charts/chart (state/subscribe :get-aggregated-monthly-amounts-for-chart)]]
    [:div
     [charts/gauge-widget
      {:gauge-description (str "Spent in "
                               (helpers/get-month-as-string (js/Date.))
                               " - your current limit is "
                               subs/monthly-limit
                               " UAH.")
       :id "gauge-widget-for-current-month"
       :number-of-levels 3
       :levels-colors [(:light-blue style/colors)
                       (:blue style/colors)
                       (:dark-blue style/colors)]
       :text-color (:black style/colors)
       :fraction-used (state/subscribe :get-fraction-used)}]]]])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (rdom/render
   [app]
   (.getElementById js/document "app")))

(defn init []
  (state/dispatch :init-db)
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
