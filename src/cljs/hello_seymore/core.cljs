(ns hello-seymore.core)

(js/console.log "hello world")

(def main-body-html
  "<br>
  <div class='center-block'>
  <img class='launch-div center-block' src='/images/top-level-chess-logo-xsmall.gif'  alt='Mountain View'>
  <br>
  <p>We are very excited to start the second year of our Chess Program at Town Centre Montessori. Top Level Chess is run by renowned International Master (IM) Artiom Samsonkin, an experienced chess teacher, and one of Canada's Top Ten rated players. For the first time, your chess program has taken full advantage of the latest in technology, including a dedicated website with chess videos that reinforce the lessons learned in class. Every class uses a smart board and/or video projector to make your chess education more dynamic and fun! And for the first time, intermediate/advanced students will have their tournaments rated.</p>
  <p> We are pleased to have you join us for a year of chess that will bring out the champion in you. Our goal at Top Level Chess is to give you the latest chess tools to reach your personal best. When you are reaching your personal best you can't lose! We look forward to our inaugural year at Town Centre Montessori.</p>
  <p>If you have questions about our program please e-mail us at toplevelchess64 @gmail.com</p>

  <a href='#menu-toggle' class='btn btn-default' id='menu-toggle'>Toggle Menu</a>
  </div>")

(def semester-list
  "<br><h3 class='active-semester btn btn-lg btn-primary btn-block' >Semester 1<br>
  <h3 class='semester-two btn btn-lg btn-primary btn-block'>Semester 2 </h3>
  <h3 class='semester-three btn btn-lg btn-primary btn-block'>Semester 3</h3>")

(defn title-html
  [id title]
  (str "<span id=" id "><a><h3>" title "</h3></a><span></span></span><br>"))

(defn video-html
  [url]
  (js/console.log (str "The url " url))
  (str "<span><br><iframe width='560' height='315' src='" url "' frameborder='1' allowfullscreen></iframe><br></span>"))

(defn vid-id
  [id]
  (str "vid_id_" id))

(def hide-div (. js/document getElementById "hide-div"))

(def video-view (. js/document getElementById "video-view"))

(defn enable-toggle-menu-button!
  []
  (let [toggle-menu-button (. js/document getElementById "menu-toggle")]
    (aset toggle-menu-button "onclick" (fn [e]
                                         (.preventDefault e)
                                         (let [wrapper-elem (. js/document getElementById "wrapper")
                                               class-list (.-classList wrapper-elem)]
                                           (.toggle class-list "toggled")
                                           (js/console.log "menu is toggled"))))))

(aset hide-div  "innerHTML" main-body-html)
(enable-toggle-menu-button!)

#_(enable-toggle-menu-button!)

(defn empty-hide-div!
  []
  (while (.-firstChild hide-div)
    (.removeChild hide-div (.-firstChild hide-div))))

(defn empty-video-view!
  []
  (while (.-firstChild video-view)
    (.removeChild video-view (.-firstChild video-view))))

(def videos-list (atom nil))

(defn foo
  [e]
  (let [title (aget e "title")
        id (vid-id (aget e "id"))]
    (title-html id title)))

(add-watch videos-list :watcher
  (fn [key atom old-state new-state]
    (if (empty? new-state)
      (aset video-view "innerHTML" "No videos in this section :(")
      (let [html (reduce #(str %1 (foo %2)) "" new-state)]
        (aset video-view "innerHTML" html)
        (doseq [video new-state]
          (let [id (vid-id (aget video "id"))
                _ (js/console.log id)
                url (aget video "url")
                title-elem (. js/document getElementById id)]
            (aset title-elem "onclick" (fn [e]
                                         (.preventDefault e)
                                         (let [span-child (aget (.-children title-elem) "1")]
                                           (def s span-child)
                                           (aset span-child "innerHTML" (video-html url)))))))))))

(defn video-request!
  [level semester]
  (let [req (js/XMLHttpRequest.)]
    (.open req "GET" (str "/videos/" level "/" semester))
    (aset req "onload" (fn [] (reset! videos-list (.parse js/JSON (.-response req)))))
    (.send req)))

;(def xml-request (js/XMLHttpRequest.))
;
;(.open xml-request "GET" " http://localhost:5000/videos" true)
;
;(aset xml-request "onload" (fn [] (def resp xml-request)))
;(.send xml-request)

(defn enable-ajax-request-for-level!
  [level semester class-tag]
  (js/console.log "okgo!")
  (let [item (aget (. js/document getElementsByClassName class-tag) "0")]
    (def b item)
    (aset item "onclick" (fn [e]
                           (.preventDefault e)
                           (video-request! level semester)))))

(defn enable-beginner-ajax-request!
  []
  (enable-ajax-request-for-level! 1 1 "active-semester")
  (enable-ajax-request-for-level! 1 2 "semester-two")
  (enable-ajax-request-for-level! 1 3 "semester-three"))

(defn enable-intermediate-ajax-request!
  []
  (enable-ajax-request-for-level! 2 1 "active-semester")
  (enable-ajax-request-for-level! 2 2 "semester-two")
  (enable-ajax-request-for-level! 2 3 "semester-three"))

(defn enable-advanced-ajax-request!
  []
  (enable-ajax-request-for-level! 3 1 "active-semester")
  (enable-ajax-request-for-level! 3 2 "semester-two")
  (enable-ajax-request-for-level! 3 3 "semester-three"))

(-> (. js/document getElementById "beginner")
    (aset "onclick" (fn [e]
                      (.preventDefault e)
                      (empty-hide-div!)
                      (empty-video-view!)
                      (let [elem (. js/document getElementById "hide-div")]
                        (set! (.-innerHTML elem) (str "<h3>Beginner</h3>" semester-list)))
                      (enable-beginner-ajax-request!))))

(-> (. js/document getElementById "intermediate")
    (aset "onclick" (fn [e]
                      (.preventDefault e)
                      (empty-hide-div!)
                      (empty-video-view!)
                      (let [elem (. js/document getElementById "hide-div")]
                        (set! (.-innerHTML elem) (str "<h3>Intermediate</h3>" semester-list)))
                      (enable-intermediate-ajax-request!))))

(-> (. js/document getElementById "advanced")
    (aset "onclick" (fn [e]
                      (.preventDefault e)
                      (empty-hide-div!)
                      (empty-video-view!)
                      (let [elem (. js/document getElementById "hide-div")]
                        (set! (.-innerHTML elem) (str "<h3>Advanced</h3>" semester-list)))
                      (enable-advanced-ajax-request!))))

(-> (. js/document getElementById "home-text")
    (aset "onclick" (fn [e]
                      (.preventDefault e)
                      (empty-hide-div!)
                      (empty-video-view!)
                      (aset hide-div  "innerHTML" main-body-html)
                      (enable-toggle-menu-button!))))

(js/console.log "ok")