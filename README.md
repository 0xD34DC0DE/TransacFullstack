# TransacFullstack

Cette application Fullstack a comme but principal de déservir des passeports de test covid ou de vaccination sous la forme d'un code QR à la population québécoise.

Le projet contient 4 applications d'ont l'une est une application administrative.

La première application ne sert que d'un proxy (simulation) de la base de données gouvernementalede la santé.
Cette application permet l'interaction avec une base de données qui contient les informations des résidents du Québec ainsi que leur autorisation pour l'obtention d'un passport
test/vaccin

La deuxième est l'application principale. Cette dernière est responsable de gérer les comptes usagers, les passeports et l'envoi/création des codes QR.
C'est cette application qui gère les données pour l'application front-end et gère la communication avec la première application pour les vérifications d'authorisation des
passeport.

La troisièrme application est celle qui est présenté aux usagers. Elle fournit un accès web facile à utiliser pour permettre l'obtention des passeport et la gestion du compte.
Cette application à aussi un deuxième rôle, car l'application présente une page web qui est stocké dans les codes QR. C'est à dire: les codes QR contiennent un URL qui pointe vers
une page web et cette page web permet de vérifié l'état de validité d'un passeport. Cette technique permet un niveau plus grand d'accessibilité à l'information d'un permis, car 
n'importe qui doté d'un téléphone intelligent peut scanner le code QR et vérifier ce dernier sans avoir besoin d'une application dédié.

Finalement la quatrième application en est une d'administrion. Elle présente les quatres fonctions courament utiliser dans les bases de données (Création, Mise a jour,
Suppression et recherche) pour les comptes et les passeport. Cette application présent aussi une interface web. Cela permet d'éliminer la création d'une autre application dédié
 à cette fin).
 
 
 Finalement, un cinquième application, en relation avec les trois première application présentées, à été créé pour fournir un expérience plus fluide pour les utilisateurs mobiles.
 Cependant cette application ne faisant pas partie du contexte de ce projet. Est situé dans un autre projet.
 Cette application est disponible au liens suivant: http://github.com/0xD34DC0DE/MobileFinal
