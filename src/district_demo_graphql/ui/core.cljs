(ns district-demo-graphql.ui.core
  (:require
    [district-demo-graphql.shared.schema :refer [schema]]
    [district-demo-graphql.ui.home-page :refer [home-page middleware-root-value]]
    [district.ui.graphql.middleware.resolver :refer [create-resolver-middleware]]
    [district.ui.graphql]
    [district.ui.reagent-render]
    [mount.core :as mount]
    [print.foo :include-macros true]))

(defn ^:export init []
  (enable-console-print!)
  (-> (mount/with-args
        {:reagent-render {:id "app"
                          :component-var #'home-page}
         :graphql {:schema schema
                   :url "http://localhost:6450/graphql"
                   :query-middlewares [(create-resolver-middleware :my-resolver {:root-value middleware-root-value})]}})
    (mount/start)))