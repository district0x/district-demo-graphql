(ns district-demo-graphql.ui.home-page
  (:require
    [cljs-time.format :as time-format]
    [district.ui.graphql.subs :as gql]
    [re-frame.core :refer [subscribe]]))


(def middleware-root-value
  {:user (fn [{:keys [:user/id]}]
           {:user/address "Street 456"
            :user/premium-member? (fn []
                                    (js/Promise. (fn [resolve]
                                                   (resolve false))))
            :user/cart-items (fn []
                               [{:cart-item/item (fn []
                                                   {:item/price
                                                    (fn []
                                                      (js/Promise. (fn [resolve]
                                                                     (resolve 456.789))))})}])})
   :search-items (fn [{:keys [:keyword :item/status]}]
                   [{:item/description (fn []
                                         (js/Promise. (fn [resolve]
                                                        (resolve "Other Item Description"))))}])})


(defn- format-date [dt]
  (when dt
    (time-format/unparse (time-format/formatters :rfc822) dt)))


(defn user-info []
  (let [query (subscribe [::gql/query
                          {:queries [[:user
                                      {:user/id "abc"}
                                      [:user/address
                                       :user/registered-on
                                       :user/premium-member?
                                       [:user/cart-items [:cart-item/quantity
                                                          [:cart-item/item [:item/title
                                                                            :item/price]]]]]]]}])]
    (fn []
      (if (:graphql/loading? @query)
        [:div "Loading..."]
        (let [{:keys [:user/address :user/registered-on :user/premium-member? :user/cart-items]} (:user @query)]
          [:div
           [:div "User Information:"]
           [:div "Address: " address]
           (when registered-on
             [:div "Registered On: " (format-date registered-on)])
           [:div "Premium Member?: " premium-member?]
           [:div "Cart Items:"]
           (for [{:keys [:cart-item/item :cart-item/quantity]} cart-items]
             (let [{:keys [:item/title :item/price]} item]
               [:div {:key title}
                title ": $" (* quantity price)]))])))))


(defn searched-items []
  (let [query (subscribe [::gql/query
                          {:queries [[:search-items
                                      {:keyword "Grass" :item/status :item.status/active}
                                      [:item/id
                                       :item/title
                                       :item/description
                                       :item/status
                                       :item/price]]]}])]
    (fn []
      [:div
       [:div "Search Results:"]
       (if-not (:graphql/errors @query)
         (for [{:keys [:item/id :item/title :item/description :item/status :item/price]} (:search-items @query)]
           [:div {:key id}
            [:div "Item: " title]
            [:div description]
            [:div "Status: " status]
            [:div "Price: $" price]])
         [:div "Errors during loading: " (:graphql/errors @query)])])))

(defn home-page []
  [:div
   [user-info]
   [:p]
   [searched-items]])