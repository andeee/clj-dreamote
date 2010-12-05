(use 'clj-dreamote.core)
(with-remote {:host "dm600pvr" :auth ["root" "dreambox"]}
  (press 'power)
  (press 'blue 2)
  (press 'down 4)
  (press 'ok)
  (press 'down)
  (press 'ok 2))