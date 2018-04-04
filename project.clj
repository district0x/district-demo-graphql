(defproject district0x/district-demo-graphql "1.0.0"
  :description "Demo application demonstrating district0x GraphQL modules"
  :url "https://github.com/district0x/district-demo-graphql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.andrewmcveigh/cljs-time "0.5.2"]
                 [district0x/district-format "1.0.0"]
                 [district0x/district-server-graphql "1.0.11"]
                 [district0x/district-ui-graphql "1.0.0"]
                 [district0x/district-ui-reagent-render "1.0.1"]
                 [mount "0.1.12"]
                 [org.clojure/clojurescript "1.10.238"]
                 [print-foo-cljs "2.0.3"]]

  :plugins [[lein-auto "0.1.2"]
            [lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.14"]
            [lein-shell "0.5.0"]
            [lein-doo "0.1.8"]
            [lein-npm "0.6.2"]
            [lein-pdo "0.1.1"]]

  :source-paths ["src"]

  :figwheel {:server-port 4629
             :css-dirs ["resources/public/css"]}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.9.0"]
                                  [binaryage/devtools "0.9.9"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [figwheel-sidecar "0.5.14" :exclusions [org.clojure/core.async]]
                                  [org.clojure/tools.reader "1.2.1"]]
                   :source-paths ["dev" "src"]
                   :resource-paths ["resources"]}}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/district_demo_graphql/ui" "src/district_demo_graphql/shared"]
                        :figwheel {:on-jsload "district.ui.reagent-render/rerender"}
                        :compiler {:main "district-demo-graphql.ui.core"
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true
                                   :preloads [print.foo.preloads.devtools]
                                   :closure-defines {goog.DEBUG true}
                                   :external-config {:devtools/config {:features-to-install :all}}}}
                       {:id "server"
                        :source-paths ["src/district_demo_graphql/server" "src/district_demo_graphql/shared"]
                        :figwheel {:on-jsload "district-demo-graphql.server.core/on-jsload"}
                        :compiler {:main "district-demo-graphql.server.core"
                                   :output-to "server/district-demo-graphql.js"
                                   :output-dir "server"
                                   :target :nodejs
                                   :optimizations :none
                                   :closure-defines {goog.DEBUG true}
                                   :source-map true}}
                       {:id "ui"
                        :source-paths ["src"]
                        :compiler {:main "district-demo-graphql.ui.core"
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false
                                   :pseudo-names false}}]})
