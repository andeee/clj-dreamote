(use 'clj-dreamote.core)
(with-remote {:host "dm600pvr" :auth ["root" "dreambox"]}
  (press 'exit 3)
  (press 1)
  (press 'ok)
  (press 'power))