;; Copyright © 2015, JUXT LTD.

(ns yada.content
  (:require
   [hiccup.core :refer (html)]))

(defprotocol Content
  (representation [_ content-type]))

(defmulti render-map (fn [content content-type] content-type))

(extend-protocol Content
  java.util.Map
  (representation [content content-type] (render-map content content-type)))

(defmethod render-map "text/html"
  [m _]
  (let [style {:style "border: 1px solid black; border-collapse: collapse"}]
    (html
     [:html
      [:body
       (cond
         (map? (second (first m)))
         (let [ks (keys (second (first m)))
               ]
           [:table
            [:thead
             [:tr
              [:th style "id"]
              (for [k ks] [:th style k])
              ]]
            [:tbody
             (for [[k v] (sort m)]
               [:tr
                [:td style k]
                (for [k ks]
                  [:td style (get v k)])])]])
         :otherwise
         [:table
          [:thead
           [:tr
            [:th "Name"]
            [:th "Value"]
            ]]
          [:tbody
           (for [[k v] m]
             [:tr
              [:td style k]
              [:td style v]
              ])]])
       [:p "This is a default rendering to assist development"]]])))
