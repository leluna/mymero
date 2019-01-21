// Compiled by ClojureScript 1.10.339 {}
goog.provide('mymero.core');
goog.require('cljs.core');
goog.require('goog.dom');
goog.require('reagent.core');
goog.require('mymero.dict');
cljs.core.enable_console_print_BANG_.call(null);
mymero.core.initial_npairs = (function mymero$core$initial_npairs(){
return (6);
});
mymero.core.process_word = (function mymero$core$process_word(p__9307){
var vec__9308 = p__9307;
var word = cljs.core.nth.call(null,vec__9308,(0),null);
var article = cljs.core.nth.call(null,vec__9308,(1),null);
return cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"word","word",-420123725),new cljs.core.Keyword(null,"article","article",-21685045),new cljs.core.Keyword(null,"unmatched","unmatched",1628955483),new cljs.core.Keyword(null,"selected","selected",574897764)],[word,article,true,false]);
});
mymero.core.load_words = (function mymero$core$load_words(words){
return cljs.core.apply.call(null,cljs.core.merge,cljs.core.map_indexed.call(null,(function (p1__9311_SHARP_,p2__9312_SHARP_){
return cljs.core.PersistentHashMap.fromArrays([p1__9311_SHARP_],[mymero.core.process_word.call(null,p2__9312_SHARP_)]);
}),words));
});
if((typeof mymero !== 'undefined') && (typeof mymero.core !== 'undefined') && (typeof mymero.core.app_state !== 'undefined')){
} else {
mymero.core.app_state = reagent.core.atom.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"theme","theme",-1247880880),cljs.core.first.call(null,cljs.core.keys.call(null,mymero.dict.dict)),new cljs.core.Keyword(null,"words","words",1924933001),mymero.core.load_words.call(null,cljs.core.val.call(null,cljs.core.first.call(null,mymero.dict.dict)))], null));
}
mymero.core.get_app_element = (function mymero$core$get_app_element(){
return goog.dom.getElement("app");
});
mymero.core.words = (function mymero$core$words(theme){
return cljs.core.get.call(null,mymero.dict.dict,theme);
});
mymero.core.hidden_QMARK_ = (function mymero$core$hidden_QMARK_(content){
var and__3938__auto__ = new cljs.core.Keyword(null,"unmatched","unmatched",1628955483).cljs$core$IFn$_invoke$arity$1(content);
if(cljs.core.truth_(and__3938__auto__)){
return cljs.core.not.call(null,new cljs.core.Keyword(null,"selected","selected",574897764).cljs$core$IFn$_invoke$arity$1(content));
} else {
return and__3938__auto__;
}
});
mymero.core.select = (function mymero$core$select(item){
return null;
});
mymero.core.card = (function mymero$core$card(item){
var content = cljs.core.val.call(null,item);
var map__9313 = content;
var map__9313__$1 = ((((!((map__9313 == null)))?(((((map__9313.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__9313.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__9313):map__9313);
var word = cljs.core.get.call(null,map__9313__$1,new cljs.core.Keyword(null,"word","word",-420123725));
var article = cljs.core.get.call(null,map__9313__$1,new cljs.core.Keyword(null,"article","article",-21685045));
var unmatched = cljs.core.get.call(null,map__9313__$1,new cljs.core.Keyword(null,"unmatched","unmatched",1628955483));
var selected = cljs.core.get.call(null,map__9313__$1,new cljs.core.Keyword(null,"selected","selected",574897764));
return cljs.core.with_meta(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"button.card","button.card",-1317771534),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"class","class",-2030961996),[cljs.core.str.cljs$core$IFn$_invoke$arity$1(((cljs.core.not.call(null,mymero.core.hidden_QMARK_.call(null,content)))?article:null)),cljs.core.str.cljs$core$IFn$_invoke$arity$1((cljs.core.truth_(unmatched)?" unmatched":null)),cljs.core.str.cljs$core$IFn$_invoke$arity$1((cljs.core.truth_(selected)?" selected":null))].join(''),new cljs.core.Keyword(null,"onclick","onclick",1297553739),((function (content,map__9313,map__9313__$1,word,article,unmatched,selected){
return (function (){
return mymero.core.select.call(null,item);
});})(content,map__9313,map__9313__$1,word,article,unmatched,selected))
], null),(cljs.core.truth_(mymero.core.hidden_QMARK_.call(null,content))?"":word)], null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"key","key",-1516042587),cljs.core.key.call(null,item)], null));
});
mymero.core.mymero = (function mymero$core$mymero(){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.game","div.game",1488032861),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.container","div.container",72419955),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"h1","h1",-1896887462),new cljs.core.Keyword(null,"theme","theme",-1247880880).cljs$core$IFn$_invoke$arity$1(cljs.core.deref.call(null,mymero.core.app_state))], null),new cljs.core.PersistentVector(null, 10, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.cards","div.cards",-1166114667),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.card,cljs.core.first.call(null,new cljs.core.PersistentArrayMap(null, 1, [(1),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"word","word",-420123725),"Drucker",new cljs.core.Keyword(null,"article","article",-21685045),"der",new cljs.core.Keyword(null,"unmatchhed","unmatchhed",1314818005),false,new cljs.core.Keyword(null,"selected","selected",574897764),false], null)], null))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.card,cljs.core.first.call(null,new cljs.core.PersistentArrayMap(null, 1, [(2),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"word","word",-420123725),"Schlange",new cljs.core.Keyword(null,"article","article",-21685045),"die",new cljs.core.Keyword(null,"unmatched","unmatched",1628955483),false,new cljs.core.Keyword(null,"selected","selected",574897764),false], null)], null))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.card,cljs.core.first.call(null,new cljs.core.PersistentArrayMap(null, 1, [(3),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"word","word",-420123725),"M\u00E4dchen",new cljs.core.Keyword(null,"article","article",-21685045),"das",new cljs.core.Keyword(null,"unmatched","unmatched",1628955483),false,new cljs.core.Keyword(null,"selected","selected",574897764),false], null)], null))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.card,cljs.core.first.call(null,new cljs.core.PersistentArrayMap(null, 1, [(4),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"word","word",-420123725),"Drucker",new cljs.core.Keyword(null,"article","article",-21685045),"der",new cljs.core.Keyword(null,"unmatched","unmatched",1628955483),false,new cljs.core.Keyword(null,"selected","selected",574897764),true], null)], null))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.card,cljs.core.first.call(null,new cljs.core.PersistentArrayMap(null, 1, [(5),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"word","word",-420123725),"Schlange",new cljs.core.Keyword(null,"article","article",-21685045),"die",new cljs.core.Keyword(null,"unmatched","unmatched",1628955483),true,new cljs.core.Keyword(null,"selected","selected",574897764),true], null)], null))], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.card,cljs.core.first.call(null,new cljs.core.PersistentArrayMap(null, 1, [(6),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"word","word",-420123725),"Maedchen",new cljs.core.Keyword(null,"article","article",-21685045),"das",new cljs.core.Keyword(null,"unmatched","unmatched",1628955483),true,new cljs.core.Keyword(null,"selected","selected",574897764),false], null)], null))], null),cljs.core.map.call(null,mymero.core.card,new cljs.core.Keyword(null,"words","words",1924933001).cljs$core$IFn$_invoke$arity$1(cljs.core.deref.call(null,mymero.core.app_state))),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"h3","h3",2067611163),document.documentElement.clientWidth], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"h3","h3",2067611163),document.documentElement.clientHeight], null)], null)], null)], null);
});
mymero.core.mount = (function mymero$core$mount(el){
return reagent.core.render_component.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [mymero.core.mymero], null),el);
});
mymero.core.mount_app_element = (function mymero$core$mount_app_element(){
var temp__5457__auto__ = mymero.core.get_app_element.call(null);
if(cljs.core.truth_(temp__5457__auto__)){
var el = temp__5457__auto__;
return mymero.core.mount.call(null,el);
} else {
return null;
}
});
mymero.core.mount_app_element.call(null);
mymero.core.on_reload = (function mymero$core$on_reload(){
return mymero.core.mount_app_element.call(null);
});

//# sourceMappingURL=core.js.map
