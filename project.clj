(defproject quick-clj-repl "0.1.0-SNAPSHOT"
  :description "quick clj repl"
  :url "https://example.com"
  :min-lein-version "2.5.0"

  :aliases {"kaocha" ["with-profile" "+dev" "run" "-m" "kaocha.runner"]
            "test" ["version"]}

  :exclusions [org.slf4j/slf4j-simple
               org.slf4j/simple
               commons-logging
               log4j
               org.apache.logging.log4j/log4j
               org.apache.logging.log4j/log4j-api
               org.apache.logging.log4j/log4j-core
               org.apache.logging.log4j/log4j-slf4j-impl
               org.slf4j/slf4j-api
               org.slf4j/slf4j-jcl
               org.slf4j/slf4j-nop
               org.slf4j/slf4j-log4j12
               org.slf4j/slf4j-log4j13]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/core.async "1.2.603"]

                 [org.clojure/spec.alpha "0.2.187"]
                 [org.clojure/tools.cli "1.0.194"]
                 [metosin/malli "0.0.1-SNAPSHOT"]

                 [philoskim/debux-stubs "0.7.5"]
                 [bb-utils "0.1.0-SNAPSHOT"]
                 [hashp "0.2.0"]

                 ;; logging
                 [org.clojure/tools.logging "1.1.0"]
                 [org.slf4j/slf4j-api "1.7.30"]
                 [org.slf4j/jul-to-slf4j "1.7.30"]
                 [org.slf4j/jcl-over-slf4j "1.7.30"]
                 [org.slf4j/log4j-over-slf4j "1.7.30"]
                 [org.slf4j/osgi-over-slf4j "1.7.30"]
                 [ch.qos.logback/logback-classic "1.2.3"]

                 [org.clojure/java.jdbc  "0.7.11"]
                 [toucan "1.15.1"
                  :exclusions [org.clojure/java.jdbc honeysql]]
                 [honeysql
                  "1.0.444"
                  :exclusions [org.clojure/clojurescript]]
                 [mysql/mysql-connector-java "8.0.21"]
                 [org.xerial/sqlite-jdbc "3.32.3.1"]

                 [io.pedestal/pedestal.service  "0.5.8"]
                 [io.pedestal/pedestal.route    "0.5.8"]
                 [io.pedestal/pedestal.jetty    "0.5.8"]
                 [prone "2020-01-17"]

                 [datascript "1.0.0"]
                 [potemkin "0.4.5"]
                 [methodical "0.10.0-alpha"]
                 [semantic-csv "0.2.1-alpha1"]
                 [metosin/muuntaja "0.6.7"]
                 [metosin/jsonista "0.2.6"]
                 ;; Have to incldue jackson deps explicitly otherwise
                 ;; jsonista would error when building uberjar.
                 [com.fasterxml.jackson.core/jackson-databind "2.10.3"]
                 [com.fasterxml.jackson.core/jackson-core "2.10.3"]
                 [com.cognitect/transit-clj "1.0.324"]
                 [cheshire  "5.10.0"]
                 [clj-http "3.10.1"]
                 [kotyo/clj-rocksdb "0.1.6"]
                 [cljc.java-time "0.1.11"]
                 [tick "0.4.26-alpha"]
                 [slingshot "0.12.2"]
                 [kixi/stats "0.5.4"]
                 [org.clojure/math.combinatorics "0.1.6"]                           ; combinatorics functions
                 [org.clojure/math.numeric-tower "0.0.4"]                           ; math functions like `ceil`
                 [com.taoensso/carmine "2.20.0-RC1"]
                 [buddy/buddy-core "1.6.0"
                  :exclusions [commons-codec]]
                 [buddy/buddy-sign "3.1.0"]
                 [commons-codec/commons-codec "1.14"]
                 [commons-io/commons-io "2.7"] ; Apache Commons -- useful IO util fns
                 [commons-validator/commons-validator "1.6" ; Apache Commons -- useful validation util fns
                  :exclusions [commons-beanutils
                               commons-digester
                               commons-logging]]
                 [me.raynes/fs                  "1.4.6"]
                 [cprop "0.1.17" :exclusions [org.clojure/clojure]]
                 [byte-streams "0.2.5-alpha2"]
                 [com.taoensso/nippy "2.14.0"]
                 [funcool/cuerdas "2020.03.26-3"]
                 [medley             "1.3.0"]
                 [redux              "0.1.4"]
                 [hiccup             "1.0.5"]
                 [zprint             "1.0.0"]
                 [com.rpl/specter    "1.1.3"]
                 [slingshot "0.12.2"]
                 [lambdaisland/uri "1.4.54"]]

  :injections [(require 'hashp.core)
               (require 'debux.core)]
  :resource-paths ["config", "resources"]
  :main quick-clj-repl.core

  :jvm-opts
  [;; ignore things not recognized for our Java version instead of refusing to start
   "-XX:+IgnoreUnrecognizedVMOptions"
   ;; disable bytecode verification when running in dev so it starts slightly faster
   "-Xverify:none"]
  :target-path "target/%s"
  :profiles {:dev {
                   :repl-options {:port 6667
                                  :init-ns user}

                   :jvm-opts
                   ["-XX:+UnlockDiagnosticVMOptions" "-XX:+DebugNonSafepoints"]

                   :source-paths ["dev/src" "local/src"]
                   :dependencies [[org.clojure/clojurescript "1.10.773"]
                                  [philoskim/debux "0.7.5"]
                                  [org.clojure/test.check "1.1.0"]
                                  [expectations/clojure-test "1.2.1"]
                                  [lambdaisland/kaocha "1.0.641"]
                                  [nubank/matcher-combinators "3.1.1"
                                   :exclusions [midje]]
                                  [kaocha-noyoda "2019-06-03"]
                                  [hickory "0.7.1"]
                                  [stylefruits/gniazdo "1.1.4"
                                   :exclusions
                                   [org.clojure/clojure
                                    org.eclipse.jetty.websocket/websocket-client]]
                                  [postmortem "0.4.0"]
                                  [com.clojure-goes-fast/clj-async-profiler "0.4.1"]]}

             :dbg {:jvm-opts ["-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"]}

             :repl {:dependencies [[me.raynes/fs "1.4.6"]
                                   [compliment "0.3.10"]
                                   [clj-commons/pomegranate "1.2.0"]
                                   [org.tcrawley/dynapath "1.1.0"]
                                   [refactor-nrepl "2.5.0"]
                                   [cider/cider-nrepl "0.25.3-SNAPSHOT"]

                                   [techascent/tech.ml.dataset "3.06"]
                                   [techascent/tech.jna "3.23"]
                                   [metasoarous/oz "1.6.0-alpha11"]
                                   [aysylu/loom "1.0.2"]
                                   [clj-python/libpython-clj "1.45"]]


                    :plugins [[cider/cider-nrepl "0.25.3-SNAPSHOT"]
                              [refactor-nrepl "2.5.0"]
                              [lein-cljfmt "0.6.7"]]}
             :uberjar {:aot :all}})
