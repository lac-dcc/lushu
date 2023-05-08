# Manual observations of the behavior of Lushu

## Postgresql

### Grammar

Here is the grammar, after ten thousand lines of PG logs:

```
R0 :: [023]{4,4}[-]{1,1}[05]{2,2}[-]{1,1}[06]{2,2} | R1
R1 :: [19]{2,2}[:]{1,1}[06]{2,2}[:]{1,1}[013456]{2,2}[.]{1,1}[0123456789]{3,3} | R2
R2 :: [-]{1,1}[03]{2,2} | R3
R3 :: [\[]{1,1}[0123456789]{6,6}[\]]{1,1} | R4
R4 :: [egoprst]{8,8}[@]{1,1}[hlsu]{5,5}[_]{1,1}[bdest]{6,6} | [GLO]{3,3}[:]{1,1} | [\[]{1,1}[knouw]{7,7}[@\[\]]{3,3}[knouw]{7,7}[\]]{1,1} | [egoprst]{8,8}[@]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | R5
R5 :: [GLO]{3,3}[:]{1,1} |  | R6
R6 ::  | [abdegilnrst]{8,9} | R7
R7 :: [acdeimnorstu]{8,13}[:]{1,1} | [LPQScegimnorsty]{2,10} | R8
R8 :: [0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | [0123456789]{1,2}[.]{1,1}[0123456789]{1,3} | [BDEGINacdehiortuvz]{3,10}[:;]{1,1} |  | R9
R9 :: [INOTabcdehklmnorstuy]{2,8} | [bceghnprsu]{4,7}[=_]{1,1}[abceghilnoprstuy]{7,8} | [(]{1,1}[Ubntu]{6,6} | [host]{1,4}[.=\[]{1,2}[aclno]{1,5}[,\]]{1,1} | [ceimnotu]{4,5}[!"#$%&'()*+,-./:;<=>?@\[\\]^_`{|}~]+ | R10
R10 :: [EFMORSTdfmnortw]{2,4} | [abcdeghnpst]{7,8}[=_]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | [14]{2,2}[.]{1,1}[7]{1,1}[-]{1,1}[1]{1,1}[.]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+[.]{1,1}[04]{2,2}[+]{1,1}[1]{1,1}[)]{1,1} | ["]{1,1}[127]{3,3}[.]{1,1}[0]{1,1}[.]{1,1}[0]{1,1}[.]{1,1}[1]{1,1}[",]{2,2} | ["/]{2,2}[arv]{3,3}[/]{1,1}[nru]{3,3}[/]{1,1}[eglopqrst]{10,10}[./]{2,2}[s]{1,1}[.]{1,1}[GLPQS]{5,5}[.]{1,1}[2345]{4,4}["]{1,1} | [0]{1,1}[:]{1,1}[0]{2,2}[:]{1,1}[0]{2,2}[.]{1,1}[0147]{3,3} | [abdest]{8,8}[=]{1,1}[hlsu]{5,5}[_]{1,1}[bdest]{6,6} | [p]{1,1}[.]{1,1}[aprst]{9,9}[,]{1,1} | R11
R11 :: [bceghnprsu]{4,7}[=_]{1,1}[abceghnoprstu]{8,8} | [abcelnoprt]{2,8} | [(]{1,1}[dit]{3,3}[,]{1,1} | [acilnopt]{11,11}[_]{1,1}[aemn]{4,4}[=]{1,1}[bceghlnpqs]{4,7} | [gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[cnotu]{5,5}[(]{1,1}[i]{1,1}[.]{1,1}[aehinprt]{9,9}[)]{1,1} | R12
R12 :: [0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | [=]{1,1} | [bdi]{3,3}[,]{1,1} | [0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+[_]{1,1}[46]{2,2}[-]{1,1}[cp]{2,2}[-]{1,1}[ilnux]{5,5}[-]{1,1}[gnu]{3,3}[,]{1,1} | [0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+[-=]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+[-_]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | [abdest]{8,8}[=]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | R13
R13 :: [abcdeilmnopt]{3,8} | [adi]{3,3}[,]{1,1} | [19]{2,2}[:]{1,1}[06]{2,2}[:]{1,1}[03]{2,2} | [host]{4,4}[=\[]{2,2}[aclo]{5,5}[\]]{1,1} | [gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[gp]{2,2}[_]{1,1}[acls]{5,5} | R14
R14 :: [+=]{1,1} | [adelt]{5,5}[,]{1,1} | [absy]{2,2} | [-]{1,1}[03]{2,2} | R15
R15 :: [0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+[);]{1,1} | [0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+ | [-]{1,1}[0123456789]{1,4} | R16
R16 :: [AEHLRSUVWijno]{4,6} | [(]{1,1}[Ubntu]{6,6} | R17
R17 :: [abdit]{3,3} | [(]{1,1}[0123456789]{1,2}[,]{1,1} | [9]{1,1}[.]{1,1}[4]{1,1}[.]{1,1}[0]{1,1}[-]{1,1}[0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]+[~]{1,1}[02]{2,2}[.]{1,1}[04]{2,2}[.]{1,1}[1]{1,1}[)]{1,1} | [gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[gp]{2,2}[_]{1,1}[acemnps]{9,9} | R18
R18 :: [=]{1,1} | [1]{1,1}[,]{1,1} | [9]{1,1}[.]{1,1}[4]{1,1}[.]{1,1}[0]{1,1}[,]{1,1} | [as]{2,2} | R19
R19 :: [0123456789]{1,5}[,;]{1,1} | [46]{2,2}[-]{1,1}[bit]{3,3} | [n]{1,1} | R20
R20 :: [-]{1,1}[0123456789]{2,4}[,]{1,1} | [no]{2,2} | [0123456789]{2,4}[,]{1,1} | R21
R21 :: [CENRTU]{7,7}[_]{1,1}[AEIMPST]{9,9}[);]{2,2} | [(]{1,1}[n]{1,1}[.]{1,1}[dio]{3,3} | R22
R22 :: [=]{1,1} | R23
R23 :: [c]{1,1}[.]{1,1}[acelmnprs]{12,12}[)]{1,1} | R24
R24 :: [cors]{5,5} | R25
R25 :: [ijno]{4,4} | R26
R26 :: [aelrt]{7,7} | R27
R27 :: [(]{1,1}[celst]{6,6} | R28
R28 :: [gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[ary]{5,5}[_]{1,1}[inopst]{8,8}[(]{1,1}[gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[cenrtu]{7,7}[_]{1,1}[acehms]{7,7}[(]{1,1}[ertu]{4,4}[),]{2,2} | R29
R29 :: [n]{1,1}[.]{1,1}[aemnps]{7,7}[)]{2,2} | R30
R30 :: [as]{2,2} | R31
R31 :: [o]{1,1}[(]{1,1}[n]{1,1}[)]{1,1} | R32
R32 :: [eflt]{4,4} | R33
R33 :: [ijno]{4,4} | R34
R34 :: [gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[gp]{2,2}[_]{1,1}[adeinoprt]{11,11}[_]{1,1}[abelt]{5,5} | R35
R35 :: [as]{2,2} | R36
R36 :: [p]{1,1} | R37
R37 :: [no]{2,2} | R38
R38 :: [(]{1,1}[p]{1,1}[.]{1,1}[adeilprt]{9,9} | R39
R39 :: [=]{1,1} | R40
R40 :: [c]{1,1}[.]{1,1}[dio]{3,3}[)]{1,1} | R41
R41 :: [eflt]{4,4} | R42
R42 :: [ijno]{4,4} | R43
R43 :: [gp]{2,2}[_]{1,1}[acglot]{7,7}[.]{1,1}[gp]{2,2}[_]{1,1}[ehinrst]{8,8} | R44
R44 :: [as]{2,2} | R45
R45 :: [i]{1,1} | R46
R46 :: [no]{2,2} | R47
R47 :: [(]{1,1}[c]{1,1}[.]{1,1}[dio]{3,3} | R48
R48 :: [=]{1,1} | R49
R49 :: [i]{1,1}[.]{1,1}[aehinprt]{9,9}[)]{1,1} | R50
R50 :: [ehrw]{5,5} | R51
R51 :: [c]{1,1}[.]{1,1}[aelmnr]{7,7} | R52
R52 :: [=]{1,1} | R53
R53 :: [']{1,1}[bceghnp]{7,7}[_]{1,1}[acnostu]{8,8}[']{1,1} | R54
R54 :: [adn]{3,3} | R55
R55 :: [o]{1,1}[.]{1,1}[n]{1,1} | R56
R56 :: [is]{2,2} | R57
R57 :: [not]{3,3} | R58
R58 :: [lnu]{4,4} | R59
R59 :: [gopru]{5,5} | R60
R60 :: [by]{2,2} | R61
R61 :: [1]{1,1}[,]{1,1} | R62
R62 :: [2]{1,1} | R63
R63 :: [deor]{5,5} | R64
R64 :: [by]{2,2} | R65
R65 :: [1]{1,1} | R66
R66 :: [acs]{3,3} | R67
R67 :: [ilmt]{5,5} | R68
R68 :: [1]{1,1}
```

Grammar statistics:

```
numNonTerminals=69 numTerminals=119 numTokens=351
```

Converged to 69 non-terminals and 118 terminals after 32 lines. Converged to 69
non-terminals and 119 terminals after 155 lines. The number of tokens converged
to 349 after 32 lines, and to 351 after 155 lines. After that, it did not
change. After consuming 155 lines, grammar size did not change (it converged).

### Merger

In the same setting as for the grammar observations above, the Powerset Lattice
of the Merger converged after 6 lines, with a total of 6 powerset nodes. This
includes the 4 initial ones specified by the user, so only 2 new ones.

Regarding nodes from the Interval lattice, we don't actually keep them stored
anywhere in the Merger Lattice. They are stored in the Grammar by terminal
rules. As seen above, there were a total of 351 interval nodes, which remained
constant after seeing 155 logs.
