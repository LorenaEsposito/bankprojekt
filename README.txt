********************************************************************************
README
********************************************************************************

In dieser Datei finden Sie einige wichtige Hinweise, damit Sie lästige Probleme
beseitigen können bzw. diese erst gar nicht auftreten.

1) Umlaute und internationale Zeichen wie Ä,Ö,Ü,ä,ö,ü,ß im Browser richtig 
darstellen.

GWT erwartet die Zeichenkodierung des ausgelieferten Kompilats im Format UTF-8.
Die Entwicklungsumgebung (z.B. Eclipse) speichert den Source Code in der Regel
nicht in diesem Format. Bei Eclipse k�nnen Sie das aber einstellen.

a) Wählen Sie im Menü "Project" den Eintag "Properties" aus. In dem erscheinenden
Dialog wählen Sie links oben "Resource" aus. Dort finden Sie dann einen Abschnitt 
mit der Überschrift "Text file encoding". Wählen Sie dort in der Klappliste 
"Other" den Wert "UTF-8" aus und drücken Sie "Apply" und schließlich "Ok". 
Eclipse konvertiert daraufhin Ihre Projektdateien in das neue Format. Sollten 
Sie zuvor schon Umlaute verwendet haben, so werden diese evtl. nun mit 
merkwürdigen Symbolen dargestellt. Dies müssen Sie dann leider manuell 
überarbeiten.

b) Achten Sie darauf, dass die HTML-Datei, mit der Sie Ihren Web Client starten
folgende Meta Tag zu Beginn aufweist:

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

Wichtig ist hier das Attribut content="text/html; charset=UTF-8". Dadurch stellen 
Sie sicher, dass die Zeichenkodierung im Browser auf jeden Fall auch (wie Ihre 
Entwicklungsumgebung) mittels UTF-8 erfolgt.

