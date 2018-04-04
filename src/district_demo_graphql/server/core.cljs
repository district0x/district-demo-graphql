(ns district-demo-graphql.server.core
  (:require
    [cljs-time.core :as t]
    [cljs.pprint :as pprint]
    [district-demo-graphql.shared.schema :refer [schema]]
    [district.server.graphql :as graphql]
    [mount.core :as mount]
    [print.foo :include-macros true]))

(def root-value
  {:user (fn [{:keys [:user/id]}]
           {:user/id "abc"
            :user/address "Street 123"
            :user/registered-on (t/date-time 2017 8 9)
            :user/age 21
            :user/premium-member? true
            :user/cart-items (fn []
                               [{:cart-item/quantity 2
                                 :cart-item/item (fn []
                                                   {:item/title "Some Item"
                                                    :item/price 123.456})}])})
   :search-items (fn [{:keys [:keyword :item/status]}]
                   [{:item/id "xyz"
                     :item/title "Some Item"
                     :item/description "Some Item Description"
                     :item/status :item.status/active
                     :item/price 123.456}])})

(defn on-jsload []
  (graphql/restart {:root-value root-value :schema schema}))

(defn -main [& _]
  (-> (mount/with-args
        {:graphql {:port 6450
                   :schema schema
                   :root-value root-value
                   :path "/graphql"
                   :graphiql true}})
    (mount/start)
    pprint/pprint))

(set! *main-cli-fn* -main)