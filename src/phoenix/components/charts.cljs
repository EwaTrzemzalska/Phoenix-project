(ns phoenix.components.charts
  (:require
   [clojure.string :as string]
   [phoenix.style :as style]
   [phoenix.state :as state]
   [phoenix.helpers :as helpers]
   ["react-vis" :as rvis]
   ["react-gauge-chart" :default GaugeChart]))


(defn chart [data]
  [:<>
   [:> rvis/XYPlot {:height 300
                    :width 400
                    :margin {:left 100 :right 50 :top 50}
                    :xType "ordinal"}
    [:> rvis/XAxis]
    [:> rvis/YAxis]
    [:> rvis/VerticalBarSeries {:data (clj->js data)
                                :color (:dark-blue style/colors)
                                :strokeWidth 4
                                :style {:strokeLinejoin "round"
                                        :strokeLinecap "round"}}]]])

(defn gauge-widget [{:keys [gauge-description id number-of-levels levels-colors text-color fraction-used]}]
  [:div.black
   gauge-description
   [:> GaugeChart {:id id
                   :nrOfLevels number-of-levels
                   :colors levels-colors
                   :textColor text-color
                   :animate false
                   :percent fraction-used}]])
