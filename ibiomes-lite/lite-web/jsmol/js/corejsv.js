(function(Clazz
,$_A
,$_Ab
,$_AB
,$_AC
,$_AD
,$_AF
,$_AI
,$_AL
,$_AS
,$_B
,$_C
,$_D
,$_E
,$_F
,$_G
,$_H
,$_I
,$_J
,$_K
,$_k
,$_L
,$_M
,$_N
,$_O
,$_P
,$_Q
,$_R
,$_S
,$_s
,$_T
,$_U
,$_V
,$_W
,$_X
,$_Y
,$_Z
,Clazz_doubleToInt
,Clazz_declarePackage
,Clazz_instanceOf
,Clazz_load
,Clazz_instantialize
,Clazz_decorateAsClass
,Clazz_floatToInt
,Clazz_makeConstructor
,Clazz_defineEnumConstant
,Clazz_exceptionOf
,Clazz_newIntArray
,Clazz_defineStatics
,Clazz_newFloatArray
,Clazz_declareType
,Clazz_prepareFields
,Clazz_superConstructor
,Clazz_newByteArray
,Clazz_declareInterface
,Clazz_p0p
,Clazz_pu$h
,Clazz_newShortArray
,Clazz_innerTypeInstance
,Clazz_isClassDefined
,Clazz_prepareCallback
,Clazz_newArray
,Clazz_castNullAs
,Clazz_floatToShort
,Clazz_superCall
,Clazz_decorateAsType
,Clazz_newBooleanArray
,Clazz_newCharArray
,Clazz_implementOf
,Clazz_newDoubleArray
,Clazz_overrideConstructor
,Clazz_supportsNativeObject
,Clazz_extendedObjectMethods
,Clazz_callingStackTraces
,Clazz_clone
,Clazz_doubleToShort
,Clazz_innerFunctions
,Clazz_getInheritedLevel
,Clazz_getParamsType
,Clazz_isAF
,Clazz_isAI
,Clazz_isAS
,Clazz_isASS
,Clazz_isAP
,Clazz_isAFloat
,Clazz_isAII
,Clazz_isAFF
,Clazz_isAFFF
,Clazz_tryToSearchAndExecute
,Clazz_getStackTrace
,Clazz_inheritArgs
){
var $t$;
//var c$;
___JmolDate="$Date: 2013-12-04 12:39:13 -0600 (Wed, 04 Dec 2013) $"
___fullJmolProperties="src/org/jmol/viewer/Jmol.properties"
___JmolVersion="14.0.1_2013.12.04"
// JSmolJavaExt.js

// BH 12/3/2013 11:43:10 AM bizarre Safari bug in reassigning Boolean (OK, I admit, we shouldn't have done that...) 
// BH 12/1/2013 6:50:16 AM evit Number.prototype.toString assignment removed!
// BH 11/30/2013 1:46:31 PM fixing Byte, Short, Long, Integer, Float, Double to reflect proper bounds and error conditions
// BH 11/29/2013 8:58:49 PM removing Boolean.toString(boolean)
// BH 11/4/2013 7:34:26 AM changing "var nativeClazz" to "var nativeClass" to avoid ANT replacement of "nativeClazz_" to "nativeClazz_"
// BH 10/19/2013 1:29:27 PM fixed String.$replace()
// BH 10/18/2013 6:09:23 PM fixed (Double|Float).valueOf(NaN).valueOf(), which should return NaN, not throw an error
// BH 10/12/2013 11:18:44 AM fixed bug in Double(String) and Float(String) that was returning typeof "string"
// BH 10/10/2013 2:40:20 PM  added Math.log10   

// BH 7/23/2013 7:24:01 AM fixing Number.shortValue() and Number.byteValue() for negative values
// BH 6/16/2013 1:31:30 PM adding /| in String.replace -- thank you David Koes
// BH 3/13/2013 12:49:23 PM setting Boolean.valueOf() "@" 
// BH 3/2/2013 10:46:45 PM removed Double.valueOf(String)
// BH 11/6/2012 8:26:33 PM added instanceof Int32Array in String.instantialize
// BH 10/13/2012 11:38:07 PM corrected Integer.parseInt to allow only +-0123456789; created Integer.parseIntRadix
// BH 11/1/2012 added Short
// BH 9/10/2012 6:27:21 AM added java.net.URL... classes
// BH 1/7/2013 7:40:06 AM added Clazz_dateToString

// JSmolJavaExt.js

// BH 11/30/2013 1:46:31 PM fixing Float(), Integer(), Double() (again)
// BH 11/29/2013 8:58:49 PM removing Boolean.toString(boolean)
// BH 11/4/2013 7:34:26 AM changing "var nativeClazz" to "var nativeClass" to avoid ANT replacement of "nativeClazz_" to "nativeClazz_"
// BH 10/19/2013 1:29:27 PM fixed String.$replace()
// BH 10/18/2013 6:09:23 PM fixed (Double|Float).valueOf(NaN).valueOf(), which should return NaN, not throw an error
// BH 10/12/2013 11:18:44 AM fixed bug in Double(String) and Float(String) that was returning typeof "string"
// BH 10/10/2013 2:40:20 PM  added Math.log10   

// BH 7/23/2013 7:24:01 AM fixing Number.shortValue() and Number.byteValue() for negative values
// BH 6/16/2013 1:31:30 PM adding /| in String.replace -- thank you David Koes
// BH 3/13/2013 12:49:23 PM setting Boolean.valueOf() "@" 
// BH 3/2/2013 10:46:45 PM removed Double.valueOf(String)
// BH 11/6/2012 8:26:33 PM added instanceof Int32Array in String.instantialize
// BH 10/13/2012 11:38:07 PM corrected Integer.parseInt to allow only +-0123456789; created Integer.parseIntRadix
// BH 11/1/2012 added Short
// BH 9/10/2012 6:27:21 AM added java.net.URL... classes
// BH 1/7/2013 7:40:06 AM added Clazz_dateToString


// will be wrapped by anonymous function using ANT in build_03_tojs.xml

Math.log10||(Math.log10=function(a){return Math.log(a)/2.302585092994046});

var ntsp = Number.prototype.toString; // don't touch this one

java.lang.Number=Number;
if(Clazz_supportsNativeObject){
  for(var i=0;i<Clazz_extendedObjectMethods.length;i++){
    var p=Clazz_extendedObjectMethods[i];
    Number.prototype[p]=JavaObject.prototype[p];
  }
}

Number.prototype.toString = ntsp;

Number.__CLASS_NAME__="Number";
Clazz_implementOf(Number,java.io.Serializable);
Number.equals=Clazz_innerFunctions.equals;
Number.getName=Clazz_innerFunctions.getName;


$_M(Number,"shortValue",
function(){
var x = Math.round(this)&0xffff;
return (this < 0 && x > 0 ? x - 0x10000 : x);
});

$_M(Number,"byteValue",
function(){
var x = Math.round(this)&0xff;
return (this < 0 && x > 0 ? x - 0x100 : x);
});

$_M(Number,"intValue",
function(){
return Math.round(this)&0xffffffff;
});

$_M(Number,"longValue",
function(){
return Math.round(this);
});

$_M(Number,"floatValue",
function(){
return this.valueOf();
});
$_M(Number,"doubleValue",
function(){
return parseFloat(this.valueOf());
});

$_V(Number,"hashCode",
function(){
return this.valueOf();
});

java.lang.Integer=Integer=function(){
Clazz_instantialize(this,arguments);
};
Clazz_decorateAsType(Integer,"Integer",Number,Comparable,null,true);
Integer.prototype.valueOf=function(){return 0;};
Integer.toString=Integer.prototype.toString=function(){
if(arguments.length!=0){
return""+arguments[0];
} else if(this===Integer){
return"class java.lang.Integer";
}
return""+this.valueOf();
};

/*

Clazz_makeConstructor(Integer,
function(){
this.valueOf=function(){
return 0;
};
});
*/


$_k(Integer, function(v){
 v == null && (v = 0);
 if (typeof v != "number")
  v = Integer.parseIntRadix(v, 10);
 this.valueOf=function(){return v;};
}); //BH
/*
Clazz_makeConstructor(Integer,
function(s){
var value=Integer.parseInt(s,10);
this.valueOf=function(){
return value;
};
},"String");
*/
Integer.MIN_VALUE=Integer.prototype.MIN_VALUE=-0x80000000;
Integer.MAX_VALUE=Integer.prototype.MAX_VALUE=0x7fffffff;
Integer.TYPE=Integer.prototype.TYPE=Integer;


$_M(Integer,"bitCount",
function(i) {
  i = i - ((i >>> 1) & 0x55555555);
  i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
  i = (i + (i >>> 4)) & 0x0f0f0f0f;
  i = i + (i >>> 8);
  i = i + (i >>> 16);
  return i & 0x3f;
},"Number");
Integer.bitCount=Integer.prototype.bitCount;

$_M(Integer,"numberOfLeadingZeros",
function(i) {
 if (i == 0) return 32;
 var n = 1;
 if (i >>> 16 == 0) { n += 16; i <<= 16; }
 if (i >>> 24 == 0) { n +=  8; i <<=  8; }
 if (i >>> 28 == 0) { n +=  4; i <<=  4; }
 if (i >>> 30 == 0) { n +=  2; i <<=  2; }
 n -= i >>> 31;
 return n;
},"Number");
Integer.numberOfLeadingZeros=Integer.prototype.numberOfLeadingZeros;

$_M(Integer,"numberOfTrailingZeros",
function(i) {
  if (i == 0) return 32;
  var n = 31;
  var y = i <<16; if (y != 0) { n = n -16; i = y; }
  y = i << 8; if (y != 0) { n = n - 8; i = y; }
  y = i << 4; if (y != 0) { n = n - 4; i = y; }
  y = i << 2; if (y != 0) { n = n - 2; i = y; }
  return n - ((i << 1) >>> 31);
},"Number");
Integer.numberOfTrailingZeros=Integer.prototype.numberOfTrailingZeros;

$_M(Integer,"parseIntRadix",
function(s,radix){
if(s==null){
throw new NumberFormatException("null");
}if(radix<2){
throw new NumberFormatException("radix "+radix+" less than Character.MIN_RADIX");
}if(radix>36){
throw new NumberFormatException("radix "+radix+" greater than Character.MAX_RADIX");
}
if (radix == 10) {
	for (var i = s.length; --i >= 0;) {
	  var c = s.charCodeAt(i);
	  if (c >= 48 && c <= 57) continue;
	  if (i > 0 || c != 43 && c != 45)
		  throw new NumberFormatException("Not a Number : "+s);

	}
}
var i=parseInt(s,radix);
if(isNaN(i)){
throw new NumberFormatException("Not a Number : "+s);
}
return i;
},"String, Number");
Integer.parseIntRadix=Integer.prototype.parseIntRadix;

$_M(Integer,"parseInt",
function(s){
return Integer.parseIntRadix(s,10);
},"String");
Integer.parseInt=Integer.prototype.parseInt;

/*
$_M(Integer,"$valueOf",
function(s){
return new Integer(Integer.parseIntRadix(s,10));
},"String");
*/

$_V(Integer,"$valueOf",
function(s){
return new Integer(s);
});

/*
$_M(Integer,"$valueOf",
function(s,r){
return new Integer(Integer.parseIntRadix(s,r));
},"String, Number");
*/

Integer.$valueOf=Integer.prototype.$valueOf;


$_V(Integer,"equals",
function(s){
if(s==null||!Clazz_instanceOf(s,Integer)){
return false;
}
return s.valueOf()==this.valueOf();
},"Object");
Integer.toHexString=Integer.prototype.toHexString=function(d){
if(d.valueOf)d=d.valueOf();
if (d < 0) {
var b = d & 0xFFFFFF;
var c = ((d>>24)&0xFF);
return c._numberToString(16) + (b = b._numberToString(16)).substring(b.length - 6);
}
return d._numberToString(16);};
Integer.toOctalString=Integer.prototype.toOctalString=function(d){if(d.valueOf)d=d.valueOf();return d._numberToString(8);};
Integer.toBinaryString=Integer.prototype.toBinaryString=function(d){if(d.valueOf)d=d.valueOf();return d._numberToString(2);};

Integer.decodeRaw=$_M(Integer,"decodeRaw", function(n){
if (n.indexOf(".") >= 0)n = "";
var i = (n.startsWith("-") ? 1 : 0);
n = n.replace(/\#/, "0x").toLowerCase();
var radix=(n.startsWith("0x", i) ? 16 : n.startsWith("0", i) ? 8 : 10);
// The general problem with parseInt is that is not strict -- ParseInt("10whatever") == 10.
// Number is strict, but Number("055") does not work, though ParseInt("055", 8) does.
n = Number(n);
return (radix == 8 ? parseInt(n, 8) : n);
},"~S");

Integer.decode=$_M(Integer,"decode", function(n){
  n = Integer.decodeRaw(n);
  if (isNaN(n) || n < Integer.MIN_VALUE|| n > Integer.MAX_VALUE)
  throw new NumberFormatException("Invalid Integer");
  return new Integer(n);
},"~S");

$_V(Integer,"hashCode",
function(){
return this.valueOf();
});

// Note that Long is problematic in JavaScript 

java.lang.Long=Long=function(){
Clazz_instantialize(this,arguments);
};
Clazz_decorateAsType(Long,"Long",Number,Comparable,null,true);
Long.prototype.valueOf=function(){return 0;};
Long.toString=Long.prototype.toString=function(){
if(arguments.length!=0){
return""+arguments[0];
}else if(this===Long){
return"class java.lang.Long";
}
return""+this.valueOf();
};

$_k(Long, function(v){
 v == null && (v = 0);
 v = (typeof v == "number" ? Math.round(v) : Integer.parseIntRadix(v, 10));
this.valueOf=function(){return v;};
});

//Long.MIN_VALUE=Long.prototype.MIN_VALUE=-0x8000000000000000;
//Long.MAX_VALUE=Long.prototype.MAX_VALUE=0x7fffffffffffffff;
Long.TYPE=Long.prototype.TYPE=Long;

$_M(Long,"parseLong",
function(s,radix){
 return Integer.parseInteger(s, radix || 10);
});

Long.parseLong=Long.prototype.parseLong;

$_V(Long,"$valueOf",
function(s){
return new Long(s);
});
/*
$_M(Long,"$valueOf",
function(s){
return new Long(s);
},"Number");

$_M(Long,"$valueOf",
function(s,r){
return new Long(Long.parseLong(s,r));
},"String, Number");
*/
Long.$valueOf=Long.prototype.$valueOf;
$_V(Long,"equals",
function(s){
if(s==null||!Clazz_instanceOf(s,Long)){
return false;
}
return s.valueOf()==this.valueOf();
},"Object");
Long.toHexString=Long.prototype.toHexString=function(i){
return i.toString(16);
};
Long.toOctalString=Long.prototype.toOctalString=function(i){
return i.toString(8);
};
Long.toBinaryString=Long.prototype.toBinaryString=function(i){
return i.toString(2);
};


Long.decode=$_M(Long,"decode",
function(n){
  n = Integer.decodeRaw(n);
  if (isNaN(n))
    throw new NumberFormatException("Invalid Long");
  return new Long(n);
},"~S");

java.lang.Short = Short = function () {
Clazz_instantialize (this, arguments);
};
Clazz_decorateAsType (Short, "Short", Number, Comparable, null, true);
Short.prototype.valueOf = function () { return 0; };
Short.toString = Short.prototype.toString = function () {
	if (arguments.length != 0) {
		return "" + arguments[0];
	} else if (this === Short) {
		return "class java.lang.Short"; // Short.class.toString
	}
	return "" + this.valueOf ();
};

$_k (Short,
function (v) {
 v == null && (v = 0);
 if (typeof v != "number")
  v = Integer.parseIntRadix(v, 10);
 v = v.shortValue();
 this.valueOf = function () {return v;};
});


Short.MIN_VALUE = Short.prototype.MIN_VALUE = -32768;
Short.MAX_VALUE = Short.prototype.MAX_VALUE = 32767;
Short.TYPE = Short.prototype.TYPE = Short;

$_M(Short, "parseShortRadix",
function (s, radix) {
return Integer.parseIntRadix(s, radix).shortValue();
}, "String, Number");
Short.parseShortRadix = Short.prototype.parseShortRadix;

$_M(Short, "parseShort",
function (s) {
return Short.parseShortRadix (s, 10);
}, "String");

Short.parseShort = Short.prototype.parseShort;

/*
$_M(Short, "$valueOf",
function (s) {
return new Short(Short.parseShort (s, 10));
}, "String");
  */
  
$_V(Short, "$valueOf",
function (s) {
return new Short(s);
});

/*
$_M(Short, "$valueOf",
function (s, r) {
return new Short(Short.parseShort (s, r));
}, "String, Number");
  */
  
Short.$valueOf = Short.prototype.$valueOf;
$_V(Short, "equals",
function (s) {
if(s == null || !Clazz_instanceOf(s, Short) ){
	return false;
}
return s.valueOf()  == this.valueOf();
}, "Object");
Short.toHexString = Short.prototype.toHexString = function (i) {
	return i.toString (16);
};
Short.toOctalString = Short.prototype.toOctalString = function (i) {
	return i.toString (8);
};
Short.toBinaryString = Short.prototype.toBinaryString = function (i) {
	return i.toString (2);
};
Short.decode = $_M(Short, "decode",
function(n){
  n = Integer.decodeRaw(n);
  if (isNaN(n) || n < -32768|| n > 32767)
    throw new NumberFormatException("Invalid Short");
  return new Short(n);
}, "~S");

java.lang.Byte=Byte=function(){
Clazz_instantialize(this,arguments);
};
Clazz_decorateAsType(Byte,"Byte",Number,Comparable,null,true);
Byte.prototype.valueOf=function(){return 0;};
Byte.toString=Byte.prototype.toString=function(){
if(arguments.length!=0){
return""+arguments[0];
}else if(this===Byte){
return"class java.lang.Byte";
}
return""+this.valueOf();
};
Clazz_makeConstructor(Byte,
function(v){
 if (typeof v != "number")
   v = Integer.parseIntRadix(v, 10);
 v = v.byteValue();
this.valueOf=function(){
return v;
};
});

Byte.serialVersionUID=Byte.prototype.serialVersionUID=-7183698231559129828;
Byte.MIN_VALUE=Byte.prototype.MIN_VALUE=-128;
Byte.MAX_VALUE=Byte.prototype.MAX_VALUE=127;
Byte.SIZE=Byte.prototype.SIZE=8;
Byte.TYPE=Byte.prototype.TYPE=Byte;

$_M(Byte,"parseByteRadix",
function(s,radix){
 return Integer.parseIntRadix(s, radix).byteValue();
},"String, Number");
Byte.parseByteRadix=Byte.prototype.parseByteRadix;

$_M(Byte,"parseByte",
function(s){
return Byte.parseByte(s,10);
},"String");

Byte.parseByte=Byte.prototype.parseByte;

$_V(Byte, "$valueOf",
function (s) {
return new Byte(s);
});

Byte.$valueOf=Byte.prototype.$valueOf;
$_V(Byte,"equals",
function(s){
if(s==null||!Clazz_instanceOf(s,Byte)){
return false;
}
return s.valueOf()==this.valueOf();
},"Object");
Byte.toHexString=Byte.prototype.toHexString=function(i){
return i.toString(16);
};
Byte.toOctalString=Byte.prototype.toOctalString=function(i){
return i.toString(8);
};
Byte.toBinaryString=Byte.prototype.toBinaryString=function(i){
return i.toString(2);
};
Byte.decode=$_M(Byte,"decode",
function(n){
  n = Integer.decodeRaw(n);
  if (isNaN(n) || n < -128|| n > 127)
    throw new NumberFormatException("Invalid Byte");
return new Byte(n);
},"~S");

java.lang.Float=Float=function(){
Clazz_instantialize(this,arguments);
};
Clazz_decorateAsType(Float,"Float",Number,Comparable,null,true);
Float.prototype.valueOf=function(){return 0;};
Float.toString=Float.prototype.toString=function(){
if(arguments.length!=0){
return""+arguments[0];
}else if(this===Float){
return"class java.lang.Float";
}
return""+this.valueOf();
};

$_k(Float, function(v){
 v == null && (v = 0);
 if (typeof v != "number") 
  v = Number(v);
 this.valueOf=function(){return v;}
});

Float.serialVersionUID=Float.prototype.serialVersionUID=-2671257302660747028;
Float.MIN_VALUE=Float.prototype.MIN_VALUE=3.4028235e+38;
Float.MAX_VALUE=Float.prototype.MAX_VALUE=1.4e-45;
Float.NEGATIVE_INFINITY=Number.NEGATIVE_INFINITY;
Float.POSITIVE_INFINITY=Number.POSITIVE_INFINITY;
Float.NaN=Number.NaN;
Float.TYPE=Float.prototype.TYPE=Float;

$_M(Float,"parseFloat",
function(s){
if(s==null){
throw new NumberFormatException("null");
}
if (typeof s == "number")return s;  // important -- typeof NaN is "number" and is OK here
var floatVal=Number(s);
if(isNaN(floatVal)){
throw new NumberFormatException("Not a Number : "+s);
}
return floatVal;
},"String");
Float.parseFloat=Float.prototype.parseFloat;

$_V(Float,"$valueOf",
function(s){
return new Float(s);
});

Float.$valueOf=Float.prototype.$valueOf;

$_M(Float,"isNaN",
function(num){
return isNaN(num);
},"Number");
Float.isNaN=Float.prototype.isNaN;
$_M(Float,"isInfinite",
function(num){
return!isFinite(num);
},"Number");
Float.isInfinite=Float.prototype.isInfinite;

$_V(Float,"equals",
function(s){
if(s==null||!Clazz_instanceOf(s,Float)){
return false;
}
return s.valueOf()==this.valueOf();
},"Object");

java.lang.Double=Double=function(){
Clazz_instantialize(this,arguments);
};
Clazz_decorateAsType(Double,"Double",Number,Comparable,null,true);
Double.prototype.valueOf=function(){return 0;};
Double.toString=Double.prototype.toString=function(){
if(arguments.length!=0){
return""+arguments[0];
}else if(this===Double){
return"class java.lang.Double";
}
return""+this.valueOf();
};

$_k(Double, function(v){
 v == null && (v = 0);
 if (typeof v != "number") 
  v = Double.parseDouble(v);
 this.valueOf=function(){return v;};
}); // BH

Double.serialVersionUID=Double.prototype.serialVersionUID=-9172774392245257468;
Double.MIN_VALUE=Double.prototype.MIN_VALUE=4.9e-324;
Double.MAX_VALUE=Double.prototype.MAX_VALUE=1.7976931348623157e+308;
Double.NEGATIVE_INFINITY=Number.NEGATIVE_INFINITY;
Double.POSITIVE_INFINITY=Number.POSITIVE_INFINITY;
Double.NaN=Number.NaN;
Double.TYPE=Double.prototype.TYPE=Double;

$_M(Double,"isNaN",
function(num){
return isNaN(num);
},"Number");
Double.isNaN=Double.prototype.isNaN;
$_M(Double,"isInfinite",
function(num){
return!isFinite(num);
},"Number");
Double.isInfinite=Double.prototype.isInfinite;

$_M(Double,"parseDouble",
function(s){
if(s==null){
throw new NumberFormatException("null");
}
if (typeof s == "number")return s;  // important -- typeof NaN is "number" and is OK here
var doubleVal=Number(s);
if(isNaN(doubleVal)){
throw new NumberFormatException("Not a Number : "+s);
}
return doubleVal;
},"String");
Double.parseDouble=Double.prototype.parseDouble;

/*
$_M(Double,"$valueOf",
function(s){
return new Double(this.parseDouble(s));
},"String");
*/

$_M(Double,"$valueOf",
function(v){
return new Double(v);
},"Number");

Double.$valueOf=Double.prototype.$valueOf;

$_V(Double,"equals",
function(s){
if(s==null||!Clazz_instanceOf(s,Double)){
return false;
}
return s.valueOf()==this.valueOf();
},"Object");


java.lang.B00lean = Boolean;
Boolean = java.lang.Boolean = Boolean || function () {Clazz_instantialize (this, arguments);};
if (Clazz_supportsNativeObject) {
	for (var i = 0; i < Clazz_extendedObjectMethods.length; i++) {
		var p = Clazz_extendedObjectMethods[i];
		Boolean.prototype[p] = JavaObject.prototype[p];
	}
}
Boolean.__CLASS_NAME__="Boolean";
Clazz_implementOf(Boolean,[java.io.Serializable,java.lang.Comparable]);
Boolean.equals=Clazz_innerFunctions.equals;
Boolean.getName=Clazz_innerFunctions.getName;
Boolean.serialVersionUID=Boolean.prototype.serialVersionUID=-3665804199014368530;

//Clazz_makeConstructor(Boolean,
//function(value){
//this.valueOf=function(){
//return value;
//};
//},"~B");

Clazz_overrideConstructor(Boolean,
function(s){
  var b = ((typeof s == "string" ? Boolean.toBoolean(s) : s) ? true : false);
  this.valueOf=function(){return b;};
},"~O");

Boolean.parseBoolean=$_M(Boolean,"parseBoolean",
function(s){
return Boolean.toBoolean(s);
},"~S");
$_M(Boolean,"booleanValue",
function(){
return this.valueOf();
});
Boolean.$valueOf=$_V(Boolean,"$valueOf",
function(b){
return(b?Boolean.TRUE:Boolean.FALSE);
});

/*
Boolean.toString=$_M(Boolean,"toString",
function(b){
return b?"true":"false";
},"~B");
*/

$_V(Boolean,"toString",
function(){
return this.valueOf()?"true":"false";
});
$_V(Boolean,"hashCode",
function(){
return this.valueOf()?1231:1237;
});
$_V(Boolean,"equals",
function(obj){
if(Clazz_instanceOf(obj,Boolean)){
return this.booleanValue()==obj.booleanValue();
}return false;
},"~O");
Boolean.getBoolean=$_M(Boolean,"getBoolean",
function(name){
var result=false;
try{
result=Boolean.toBoolean(System.getProperty(name));
}catch(e){
if(Clazz_instanceOf(e,IllegalArgumentException)){
}else if(Clazz_instanceOf(e,NullPointerException)){
}else{
throw e;
}
}
return result;
},"~S");
$_V(Boolean,"compareTo",
function(b){
return(b.value==this.value?0:(this.value?1:-1));
},"Boolean");
Boolean.toBoolean=$_M(Boolean,"toBoolean",
($fz=function(name){
return((name!=null)&&name.equalsIgnoreCase("true"));
},$fz.isPrivate=true,$fz),"~S");
Boolean.TRUE=Boolean.prototype.TRUE=new Boolean(true);
Boolean.FALSE=Boolean.prototype.FALSE=new Boolean(false);
Boolean.TYPE=Boolean.prototype.TYPE=Boolean;


Encoding=new Object();
Encoding.UTF8="utf-8";
Encoding.UTF16="utf-16";
Encoding.ASCII="ascii";


Encoding.guessEncoding=function(str){
if(str.charCodeAt(0)==0xEF&&str.charCodeAt(1)==0xBB&&str.charCodeAt(2)==0xBF){
return Encoding.UTF8;
}else if(str.charCodeAt(0)==0xFF&&str.charCodeAt(1)==0xFE){
return Encoding.UTF16;
}else{
return Encoding.ASCII;
}
};

Encoding.readUTF8=function(str){
var encoding=this.guessEncoding(str);
var startIdx=0;
if(encoding==Encoding.UTF8){
startIdx=3;
}else if(encoding==Encoding.UTF16){
startIdx=2;
}
var arrs=new Array();
for(var i=startIdx;i<str.length;i++){
var charCode=str.charCodeAt(i);
if(charCode<0x80){
arrs[arrs.length]=str.charAt(i);
}else if(charCode>0xc0&&charCode<0xe0){
var c1=charCode&0x1f;
i++;
var c2=str.charCodeAt(i)&0x3f;
var c=(c1<<6)+c2;
arrs[arrs.length]=String.fromCharCode(c);
}else if(charCode>=0xe0){
var c1=charCode&0x0f;
i++;
var c2=str.charCodeAt(i)&0x3f;
i++;
var c3=str.charCodeAt(i)&0x3f;
var c=(c1<<12)+(c2<<6)+c3;
arrs[arrs.length]=String.fromCharCode(c);
}
}
return arrs.join('');
};

Encoding.convert2UTF8=function(str){
var encoding=this.guessEncoding(str);
var startIdx=0;
if(encoding==Encoding.UTF8){
return str;
}else if(encoding==Encoding.UTF16){
startIdx=2;
}

var offset=0;
var arrs=new Array(offset+str.length-startIdx);

for(var i=startIdx;i<str.length;i++){
var charCode=str.charCodeAt(i);
if(charCode<0x80){
arrs[offset+i-startIdx]=str.charAt(i);
}else if(charCode<=0x07ff){
var c1=0xc0+((charCode&0x07c0)>>6);
var c2=0x80+(charCode&0x003f);
arrs[offset+i-startIdx]=String.fromCharCode(c1)+String.fromCharCode(c2);
}else{
var c1=0xe0+((charCode&0xf000)>>12);
var c2=0x80+((charCode&0x0fc0)>>6);
var c3=0x80+(charCode&0x003f);
arrs[offset+i-startIdx]=String.fromCharCode(c1)+String.fromCharCode(c2)+String.fromCharCode(c3);
}
}
return arrs.join('');
};
Encoding.base64Chars=new Array(
'A','B','C','D','E','F','G','H',
'I','J','K','L','M','N','O','P',
'Q','R','S','T','U','V','W','X',
'Y','Z','a','b','c','d','e','f',
'g','h','i','j','k','l','m','n',
'o','p','q','r','s','t','u','v',
'w','x','y','z','0','1','2','3',
'4','5','6','7','8','9','+','/'
);
Encoding.encodeBase64=function(str){
if(str==null||str.length==0)return str;
var b64=Encoding.base64Chars;
var length=str.length;
var index=0;
var buf=[];
var c0,c1,c2;
while(index<length){
c0=str.charCodeAt(index++);
buf[buf.length]=b64[c0>>2];
if(index<length){
c1=str.charCodeAt(index++);
buf[buf.length]=b64[((c0<<4)&0x30)|(c1>>4)];
if(index<length){
c2=str.charCodeAt(index++);
buf[buf.length]=b64[((c1<<2)&0x3c)|(c2>>6)];
buf[buf.length]=b64[c2&0x3F];
}else{
buf[buf.length]=b64[((c1<<2)&0x3c)];
buf[buf.length]='=';
}
}else{
buf[buf.length]=b64[(c0<<4)&0x30];
buf[buf.length]='=';
buf[buf.length]='=';
}
}
return buf.join('');
};
Encoding.decodeBase64=function(str){
if(str==null||str.length==0)return str;
var b64=Encoding.base64Chars;
var xb64=Encoding.xBase64Chars;
if(Encoding.xBase64Chars==null){
xb64=new Object();
for(var i=0;i<b64.length;i++){
xb64[b64[i]]=i;
}
Encoding.xBase64Chars=xb64;
}
var length=str.length;
var index=0;
var buf=[];
var c0,c1,c2,c3;
var c=0;
while(index<length&&c++<60000){
c0=xb64[str.charAt(index++)];
c1=xb64[str.charAt(index++)];
c2=xb64[str.charAt(index++)];
c3=xb64[str.charAt(index++)];
buf[buf.length]=String.fromCharCode(((c0<<2)&0xff)|c1>>4);
if(c2!=null){
buf[buf.length]=String.fromCharCode(((c1<<4)&0xff)|c2>>2);
if(c3!=null){
buf[buf.length]=String.fromCharCode(((c2<<6)&0xff)|c3);
}
}
}
return buf.join('');
};

if(String.prototype.$replace==null){
java.lang.String=String;
if(Clazz_supportsNativeObject){
for(var i=0;i<Clazz_extendedObjectMethods.length;i++){
var p=Clazz_extendedObjectMethods[i];
if("to$tring"==p||"toString"==p||"equals"==p||"hashCode"==p){
continue;
}
String.prototype[p]=JavaObject.prototype[p];
}
}

Clazz_implementOf(String,[java.io.Serializable,CharSequence,Comparable]);

String.getName=Clazz_innerFunctions.getName;

String.serialVersionUID=String.prototype.serialVersionUID=-6849794470754667710;

String.prototype.$replace=function(c1,c2){
	if (c1 == c2 || this.indexOf (c1) < 0) return "" + this;
	if (c1.length == 1) {
    if ("\\$.*+|?^{}()[]".indexOf(c1) >= 0) 	c1 = "\\" + c1;
  } else {    
    c1=c1.replace(/([\\\$\.\*\+\|\?\^\{\}\(\)\[\]])/g,function($0,$1){return"\\"+$1;});
  }
  return this.replace(new RegExp(c1,"gm"),c2);
};
String.prototype.$generateExpFunction=function(str){
var arr=[];
var orders=[];
var idx=0;
arr[0]="";
var i=0;
for(;i<str.length;i++){
var ch=str.charAt(i);
if(i!=str.length-1&&ch=='\\'){
i++;
var c=str.charAt(i);
if(c=='\\'){
arr[idx]+='\\';
}
arr[idx]+=c;
}else if(i!=str.length-1&&ch=='$'){
i++;
orders[idx]=parseInt(str.charAt(i));
idx++;
arr[idx]="";
}else if(ch=='\r'){
arr[idx]+="\\r";
}else if(ch=='\n'){
arr[idx]+="\\n";
}else if(ch=='\t'){
arr[idx]+="\\t";
}else if(ch=='\"'){
arr[idx]+="\\\"";
}else{
arr[idx]+=ch;
}
}
var funStr="f = function (";
var max=Math.max.apply({},orders);
for(i=0;i<=max;i++){
funStr+="$"+i;
if(i!=max){
funStr+=", ";
}
}
funStr+=") { return ";
for(i=0;i<arr.length-1;i++){
funStr+="\""+arr[i]+"\" + $"+orders[i]+" + ";
}
funStr+="\""+arr[i]+"\"; }";
var f=null;
eval(funStr)
return f;
};

String.prototype.replaceAll=function(exp,str){
var regExp=new RegExp(exp,"gm");
return this.replace(regExp,this.$generateExpFunction(str));
};
String.prototype.replaceFirst=function(exp,str){
var regExp=new RegExp(exp,"m");
return this.replace(regExp,this.$generateExpFunction(str));
};
String.prototype.matches=function(exp){
if(exp!=null){
exp="^("+exp+")$";
}
var regExp=new RegExp(exp,"gm");
var m=this.match(regExp);
return m!=null&&m.length!=0;
};
String.prototype.regionMatches=function(ignoreCase,toffset,
other,ooffset,len){

if(typeof ignoreCase=="number"
||(ignoreCase!=true&&ignoreCase!=false)){
len=ooffset;
ooffset=other;
other=toffset;
toffset=ignoreCase;
ignoreCase=false;
}
var to=toffset;
var po=ooffset;

if((ooffset<0)||(toffset<0)||(toffset>this.length-len)||
(ooffset>other.length-len)){
return false;
}
var s1=this.substring(toffset,toffset+len);
var s2=other.substring(ooffset,ooffset+len);
if(ignoreCase){
s1=s1.toLowerCase();
s2=s2.toLowerCase();
}
return s1==s2;
};



String.prototype.$plit=function(regex,limit){

if(limit!=null&&limit>0){
if(limit==1){
return this;
}
var regExp=new RegExp("("+regex+")","gm");
var count=1;
var s=this.replace(regExp,function($0,$1){
count++;
if(count==limit){
return"@@_@@";
}else if(count>limit){
return $0;
}else{
return $0;
}
});
regExp=new RegExp(regex,"gm");
var arr=this.split(regExp);
if(arr.length>limit){
arr[limit-1]=s.substring(s.indexOf("@@_@@")+5);
arr.length=limit;
}
return arr;
}else{
var regExp=new RegExp(regex,"gm");
return this.split(regExp);
}
};

String.prototype.trim=function(){
var len=this.length;
var st=0;

while((st<len)&&(this.charAt(st)<=' ')){
st++;
}
while((st<len)&&(this.charAt(len-1)<=' ')){
len--;
}
return((st>0)||(len<len))?this.substring(st,len):this;
};

String.prototype.trim=function(){
return this.replace(/^\s+/g,'').replace(/\s+$/g,'');
};


String.prototype.startsWith_string_number=function(prefix,toffset){
var to=toffset;
var po=0;
var pc=prefix.length;

if((toffset<0)||(toffset>this.length-pc)){
return false;
}
while(--pc>=0){
if(this.charAt(to++)!=prefix.charAt(po++)){
return false;
}
}
return true;
};

String.prototype.startsWith=function(prefix){
if(arguments.length==1){
return this.startsWith_string_number(arguments[0],0);
}else if(arguments.length==2){
return this.startsWith_string_number(arguments[0],arguments[1]);
}else{
return false;
}
};

String.prototype.endsWith=function(suffix){
return this.startsWith(suffix,this.length-suffix.length);
};

String.prototype.equals=function(anObject){
return this.valueOf()==anObject;
};

String.prototype.equalsIgnoreCase=function(anotherString){
return(anotherString==null)?false:(this==anotherString
||this.toLowerCase()==anotherString.toLowerCase());
};


String.prototype.hash=0;

String.prototype.hashCode=function(){
var h=this.hash;
if(h==0){
var off=0;
var len=this.length;
for(var i=0;i<len;i++){
h=31*h+this.charCodeAt(off++);
h&=0xffffffff;
}
this.hash=h;
}
return h;
};

String.prototype.getBytes=function(){
if(arguments.length==4){
return this.getChars(arguments[0],arguments[1],arguments[2],arguments[3]);
}
var s=this;
if(arguments.length==1){
var cs=arguments[0].toString().toLowerCase();
var charset=[
"utf-8","UTF8","us-ascii","iso-8859-1","8859_1","gb2312","gb18030","gbk"
];
var existed=false;
for(var i=0;i<charset.length;i++){
if(charset[i]==cs){
existed=true;
break;
}
}
if(!existed){
throw new java.io.UnsupportedEncodingException();
}
if(cs=="utf-8"||cs=="utf8"){
s=Encoding.convert2UTF8(this);
}
}
var arrs=new Array(s.length);
var c=0,ii=0;
for(var i=0;i<s.length;i++){
c=s.charCodeAt(i);
if(c>255){
arrs[ii]=0x1a;
arrs[ii+1]=c&0xff;
arrs[ii+2]=(c&0xff00)>>8;
ii+=2;
}else{
arrs[ii]=c;
}
ii++;
}
return arrs;
};

/*
String.prototype.compareTo=function(anotherString){
if(anotherString==null){
throw new java.lang.NullPointerException();
}
var len1=this.length;
var len2=anotherString.length;
var n=Math.min(len1,len2);
var k=0;
while(k<n){
var c1=this.charCodeAt(k);
var c2=anotherString.charCodeAt(k);
if(c1!=c2){
return c1-c2;
}
k++;
}
return len1-len2;
};

*/

String.prototype.contains = function(a) {return this.indexOf(a) >= 0}  // bh added
String.prototype.compareTo = function(a){return this > a ? 1 : this < a ? -1 : 0} // bh added
  


String.prototype.toCharArray=function(){
var result=new Array(this.length);
for(var i=0;i<this.length;i++){
result[i]=this.charAt(i);
}
return result;
};
String.value0f=String.valueOf;
String.valueOf=function(o){
if(o=="undefined"){
return String.value0f();
}
if(o instanceof Array){
if(arguments.length==1){
return o.join('');
}else{
var off=arguments[1];
var len=arguments[2];
var oo=new Array(len);
for(var i=0;i<len;i++){
oo[i]=o[off+i];
}
return oo.join('');
}
}
return""+o;
};

String.prototype.subSequence=function(beginIndex,endIndex){
return this.substring(beginIndex,endIndex);
};

String.prototype.compareToIgnoreCase=function(str){
if(str==null){
throw new NullPointerException();
}
var s1=this.toUpperCase();
var s2=str.toUpperCase();
if(s1==s2){
return 0;
}else{
var s1=this.toLowerCase();
var s2=str.toLowerCase();
if(s1==s2){
return 0;
}else if(s1>s2){
return 1;
}else{
return-1;
}
}
};

String.prototype.contentEquals=function(sb){
if(this.length!=sb.length()){
return false;
}
var v=sb.getValue();
var i=0;
var j=0;
var n=this.length;
while(n--!=0){
if(this.charCodeAt(i++)!=v[j++]){
return false;
}
}
return true;
};

String.prototype.getChars=function(srcBegin,srcEnd,dst,dstBegin){
if(srcBegin<0){
throw new StringIndexOutOfBoundsException(srcBegin);
}
if(srcEnd>this.length){
throw new StringIndexOutOfBoundsException(srcEnd);
}
if(srcBegin>srcEnd){
throw new StringIndexOutOfBoundsException(srcEnd-srcBegin);
}
if(dst==null){
throw new NullPointerException();
}
for(var i=0;i<srcEnd-srcBegin;i++){
dst[dstBegin+i]=this.charAt(srcBegin+i);
}
};
String.prototype.$concat=String.prototype.concat;
String.prototype.concat=function(s){
if(s==null){
throw new NullPointerException();
}
return this.$concat(s);
};

String.prototype.$lastIndexOf=String.prototype.lastIndexOf;
String.prototype.lastIndexOf=function(s,last){
if(last!=null&&last+this.length<=0){
return-1;
}
if(last!=null){
return this.$lastIndexOf(s,last);
}else{
return this.$lastIndexOf(s);
}
};

String.prototype.intern=function(){
return this.valueOf();
};
String.copyValueOf=String.prototype.copyValueOf=function(){
if(arguments.length==1){
return String.instantialize(arguments[0]);
}else{
return String.instantialize(arguments[0],arguments[1],arguments[2]);
}
};
String.indexOf=function(source,sourceOffset,sourceCount,
target,targetOffset,targetCount,fromIndex){
if(fromIndex>=sourceCount){
return(targetCount==0?sourceCount:-1);
}
if(fromIndex<0){
fromIndex=0;
}
if(targetCount==0){
return fromIndex;
}

var first=target[targetOffset];
var i=sourceOffset+fromIndex;
var max=sourceOffset+(sourceCount-targetCount);

startSearchForFirstChar:
while(true){

while(i<=max&&source[i]!=first){
i++;
}
if(i>max){
return-1;
}


var j=i+1;
var end=j+targetCount-1;
var k=targetOffset+1;
while(j<end){
if(source[j++]!=target[k++]){
i++;

continue startSearchForFirstChar;
}
}
return i-sourceOffset;
}
};

/*


String.instantialize=function(){
if(arguments.length==0){
return new String();
}else if(arguments.length==1){
var x=arguments[0];
if(typeof x=="string"||x instanceof String){
return new String(x);
}else if(x instanceof Array){
if(x.length>0&&typeof x[0]=="number"){
var arr=new Array(x.length);
for(var i=0;i<x.length;i++){
arr[i]=String.fromCharCode(x[i]&0xff);
}
return Encoding.readUTF8(arr.join(''));
}
return x.join('');
}else if(x.__CLASS_NAME__=="StringBuffer"
||x.__CLASS_NAME__=="java.lang.StringBuffer"){
var value=x.shareValue();
var length=x.length();
var valueCopy=new Array(length);
for(var i=0;i<length;i++){
valueCopy[i]=value[i];
}
return valueCopy.join('')

}else{
return""+x;
}
}else if(arguments.length==2){
var x=arguments[0];
var hibyte=arguments[1];
if(typeof hibyte=="string"){
return String.instantialize(x,0,x.length,hibyte);
}else{
return String.instantialize(x,hibyte,0,x.length);
}
}else if(arguments.length==3){
var bytes=arguments[0];
var offset=arguments[1];
var length=arguments[2];
if(arguments[2]instanceof Array){
bytes=arguments[2];
offset=arguments[0];
length=arguments[1];
}
var arr=new Array(length);
if(offset<0||length+offset>bytes.length){
throw new IndexOutOfBoundsException();
}
if(length>0){
var isChar=(bytes[offset].length!=null);
if(isChar){
for(var i=0;i<length;i++){
arr[i]=bytes[offset+i];
}
}else{
for(var i=0;i<length;i++){
arr[i]=String.fromCharCode(bytes[offset+i]);
}
}
}
return arr.join('');
}else if(arguments.length==4){
var bytes=arguments[0];
var y=arguments[3];
if(typeof y=="string"||y instanceof String){
var offset=arguments[1];
var length=arguments[2];
var arr=new Array(length);
for(var i=0;i<length;i++){
arr[i]=bytes[offset+i];
if(typeof arr[i]=="number"){
arr[i]=String.fromCharCode(arr[i]&0xff);
}
}
var cs=y.toLowerCase();
if(cs=="utf-8"||cs=="utf8"){
return Encoding.readUTF8(arr.join(''));
}else{
return arr.join('');
}
}else{
var count=arguments[3];
var offset=arguments[2];
var hibyte=arguments[1];
var value=new Array(count);
if(hibyte==0){
for(var i=count;i-->0;){
value[i]=String.fromCharCode(bytes[i+offset]&0xff);
}
}else{
hibyte<<=8;
for(var i=count;i-->0;){
value[i]=String.fromCharCode(hibyte|(bytes[i+offset]&0xff));
}
}
return value.join('');
}
}else{
var s="";
for(var i=0;i<arguments.length;i++){
s+=arguments[i];
}
return s;
}
};


*/


String.instantialize=function(){
switch (arguments.length) {
case 0:
	return new String();
case 1:
	var x=arguments[0];
	if(typeof x=="string"||x instanceof String){
		return new String(x);
	}
	if(x instanceof Array || x instanceof Int32Array){
		if(x.length>0&&typeof x[0]=="number"){
			var arr=new Array(x.length);
			for(var i=0;i<x.length;i++){
				arr[i]=String.fromCharCode(x[i]&0xff);
			}
			return Encoding.readUTF8(arr.join(''));
		}
		return x.join('');
	}
	if(x.__CLASS_NAME__=="StringBuffer"||x.__CLASS_NAME__=="java.lang.StringBuffer"){
		var value=x.shareValue();
		var length=x.length();
		var valueCopy=new Array(length);
		for(var i=0;i<length;i++){
			valueCopy[i]=value[i];
		}
		return valueCopy.join('')
	}
	return""+x;
case 2:	
	var x=arguments[0];
	var hibyte=arguments[1];
	if(typeof hibyte=="string"){
		return String.instantialize(x,0,x.length,hibyte);
	}
	return String.instantialize(x,hibyte,0,x.length);
case 3:
	var bytes=arguments[0];
	var offset=arguments[1];
	var length=arguments[2];
	if(arguments[2]instanceof Array){
		bytes=arguments[2];
		offset=arguments[0];
		length=arguments[1];
	}
	var arr=new Array(length);
	if(offset<0||length+offset>bytes.length){
		throw new IndexOutOfBoundsException();
	}
	if(length>0){
		var isChar=(bytes[offset].length!=null);
		if(isChar){
			for(var i=0;i<length;i++){
				arr[i]=bytes[offset+i];
			}
		}else{
			for(var i=0;i<length;i++){
				arr[i]=String.fromCharCode(bytes[offset+i]);
			}
		}
	}
	return arr.join('');
case 4:
	var bytes=arguments[0];
	var y=arguments[3];
	if(typeof y=="string"||y instanceof String){
		var offset=arguments[1];
		var length=arguments[2];
		var arr=new Array(length);
		for(var i=0;i<length;i++){
			arr[i]=bytes[offset+i];
			if(typeof arr[i]=="number"){
				arr[i]=String.fromCharCode(arr[i]&0xff);
			}
		}
		var cs=y.toLowerCase();
		if(cs=="utf-8"||cs=="utf8"){
			return Encoding.readUTF8(arr.join(''));
		}
		return arr.join('');
	}
	var count=arguments[3];
	var offset=arguments[2];
	var hibyte=arguments[1];
	var value=new Array(count);
	if(hibyte==0){
		for(var i=count;i-->0;){
			value[i]=String.fromCharCode(bytes[i+offset]&0xff);
		}
	}else{
		hibyte<<=8;
		for(var i=count;i-->0;){
			value[i]=String.fromCharCode(hibyte|(bytes[i+offset]&0xff));
		}
	}
	return value.join('');
default:
	var s="";
	for(var i=0;i<arguments.length;i++){
		s+=arguments[i];
	}
	return s;
}
};


if(navigator.userAgent.toLowerCase().indexOf("chrome")!=-1){
String.prototype.toString=function(){
return this;
};
}

}
c$=$_C(function(){
this.value=0;
$_Z(this,arguments);
},java.lang,"Character",null,[java.io.Serializable,Comparable]);
$_K(c$,
function(value){
this.value=value;
},"~N");
$_M(c$,"charValue",
function(){
return this.value;
});
$_V(c$,"hashCode",
function(){
return(this.value).charCodeAt(0);
});
$_V(c$,"equals",
function(obj){
if($_O(obj,Character)){
return(this.value).charCodeAt(0)==((obj).charValue()).charCodeAt(0);
}return false;
},"~O");
$_V(c$,"compareTo",
function(c){
return(this.value).charCodeAt(0)-(c.value).charCodeAt(0);
},"Character");
c$.toLowerCase=$_M(c$,"toLowerCase",
function(c){
return(""+c).toLowerCase().charAt(0);
},"~N");
c$.toUpperCase=$_M(c$,"toUpperCase",
function(c){
return(""+c).toUpperCase().charAt(0);
},"~N");
c$.isDigit=$_M(c$,"isDigit",
function(c){
if(('0').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('9').charCodeAt(0))return true;
if((c).charCodeAt(0)<1632)return false;
return false;
},"~N");
c$.isUpperCase=$_M(c$,"isUpperCase",
function(c){
if(('A').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('Z').charCodeAt(0)){
return true;
}return false;
},"~N");
c$.isLowerCase=$_M(c$,"isLowerCase",
function(c){
if(('a').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('z').charCodeAt(0)){
return true;
}return false;
},"~N");
c$.isWhitespace=$_M(c$,"isWhitespace",
function(c){
if(((c).charCodeAt(0)>=0x1c&&(c).charCodeAt(0)<=0x20)||((c).charCodeAt(0)>=0x9&&(c).charCodeAt(0)<=0xd))return true;
if((c).charCodeAt(0)==0x1680)return true;
if((c).charCodeAt(0)<0x2000||(c).charCodeAt(0)==0x2007)return false;
return(c).charCodeAt(0)<=0x200b||(c).charCodeAt(0)==0x2028||(c).charCodeAt(0)==0x2029||(c).charCodeAt(0)==0x3000;
},"~N");
c$.isLetter=$_M(c$,"isLetter",
function(c){
if((('A').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('Z').charCodeAt (0)) || (('a').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('z').charCodeAt(0)))return true;
if((c).charCodeAt(0)<128)return false;
return false;
},"~N");
c$.isLetterOrDigit=$_M(c$,"isLetterOrDigit",
function(c){
return Character.isLetter(c)||Character.isDigit(c);
},"~N");
c$.isSpaceChar=$_M(c$,"isSpaceChar",
function(c){
if((c).charCodeAt(0)==0x20||(c).charCodeAt(0)==0xa0||(c).charCodeAt(0)==0x1680)return true;
if((c).charCodeAt(0)<0x2000)return false;
return(c).charCodeAt(0)<=0x200b||(c).charCodeAt(0)==0x2028||(c).charCodeAt(0)==0x2029||(c).charCodeAt(0)==0x202f||(c).charCodeAt(0)==0x3000;
},"~N");
c$.digit=$_M(c$,"digit",
function(c,radix){
if(radix>=2&&radix<=36){
if((c).charCodeAt(0)<128){
var result=-1;
if(('0').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('9').charCodeAt(0)){
result=(c).charCodeAt(0)-('0').charCodeAt(0);
}else if(('a').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('z').charCodeAt(0)){
result=(c).charCodeAt(0)-(87);
}else if(('A').charCodeAt (0) <= (c).charCodeAt (0) && (c).charCodeAt (0) <= ('Z').charCodeAt(0)){
result=(c).charCodeAt(0)-(55);
}return result<radix?result:-1;
}}return-1;
},"~N,~N");
$_V(c$,"toString",
function(){
var buf=[this.value];
return String.valueOf(buf);
});
c$.toString=$_V(c$,"toString",
function(c){
{
if(this===Character){
return"class java.lang.Character";
}
}return String.valueOf(c);
},"~N");
$_S(c$,
"MIN_VALUE",'\u0000',
"MAX_VALUE",'\uffff',
"MIN_RADIX",2,
"MAX_RADIX",36,
"TYPE",null);

java.lang.Character.TYPE=java.lang.Character.prototype.TYPE=java.lang.Character;
Array.getComponentType=function(){
return Object;
};c$=$_T(java.lang.reflect,"Array");
c$.newInstance=$_M(c$,"newInstance",
function(componentType,size){
return $_A(length);
},"Class,~N");

java.util.Date=Date;
Clazz_decorateAsType(java.util.Date,"java.util.Date",null,[java.io.Serializable,Cloneable,Comparable]);

$_M(java.util.Date,"clone",
function(){
return new Date(this.getTime());
});

$_M(java.util.Date,"before",
function(when){
return this.getTime()<when.getTime();
},"java.util.Date");
$_M(java.util.Date,"after",
function(when){
return this.getTime()>when.getTime();
},"java.util.Date");
$_M(java.util.Date,"equals",
function(obj){
return Clazz_instanceOf(obj,java.util.Date)&&this.getTime()==(obj).getTime();
},"Object");
$_M(java.util.Date,"compareTo",
function(anotherDate){
var thisTime=this.getTime();
var anotherTime=anotherDate.getTime();
return(thisTime<anotherTime?-1:(thisTime==anotherTime?0:1));
},"java.util.Date");
$_M(java.util.Date,"compareTo",
function(o){
return this.compareTo(o);
},"Object");
$_V(java.util.Date,"hashCode",
function(){
var ht=this.getTime();
return parseInt(ht)^parseInt((ht>>32));
});

c$=$_C(function(){
this.source=null;
$_Z(this,arguments);
},java.util,"EventObject",null,java.io.Serializable);
$_K(c$,
function(source){
if(source!=null)this.source=source;
else throw new IllegalArgumentException();
},"~O");
$_M(c$,"getSource",
function(){
return this.source;
});
$_V(c$,"toString",
function(){
return this.getClass().getName()+"[source="+String.valueOf(this.source)+']';
});
$_I(java.util,"EventListener");

c$=$_C(function(){
this.listener=null;
$_Z(this,arguments);
},java.util,"EventListenerProxy",null,java.util.EventListener);
$_K(c$,
function(listener){
this.listener=listener;
},"java.util.EventListener");
$_M(c$,"getListener",
function(){
return this.listener;
});
$_I(java.util,"Iterator");

$_I(java.util,"ListIterator",java.util.Iterator);
$_I(java.util,"Enumeration");
$_I(java.util,"Collection",Iterable);

$_I(java.util,"Set",java.util.Collection);
$_I(java.util,"Map");
$_I(java.util.Map,"Entry");

$_I(java.util,"List",java.util.Collection);

$_I(java.util,"Queue",java.util.Collection);
$_I(java.util,"RandomAccess");
c$=$_C(function(){
this.detailMessage=null;
this.cause=null;
this.stackTrace=null;
$_Z(this,arguments);
},java.lang,"Throwable",null,java.io.Serializable);
$_Y(c$,function(){
this.cause=this;
//alert("e0 "+ arguments.callee.caller.caller.caller.caller.caller)
});
$_K(c$,
function(){
this.fillInStackTrace();
});
$_K(c$,
function(message){
this.fillInStackTrace();
this.detailMessage=message;
},"~S");
$_K(c$,
function(message,cause){
this.fillInStackTrace();
this.detailMessage=message;
this.cause=cause;
},"~S,Throwable");
$_K(c$,
function(cause){
this.fillInStackTrace();
this.detailMessage=(cause==null?null:cause.toString());
this.cause=cause;
},"Throwable");
$_M(c$,"getMessage",
function(){
{
if(typeof this.message!="undefined"){
return this.message;
}
}return this.detailMessage;
});
$_M(c$,"getLocalizedMessage",
function(){
return this.getMessage();
});
$_M(c$,"getCause",
function(){
return(this.cause===this?null:this.cause);
});
$_M(c$,"initCause",
function(cause){
if(this.cause!==this)throw new IllegalStateException("Can't overwrite cause");
if(cause===this)throw new IllegalArgumentException("Self-causation not permitted");
this.cause=cause;
return this;
},"Throwable");
$_V(c$,"toString",
function(){
var s=this.getClass().getName();
var message=this.getLocalizedMessage();
return(message!=null)?(s+": "+message):s;
});
$_M(c$,"printStackTrace",
function(){
System.err.println(this);
for(var i=0;i<this.stackTrace.length;i++){
var t=this.stackTrace[i];
var x=t.methodName.indexOf("(");
var n=t.methodName.substring(0,x).replace(/\s+/g,"");
if(n!="construct"||t.nativeClazz==null
||Clazz_getInheritedLevel(t.nativeClazz,Throwable)<0){
System.err.println(t);
}
}
});
$_M(c$,"printStackTrace",
function(s){
this.printStackTrace();
},"java.io.PrintStream");
$_M(c$,"printStackTrace",
function(s){
this.printStackTrace();
},"java.io.PrintWriter");
$_M(c$,"fillInStackTrace",
function(){
this.stackTrace=new Array();
var caller=arguments.callee.caller;
var superCaller=null;
var callerList=new Array();
var index=Clazz_callingStackTraces.length-1;
var noLooping=true;
while(index>-1||caller!=null){
var clazzName=null;
var nativeClass=null;
if(!noLooping||caller==Clazz_tryToSearchAndExecute||caller==$_U||caller==null){
if(index<0){
break;
}
noLooping=true;
superCaller=Clazz_callingStackTraces[index].caller;
nativeClass=Clazz_callingStackTraces[index].owner;
index--;
}else{
superCaller=caller;
if(superCaller.claxxOwner!=null){
nativeClass=superCaller.claxxOwner;
}else if(superCaller.exClazz!=null){
nativeClass=superCaller.exClazz;
}
}
var st=new StackTraceElement(
((nativeClass!=null&&nativeClass.__CLASS_NAME__.length!=0)?
nativeClass.__CLASS_NAME__:"anonymous"),
((superCaller.exName==null)?"anonymous":superCaller.exName)
+" ("+Clazz_getParamsType(superCaller.arguments)+")",
null,-1);
st.nativeClazz=nativeClass;
this.stackTrace[this.stackTrace.length]=st;
for(var i=0;i<callerList.length;i++){
if(callerList[i]==superCaller){

var st=new StackTraceElement("lost","missing",null,-3);
st.nativeClazz=null;
this.stackTrace[this.stackTrace.length]=st;
noLooping=false;

}
}
if(superCaller!=null){
callerList[callerList.length]=superCaller;
}
caller=superCaller.arguments.callee.caller;
}
Clazz_initializingException=false;
return this;
});
$_M(c$,"setStackTrace",
function(stackTrace){
var defensiveCopy=stackTrace.clone();
for(var i=0;i<defensiveCopy.length;i++)if(defensiveCopy[i]==null)throw new NullPointerException("stackTrace["+i+"]");

this.stackTrace=defensiveCopy;
},"~A");

c$=$_C(function(){
this.declaringClass=null;
this.methodName=null;
this.fileName=null;
this.lineNumber=0;
$_Z(this,arguments);
},java.lang,"StackTraceElement",null,java.io.Serializable);
$_K(c$,
function(cls,method,file,line){
if(cls==null||method==null){
throw new NullPointerException();
}this.declaringClass=cls;
this.methodName=method;
this.fileName=file;
this.lineNumber=line;
},"~S,~S,~S,~N");
$_V(c$,"equals",
function(obj){
if(!($_O(obj,StackTraceElement))){
return false;
}var castObj=obj;
if((this.methodName==null)||(castObj.methodName==null)){
return false;
}if(!this.getMethodName().equals(castObj.getMethodName())){
return false;
}if(!this.getClassName().equals(castObj.getClassName())){
return false;
}var localFileName=this.getFileName();
if(localFileName==null){
if(castObj.getFileName()!=null){
return false;
}}else{
if(!localFileName.equals(castObj.getFileName())){
return false;
}}if(this.getLineNumber()!=castObj.getLineNumber()){
return false;
}return true;
},"~O");
$_M(c$,"getClassName",
function(){
return(this.declaringClass==null)?"<unknown class>":this.declaringClass;
});
$_M(c$,"getFileName",
function(){
return this.fileName;
});
$_M(c$,"getLineNumber",
function(){
return this.lineNumber;
});
$_M(c$,"getMethodName",
function(){
return(this.methodName==null)?"<unknown method>":this.methodName;
});
$_V(c$,"hashCode",
function(){
if(this.methodName==null){
return 0;
}return this.methodName.hashCode()^this.declaringClass.hashCode();
});
$_M(c$,"isNativeMethod",
function(){
return this.lineNumber==-2;
});
$_V(c$,"toString",
function(){
var buf=new StringBuilder(80);
buf.append(this.getClassName());
buf.append('.');
buf.append(this.getMethodName());
if(this.isNativeMethod()){
buf.append("(Native Method)");
}else{
var fName=this.getFileName();
if(fName==null){
buf.append("(Unknown Source)");
}else{
var lineNum=this.getLineNumber();
buf.append('(');
buf.append(fName);
if(lineNum>=0){
buf.append(':');
buf.append(lineNum);
}buf.append(')');
}}return buf.toString();
});

c$=$_T(java.lang,"Error",Throwable);

c$=$_T(java.lang,"LinkageError",Error);

c$=$_T(java.lang,"IncompatibleClassChangeError",LinkageError);

c$=$_T(java.lang,"AbstractMethodError",IncompatibleClassChangeError);

c$=$_T(java.lang,"AssertionError",Error);
$_K(c$,
function(detailMessage){
$_R(this,AssertionError,[String.valueOf(detailMessage),($_O(detailMessage,Throwable)?detailMessage:null)]);
},"~O");
$_K(c$,
function(detailMessage){
this.construct(String.valueOf(detailMessage));
},"~B");
$_K(c$,
function(detailMessage){
this.construct(String.valueOf(detailMessage));
},"~N");
$_K(c$,
function(detailMessage){
this.construct(Integer.toString(detailMessage));
},"~N");
$_K(c$,
function(detailMessage){
this.construct(Long.toString(detailMessage));
},"~N");
$_K(c$,
function(detailMessage){
this.construct(Float.toString(detailMessage));
},"~N");
$_K(c$,
function(detailMessage){
this.construct(Double.toString(detailMessage));
},"~N");

c$=$_T(java.lang,"ClassCircularityError",LinkageError);

c$=$_T(java.lang,"ClassFormatError",LinkageError);

c$=$_C(function(){
this.exception=null;
$_Z(this,arguments);
},java.lang,"ExceptionInInitializerError",LinkageError);
$_K(c$,
function(){
$_R(this,ExceptionInInitializerError);
this.initCause(null);
});
$_K(c$,
function(detailMessage){
$_R(this,ExceptionInInitializerError,[detailMessage]);
this.initCause(null);
},"~S");
$_K(c$,
function(exception){
$_R(this,ExceptionInInitializerError);
this.exception=exception;
this.initCause(exception);
},"Throwable");
$_M(c$,"getException",
function(){
return this.exception;
});
$_V(c$,"getCause",
function(){
return this.exception;
});

c$=$_T(java.lang,"IllegalAccessError",IncompatibleClassChangeError);

c$=$_T(java.lang,"InstantiationError",IncompatibleClassChangeError);

c$=$_T(java.lang,"VirtualMachineError",Error);

c$=$_T(java.lang,"InternalError",VirtualMachineError);

c$=$_T(java.lang,"NoClassDefFoundError",LinkageError);

c$=$_T(java.lang,"NoSuchFieldError",IncompatibleClassChangeError);

c$=$_T(java.lang,"NoSuchMethodError",IncompatibleClassChangeError);

c$=$_T(java.lang,"OutOfMemoryError",VirtualMachineError);

c$=$_T(java.lang,"StackOverflowError",VirtualMachineError);

c$=$_T(java.lang,"UnknownError",VirtualMachineError);

c$=$_T(java.lang,"UnsatisfiedLinkError",LinkageError);

c$=$_T(java.lang,"UnsupportedClassVersionError",ClassFormatError);

c$=$_T(java.lang,"VerifyError",LinkageError);

c$=$_T(java.lang,"ThreadDeath",Error);
$_K(c$,
function(){
$_R(this,ThreadDeath,[]);
});

c$=$_T(java.lang,"Exception",Throwable);

c$=$_T(java.lang,"RuntimeException",Exception);

c$=$_T(java.lang,"ArithmeticException",RuntimeException);

c$=$_T(java.lang,"IndexOutOfBoundsException",RuntimeException);

c$=$_T(java.lang,"ArrayIndexOutOfBoundsException",IndexOutOfBoundsException);
$_K(c$,
function(index){
$_R(this,ArrayIndexOutOfBoundsException,["Array index out of range: "+index]);
},"~N");

c$=$_T(java.lang,"ArrayStoreException",RuntimeException);

c$=$_T(java.lang,"ClassCastException",RuntimeException);

c$=$_C(function(){
this.ex=null;
$_Z(this,arguments);
},java.lang,"ClassNotFoundException",Exception);
$_K(c$,
function(){
$_R(this,ClassNotFoundException,[Clazz_castNullAs("Throwable")]);
});
$_K(c$,
function(detailMessage){
$_R(this,ClassNotFoundException,[detailMessage,null]);
},"~S");
$_K(c$,
function(detailMessage,exception){
$_R(this,ClassNotFoundException,[detailMessage]);
this.ex=exception;
},"~S,Throwable");
$_M(c$,"getException",
function(){
return this.ex;
});
$_V(c$,"getCause",
function(){
return this.ex;
});

c$=$_T(java.lang,"CloneNotSupportedException",Exception);

c$=$_T(java.lang,"IllegalAccessException",Exception);

c$=$_T(java.lang,"IllegalArgumentException",RuntimeException);
$_K(c$,
function(cause){
$_R(this,IllegalArgumentException,[(cause==null?null:cause.toString()),cause]);
},"Throwable");

c$=$_T(java.lang,"IllegalMonitorStateException",RuntimeException);

c$=$_T(java.lang,"IllegalStateException",RuntimeException);
$_K(c$,
function(cause){
$_R(this,IllegalStateException,[(cause==null?null:cause.toString()),cause]);
},"Throwable");

c$=$_T(java.lang,"IllegalThreadStateException",IllegalArgumentException);

c$=$_T(java.lang,"InstantiationException",Exception);

c$=$_T(java.lang,"InterruptedException",Exception);

c$=$_T(java.lang,"NegativeArraySizeException",RuntimeException);

c$=$_T(java.lang,"NoSuchFieldException",Exception);

c$=$_T(java.lang,"NoSuchMethodException",Exception);

c$=$_T(java.lang,"NullPointerException",RuntimeException);

c$=$_T(java.lang,"NumberFormatException",IllegalArgumentException);

c$=$_T(java.lang,"SecurityException",RuntimeException);
$_K(c$,
function(cause){
$_R(this,SecurityException,[(cause==null?null:cause.toString()),cause]);
},"Throwable");

c$=$_T(java.lang,"StringIndexOutOfBoundsException",IndexOutOfBoundsException);
$_K(c$,
function(index){
$_R(this,StringIndexOutOfBoundsException,["String index out of range: "+index]);
},"~N");

c$=$_T(java.lang,"UnsupportedOperationException",RuntimeException);
$_K(c$,
function(){
$_R(this,UnsupportedOperationException,[]);
});
$_K(c$,
function(cause){
$_R(this,UnsupportedOperationException,[(cause==null?null:cause.toString()),cause]);
},"Throwable");

c$=$_C(function(){
this.target=null;
$_Z(this,arguments);
},java.lang.reflect,"InvocationTargetException",Exception);
$_K(c$,
function(){
$_R(this,java.lang.reflect.InvocationTargetException,[Clazz_castNullAs("Throwable")]);
});
$_K(c$,
function(exception){
$_R(this,java.lang.reflect.InvocationTargetException,[null,exception]);
this.target=exception;
},"Throwable");
$_K(c$,
function(exception,detailMessage){
$_R(this,java.lang.reflect.InvocationTargetException,[detailMessage,exception]);
this.target=exception;
},"Throwable,~S");
$_M(c$,"getTargetException",
function(){
return this.target;
});
$_V(c$,"getCause",
function(){
return this.target;
});

c$=$_C(function(){
this.undeclaredThrowable=null;
$_Z(this,arguments);
},java.lang.reflect,"UndeclaredThrowableException",RuntimeException);
$_K(c$,
function(exception){
$_R(this,java.lang.reflect.UndeclaredThrowableException);
this.undeclaredThrowable=exception;
this.initCause(exception);
},"Throwable");
$_K(c$,
function(exception,detailMessage){
$_R(this,java.lang.reflect.UndeclaredThrowableException,[detailMessage]);
this.undeclaredThrowable=exception;
this.initCause(exception);
},"Throwable,~S");
$_M(c$,"getUndeclaredThrowable",
function(){
return this.undeclaredThrowable;
});
$_V(c$,"getCause",
function(){
return this.undeclaredThrowable;
});

c$=$_T(java.io,"IOException",Exception);


c$=$_T(java.io,"CharConversionException",java.io.IOException);

c$=$_T(java.io,"EOFException",java.io.IOException);

c$=$_T(java.io,"FileNotFoundException",java.io.IOException);

c$=$_C(function(){
this.bytesTransferred=0;
$_Z(this,arguments);
},java.io,"InterruptedIOException",java.io.IOException);

c$=$_T(java.io,"ObjectStreamException",java.io.IOException);

c$=$_C(function(){
this.classname=null;
$_Z(this,arguments);
},java.io,"InvalidClassException",java.io.ObjectStreamException);
$_K(c$,
function(className,detailMessage){
$_R(this,java.io.InvalidClassException,[detailMessage]);
this.classname=className;
},"~S,~S");
$_M(c$,"getMessage",
function(){
var msg=$_U(this,java.io.InvalidClassException,"getMessage",[]);
if(this.classname!=null){
msg=this.classname+';' + ' '+msg;
}return msg;
});

c$=$_T(java.io,"InvalidObjectException",java.io.ObjectStreamException);

c$=$_T(java.io,"NotActiveException",java.io.ObjectStreamException);

c$=$_T(java.io,"NotSerializableException",java.io.ObjectStreamException);

c$=$_C(function(){
this.eof=false;
this.length=0;
$_Z(this,arguments);
},java.io,"OptionalDataException",java.io.ObjectStreamException);

c$=$_T(java.io,"StreamCorruptedException",java.io.ObjectStreamException);

c$=$_T(java.io,"SyncFailedException",java.io.IOException);

c$=$_T(java.io,"UnsupportedEncodingException",java.io.IOException);

c$=$_T(java.io,"UTFDataFormatException",java.io.IOException);

c$=$_C(function(){
this.detail=null;
$_Z(this,arguments);
},java.io,"WriteAbortedException",java.io.ObjectStreamException);
$_K(c$,
function(detailMessage,rootCause){
$_R(this,java.io.WriteAbortedException,[detailMessage]);
this.detail=rootCause;
this.initCause(rootCause);
},"~S,Exception");
$_M(c$,"getMessage",
function(){
var msg=$_U(this,java.io.WriteAbortedException,"getMessage",[]);
if(this.detail!=null){
msg=msg+"; "+this.detail.toString();
}return msg;
});
$_V(c$,"getCause",
function(){
return this.detail;
});

c$=$_T(java.util,"ConcurrentModificationException",RuntimeException);
$_K(c$,
function(){
$_R(this,java.util.ConcurrentModificationException,[]);
});

c$=$_T(java.util,"EmptyStackException",RuntimeException);

c$=$_C(function(){
this.className=null;
this.key=null;
$_Z(this,arguments);
},java.util,"MissingResourceException",RuntimeException);
$_K(c$,
function(detailMessage,className,resourceName){
$_R(this,java.util.MissingResourceException,[detailMessage]);
this.className=className;
this.key=resourceName;
},"~S,~S,~S");
$_M(c$,"getClassName",
function(){
return this.className;
});
$_M(c$,"getKey",
function(){
return this.key;
});

c$=$_T(java.util,"NoSuchElementException",RuntimeException);

c$=$_T(java.util,"TooManyListenersException",Exception);

c$=$_T(java.lang,"Void");
$_S(c$,
"TYPE",null);
{
java.lang.Void.TYPE=java.lang.Void;
}$_I(java.lang.reflect,"GenericDeclaration");
$_I(java.lang.reflect,"AnnotatedElement");

c$=$_T(java.lang.reflect,"AccessibleObject",null,java.lang.reflect.AnnotatedElement);
$_K(c$,
function(){
});
$_M(c$,"isAccessible",
function(){
return false;
});
c$.setAccessible=$_M(c$,"setAccessible",
function(objects,flag){
return;
},"~A,~B");
$_M(c$,"setAccessible",
function(flag){
return;
},"~B");
$_V(c$,"isAnnotationPresent",
function(annotationType){
return false;
},"Class");
$_V(c$,"getDeclaredAnnotations",
function(){
return new Array(0);
});
$_V(c$,"getAnnotations",
function(){
return new Array(0);
});
$_V(c$,"getAnnotation",
function(annotationType){
return null;
},"Class");
c$.marshallArguments=$_M(c$,"marshallArguments",
function(parameterTypes,args){
return null;
},"~A,~A");
$_M(c$,"invokeV",
function(receiver,args){
return;
},"~O,~A");
$_M(c$,"invokeL",
function(receiver,args){
return null;
},"~O,~A");
$_M(c$,"invokeI",
function(receiver,args){
return 0;
},"~O,~A");
$_M(c$,"invokeJ",
function(receiver,args){
return 0;
},"~O,~A");
$_M(c$,"invokeF",
function(receiver,args){
return 0.0;
},"~O,~A");
$_M(c$,"invokeD",
function(receiver,args){
return 0.0;
},"~O,~A");
c$.emptyArgs=c$.prototype.emptyArgs=new Array(0);
$_I(java.lang.reflect,"InvocationHandler");
c$=$_I(java.lang.reflect,"Member");
$_S(c$,
"PUBLIC",0,
"DECLARED",1);

c$=$_T(java.lang.reflect,"Modifier");
$_K(c$,
function(){
});
c$.isAbstract=$_M(c$,"isAbstract",
function(modifiers){
return((modifiers&1024)!=0);
},"~N");
c$.isFinal=$_M(c$,"isFinal",
function(modifiers){
return((modifiers&16)!=0);
},"~N");
c$.isInterface=$_M(c$,"isInterface",
function(modifiers){
return((modifiers&512)!=0);
},"~N");
c$.isNative=$_M(c$,"isNative",
function(modifiers){
return((modifiers&256)!=0);
},"~N");
c$.isPrivate=$_M(c$,"isPrivate",
function(modifiers){
return((modifiers&2)!=0);
},"~N");
c$.isProtected=$_M(c$,"isProtected",
function(modifiers){
return((modifiers&4)!=0);
},"~N");
c$.isPublic=$_M(c$,"isPublic",
function(modifiers){
return((modifiers&1)!=0);
},"~N");
c$.isStatic=$_M(c$,"isStatic",
function(modifiers){
return((modifiers&8)!=0);
},"~N");
c$.isStrict=$_M(c$,"isStrict",
function(modifiers){
return((modifiers&2048)!=0);
},"~N");
c$.isSynchronized=$_M(c$,"isSynchronized",
function(modifiers){
return((modifiers&32)!=0);
},"~N");
c$.isTransient=$_M(c$,"isTransient",
function(modifiers){
return((modifiers&128)!=0);
},"~N");
c$.isVolatile=$_M(c$,"isVolatile",
function(modifiers){
return((modifiers&64)!=0);
},"~N");
c$.toString=$_M(c$,"toString",
function(modifiers){
var sb=new Array(0);
if(java.lang.reflect.Modifier.isPublic(modifiers))sb[sb.length]="public";
if(java.lang.reflect.Modifier.isProtected(modifiers))sb[sb.length]="protected";
if(java.lang.reflect.Modifier.isPrivate(modifiers))sb[sb.length]="private";
if(java.lang.reflect.Modifier.isAbstract(modifiers))sb[sb.length]="abstract";
if(java.lang.reflect.Modifier.isStatic(modifiers))sb[sb.length]="static";
if(java.lang.reflect.Modifier.isFinal(modifiers))sb[sb.length]="final";
if(java.lang.reflect.Modifier.isTransient(modifiers))sb[sb.length]="transient";
if(java.lang.reflect.Modifier.isVolatile(modifiers))sb[sb.length]="volatile";
if(java.lang.reflect.Modifier.isSynchronized(modifiers))sb[sb.length]="synchronized";
if(java.lang.reflect.Modifier.isNative(modifiers))sb[sb.length]="native";
if(java.lang.reflect.Modifier.isStrict(modifiers))sb[sb.length]="strictfp";
if(java.lang.reflect.Modifier.isInterface(modifiers))sb[sb.length]="interface";
if(sb.length>0){
return sb.join(" ");
}return"";
},"~N");
$_S(c$,
"PUBLIC",0x1,
"PRIVATE",0x2,
"PROTECTED",0x4,
"STATIC",0x8,
"FINAL",0x10,
"SYNCHRONIZED",0x20,
"VOLATILE",0x40,
"TRANSIENT",0x80,
"NATIVE",0x100,
"INTERFACE",0x200,
"ABSTRACT",0x400,
"STRICT",0x800,
"BRIDGE",0x40,
"VARARGS",0x80,
"SYNTHETIC",0x1000,
"ANNOTATION",0x2000,
"ENUM",0x4000);

c$=$_C(function(){
this.clazz=null;
this.parameterTypes=null;
this.exceptionTypes=null;
this.modifiers=0;
$_Z(this,arguments);
},java.lang.reflect,"Constructor",java.lang.reflect.AccessibleObject,[java.lang.reflect.GenericDeclaration,java.lang.reflect.Member]);
$_K(c$,
function(declaringClass,parameterTypes,checkedExceptions,modifiers){
$_R(this,java.lang.reflect.Constructor,[]);
this.clazz=declaringClass;
this.parameterTypes=parameterTypes;
this.exceptionTypes=checkedExceptions;
this.modifiers=modifiers;
},"Class,~A,~A,~N");
$_V(c$,"getTypeParameters",
function(){
return null;
});
$_M(c$,"toGenericString",
function(){
return null;
});
$_M(c$,"getGenericParameterTypes",
function(){
return null;
});
$_M(c$,"getGenericExceptionTypes",
function(){
return null;
});
$_M(c$,"getParameterAnnotations",
function(){
return null;
});
$_M(c$,"isVarArgs",
function(){
return false;
});
$_V(c$,"isSynthetic",
function(){
return false;
});
$_V(c$,"equals",
function(object){
if(object!=null&&$_O(object,java.lang.reflect.Constructor)){
var other=object;
if(this.getDeclaringClass()===other.getDeclaringClass()){
var params1=this.parameterTypes;
var params2=other.parameterTypes;
if(params1.length==params2.length){
for(var i=0;i<params1.length;i++){
if(params1[i]!==params2[i])return false;
}
return true;
}}}return false;
},"~O");
$_V(c$,"getDeclaringClass",
function(){
return this.clazz;
});
$_M(c$,"getExceptionTypes",
function(){
return this.exceptionTypes;
});
$_V(c$,"getModifiers",
function(){
return this.modifiers;
});
$_V(c$,"getName",
function(){
return this.getDeclaringClass().getName();
});
$_M(c$,"getParameterTypes",
function(){
return this.parameterTypes;
});
$_V(c$,"hashCode",
function(){
return this.getDeclaringClass().getName().hashCode();
});
$_M(c$,"newInstance",
function(args){
var instance=new this.clazz($_G);
$_Z(instance,args);
return instance;
},"~A");
$_V(c$,"toString",
function(){
return null;
});

c$=$_T(java.lang.reflect,"Field",java.lang.reflect.AccessibleObject,java.lang.reflect.Member);
$_V(c$,"isSynthetic",
function(){
return false;
});
$_M(c$,"toGenericString",
function(){
return null;
});
$_M(c$,"isEnumConstant",
function(){
return false;
});
$_M(c$,"getGenericType",
function(){
return null;
});
$_V(c$,"equals",
function(object){
return false;
},"~O");
$_V(c$,"getDeclaringClass",
function(){
return null;
});
$_V(c$,"getName",
function(){
return null;
});
$_M(c$,"getType",
function(){
return null;
});
$_V(c$,"hashCode",
function(){
return 0;
});
$_V(c$,"toString",
function(){
return null;
});

c$=$_C(function(){
this.clazz=null;
this.name=null;
this.returnType=null;
this.parameterTypes=null;
this.exceptionTypes=null;
this.modifiers=0;
$_Z(this,arguments);
},java.lang.reflect,"Method",java.lang.reflect.AccessibleObject,[java.lang.reflect.GenericDeclaration,java.lang.reflect.Member]);
$_K(c$,
function(declaringClass,name,parameterTypes,returnType,checkedExceptions,modifiers){
$_R(this,java.lang.reflect.Method,[]);
this.clazz=declaringClass;
this.name=name;
this.parameterTypes=parameterTypes;
this.returnType=returnType;
this.exceptionTypes=checkedExceptions;
this.modifiers=modifiers;
},"Class,~S,~A,Class,~A,~N");
$_V(c$,"getTypeParameters",
function(){
return null;
});
$_M(c$,"toGenericString",
function(){
return null;
});
$_M(c$,"getGenericParameterTypes",
function(){
return null;
});
$_M(c$,"getGenericExceptionTypes",
function(){
return null;
});
$_M(c$,"getGenericReturnType",
function(){
return null;
});
$_M(c$,"getParameterAnnotations",
function(){
return null;
});
$_M(c$,"isVarArgs",
function(){
return false;
});
$_M(c$,"isBridge",
function(){
return false;
});
$_V(c$,"isSynthetic",
function(){
return false;
});
$_M(c$,"getDefaultValue",
function(){
return null;
});
$_V(c$,"equals",
function(object){
if(object!=null&&$_O(object,java.lang.reflect.Method)){
var other=object;
if((this.getDeclaringClass()===other.getDeclaringClass())&&(this.getName()===other.getName())){
var params1=this.parameterTypes;
var params2=other.parameterTypes;
if(params1.length==params2.length){
for(var i=0;i<params1.length;i++){
if(params1[i]!==params2[i])return false;
}
return true;
}}}return false;
},"~O");
$_V(c$,"getDeclaringClass",
function(){
return this.clazz;
});
$_M(c$,"getExceptionTypes",
function(){
return this.exceptionTypes;
});
$_V(c$,"getModifiers",
function(){
return this.modifiers;
});
$_V(c$,"getName",
function(){
return this.name;
});
$_M(c$,"getParameterTypes",
function(){
return this.parameterTypes;
});
$_M(c$,"getReturnType",
function(){
return this.returnType;
});
$_V(c$,"hashCode",
function(){
return this.getDeclaringClass().getName().hashCode()^this.getName().hashCode();
});
$_M(c$,"invoke",
function(receiver,args){
var m=this.clazz.prototype[this.getName()];
if(m==null){
m=this.clazz[this.getName()];
}
if(m!=null){
m.apply(receiver,args);
}else{

}
},"~O,~A");
$_V(c$,"toString",
function(){
return null;
});


;(function() {

if (Jmol._debugCode)return;

Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVAppletInterface");
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "AppletFrame");
Clazz_declarePackage ("JSV.appletjs");
Clazz_load (["JSV.api.AppletFrame", "$.JSVAppletInterface"], "JSV.appletjs.JSVApplet", ["java.lang.Boolean", "java.net.URL", "java.util.Hashtable", "JU.PT", "JSV.app.JSVApp", "JSV.common.JSVersion", "JSV.js2d.JsPanel", "$.JsViewPanel", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.app = null;
this.viewer = null;
this.isStandalone = false;
this.viewerOptions = null;
this.htParams = null;
Clazz_instantialize (this, arguments);
}, JSV.appletjs, "JSVApplet", null, [JSV.api.JSVAppletInterface, JSV.api.AppletFrame]);
Clazz_makeConstructor (c$, 
function (viewerOptions) {
if (viewerOptions == null) viewerOptions =  new java.util.Hashtable ();
this.viewerOptions = viewerOptions;
this.htParams =  new java.util.Hashtable ();
for (var entry, $entry = viewerOptions.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) this.htParams.put (entry.getKey ().toLowerCase (), entry.getValue ());

this.init ();
}, "java.util.Map");
$_M(c$, "init", 
function () {
this.app =  new JSV.app.JSVApp (this, true);
this.initViewer ();
if (this.app.appletReadyCallbackFunctionName != null && this.viewer.fullName != null) this.callToJavaScript (this.app.appletReadyCallbackFunctionName, [this.viewer.appletID, this.viewer.fullName, Boolean.TRUE, this]);
});
$_M(c$, "initViewer", 
function () {
this.viewer = this.app.viewer;
this.setLogging ();
this.viewerOptions.remove ("debug");
var o = this.viewerOptions.get ("display");
{
o = document.getElementById(o);
}this.viewer.setDisplay (o);
J.util.Logger.info (this.getAppletInfo ());
});
$_M(c$, "setLogging", 
function () {
var iLevel = (this.getValue ("logLevel", (this.getBooleanValue ("debug", false) ? "5" : "4"))).charCodeAt (0) - 48;
if (iLevel != 4) System.out.println ("setting logLevel=" + iLevel + " -- To change, use script \"set logLevel [0-5]\"");
J.util.Logger.setLogLevel (iLevel);
});
$_M(c$, "getParameter", 
function (paramName) {
var o = this.htParams.get (paramName.toLowerCase ());
return (o == null ? null :  String.instantialize (o.toString ()));
}, "~S");
$_M(c$, "getBooleanValue", 
function (propertyName, defaultValue) {
var value = this.getValue (propertyName, defaultValue ? "true" : "");
return (value.equalsIgnoreCase ("true") || value.equalsIgnoreCase ("on") || value.equalsIgnoreCase ("yes"));
}, "~S,~B");
$_M(c$, "getValue", 
function (propertyName, defaultValue) {
var stringValue = this.getParameter (propertyName);
System.out.println ("getValue " + propertyName + " = " + stringValue);
if (stringValue != null) return stringValue;
return defaultValue;
}, "~S,~S");
$_V(c$, "isPro", 
function () {
return this.app.isPro ();
});
$_V(c$, "isSigned", 
function () {
return this.app.isSigned ();
});
$_V(c$, "finalize", 
function () {
System.out.println ("JSpecView " + this + " finalized");
});
$_M(c$, "destroy", 
function () {
this.app.dispose ();
this.app = null;
});
$_M(c$, "getParameter", 
function (key, def) {
return this.isStandalone ? System.getProperty (key, def) : (this.getParameter (key) != null ? this.getParameter (key) : def);
}, "~S,~S");
$_V(c$, "getAppletInfo", 
function () {
return "JSpecView Applet " + JSV.common.JSVersion.VERSION;
});
$_V(c$, "getSolnColour", 
function () {
return this.app.getSolnColour ();
});
$_V(c$, "getCoordinate", 
function () {
return this.app.getCoordinate ();
});
$_V(c$, "loadInline", 
function (data) {
this.app.loadInline (data);
}, "~S");
$_M(c$, "$export", 
function (type, n) {
return this.app.exportSpectrum (type, n);
}, "~S,~N");
$_V(c$, "exportSpectrum", 
function (type, n) {
return this.app.exportSpectrum (type, n);
}, "~S,~N");
$_V(c$, "setFilePath", 
function (tmpFilePath) {
this.app.setFilePath (tmpFilePath);
}, "~S");
$_V(c$, "setSpectrumNumber", 
function (i) {
this.app.setSpectrumNumber (i);
}, "~N");
$_V(c$, "toggleGrid", 
function () {
this.app.toggleGrid ();
});
$_V(c$, "toggleCoordinate", 
function () {
this.app.toggleCoordinate ();
});
$_V(c$, "toggleIntegration", 
function () {
this.app.toggleIntegration ();
});
$_V(c$, "addHighlight", 
function (x1, x2, r, g, b, a) {
this.app.addHighlight (x1, x2, r, g, b, a);
}, "~N,~N,~N,~N,~N,~N");
$_V(c$, "removeAllHighlights", 
function () {
this.app.removeAllHighlights ();
});
$_V(c$, "removeHighlight", 
function (x1, x2) {
this.app.removeHighlight (x1, x2);
}, "~N,~N");
$_V(c$, "reversePlot", 
function () {
this.app.reversePlot ();
});
$_M(c$, "script", 
function (script) {
this.app.initParams (script);
}, "~S");
$_V(c$, "runScript", 
function (script) {
this.app.runScript (script);
}, "~S");
$_V(c$, "syncScript", 
function (peakScript) {
this.app.syncScript (peakScript);
}, "~S");
$_V(c$, "writeStatus", 
function (msg) {
this.app.writeStatus (msg);
}, "~S");
$_V(c$, "getPropertyAsJavaObject", 
function (key) {
return this.app.getPropertyAsJavaObject (key);
}, "~S");
$_V(c$, "getPropertyAsJSON", 
function (key) {
return this.app.getPropertyAsJSON (key);
}, "~S");
$_V(c$, "runScriptNow", 
function (script) {
return this.app.runScriptNow (script);
}, "~S");
$_V(c$, "setDropTargetListener", 
function (isSigned, viewer) {
}, "~B,JSV.common.JSViewer");
$_V(c$, "validateContent", 
function (mode) {
}, "~N");
$_V(c$, "addNewPanel", 
function (viewer) {
viewer.viewPanel =  new JSV.js2d.JsViewPanel ();
}, "JSV.common.JSViewer");
$_V(c$, "newWindow", 
function (isSelected) {
}, "~B");
$_V(c$, "callToJavaScript", 
function (callback, data) {
var tokens = JU.PT.split (callback, ".");
{
try{
var o = window[tokens[0]]
for (var i = 1; i < tokens.length; i++){
o = o[tokens[i]]
}
return o(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9]);
} catch (e) {
System.out.println(callback + " failed " + e);
}
}}, "~S,~A");
$_V(c$, "setPanelVisible", 
function (b) {
}, "~B");
$_V(c$, "getJSVPanel", 
function (viewer, specs, initialStartIndex, initialEndIndex) {
return JSV.js2d.JsPanel.getPanelMany (viewer, specs, initialStartIndex, initialEndIndex);
}, "JSV.common.JSViewer,JU.List,~N,~N");
$_V(c$, "setVisible", 
function (b) {
}, "~B");
$_V(c$, "getDocumentBase", 
function () {
try {
return  new java.net.URL (Clazz_castNullAs ("java.net.URL"), this.viewerOptions.get ("documentBase"), null);
} catch (e) {
if (Clazz_exceptionOf (e, java.net.MalformedURLException)) {
return null;
} else {
throw e;
}
}
});
$_V(c$, "repaint", 
function () {
});
$_V(c$, "validate", 
function () {
});
$_V(c$, "doExitJmol", 
function () {
});
$_V(c$, "getApp", 
function () {
return this.app;
});
});
Clazz_declarePackage ("JSV.appletjs");
Clazz_load (["JSV.appletjs.JSVApplet"], "JSV.appletjs.JSVAppletPro", ["JSV.app.JSVAppPro"], function () {
c$ = Clazz_decorateAsClass (function () {
this.app0 = null;
Clazz_instantialize (this, arguments);
}, JSV.appletjs, "JSVAppletPro", JSV.appletjs.JSVApplet);
$_V(c$, "init", 
function () {
this.app =  new JSV.app.JSVAppPro (this, false);
this.initViewer ();
});
$_V(c$, "isPro", 
function () {
return true;
});
$_M(c$, "getAppletInfo", 
function () {
return Clazz_superCall (this, JSV.appletjs.JSVAppletPro, "getAppletInfo", []) + " (PRO)";
});
$_V(c$, "script", 
function (script) {
this.runScript (script);
}, "~S");
$_V(c$, "doExitJmol", 
function () {
this.app0.setVisible (true);
this.app = this.app0;
});
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "ScriptInterface");
Clazz_declarePackage ("JSV.api");
Clazz_load (["JSV.api.JSVAppletInterface", "$.ScriptInterface"], "JSV.api.JSVAppInterface", null, function () {
Clazz_declareInterface (JSV.api, "JSVAppInterface", [JSV.api.JSVAppletInterface, JSV.api.ScriptInterface]);
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "PanelListener");
Clazz_declarePackage ("JSV.app");
Clazz_load (["JSV.api.JSVAppInterface", "$.PanelListener"], "JSV.app.JSVApp", ["java.lang.Boolean", "$.Double", "JU.List", "$.PT", "JSV.common.Coordinate", "$.JSVFileManager", "$.JSViewer", "$.PanelNode", "$.Parameters", "$.PeakPickEvent", "$.ScriptToken", "$.ScriptTokenizer", "$.SubSpecChangeEvent", "$.ZoomEvent", "JSV.source.FileReader", "JSV.util.JSVEscape", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.appletFrame = null;
this.fileCount = 0;
this.nViews = 0;
this.scriptLevelCount = 0;
this.isNewWindow = false;
this.allowCompoundMenu = true;
this.allowMenu = true;
this.autoIntegrate = false;
this.interfaceOverlaid = false;
this.loadImaginary = false;
this.obscureTitleFromUser = null;
this.initialStartIndex = -1;
this.initialEndIndex = -1;
this.integrationRatios = null;
this.appletReadyCallbackFunctionName = null;
this.coordCallbackFunctionName = null;
this.loadFileCallbackFunctionName = null;
this.peakCallbackFunctionName = null;
this.syncCallbackFunctionName = null;
this.viewer = null;
this.prevPanel = null;
this.returnFromJmolModel = null;
Clazz_instantialize (this, arguments);
}, JSV.app, "JSVApp", null, [JSV.api.PanelListener, JSV.api.JSVAppInterface]);
Clazz_makeConstructor (c$, 
function (appletFrame, isJS) {
this.appletFrame = appletFrame;
this.initViewer (isJS);
this.init ();
}, "JSV.api.AppletFrame,~B");
$_M(c$, "initViewer", 
function (isJS) {
this.viewer =  new JSV.common.JSViewer (this, true, isJS);
this.appletFrame.setDropTargetListener (this.isSigned (), this.viewer);
var path = this.appletFrame.getDocumentBase ();
JSV.common.JSVFileManager.setDocumentBase (this.viewer, path);
}, "~B");
$_V(c$, "isPro", 
function () {
return this.isSigned ();
});
$_V(c$, "isSigned", 
function () {
{
return true;
}});
$_V(c$, "siSetCurrentSource", 
function (source) {
this.viewer.currentSource = source;
}, "JSV.source.JDXSource");
$_V(c$, "siGetFileCount", 
function () {
return this.fileCount;
});
$_V(c$, "siSetFileCount", 
function (n) {
this.fileCount = n;
}, "~N");
$_V(c$, "siSetIntegrationRatios", 
function (value) {
this.integrationRatios = value;
}, "~S");
$_V(c$, "siGetIntegrationRatios", 
function () {
return this.integrationRatios;
});
$_M(c$, "getAppletFrame", 
function () {
return this.appletFrame;
});
$_V(c$, "siSetLoadImaginary", 
function (TF) {
this.loadImaginary = TF;
}, "~B");
$_V(c$, "siIncrementScriptLevelCount", 
function (n) {
return this.scriptLevelCount += n;
}, "~N");
$_V(c$, "siIncrementViewCount", 
function (n) {
return this.nViews += n;
}, "~N");
$_M(c$, "dispose", 
function () {
try {
this.viewer.dispose ();
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
e.printStackTrace ();
} else {
throw e;
}
}
});
$_V(c$, "getPropertyAsJavaObject", 
function (key) {
return this.viewer.getPropertyAsJavaObject (key);
}, "~S");
$_V(c$, "getPropertyAsJSON", 
function (key) {
return JU.PT.toJSON (null, this.getPropertyAsJavaObject (key));
}, "~S");
$_V(c$, "getCoordinate", 
function () {
return this.viewer.getCoordinate ();
});
$_V(c$, "loadInline", 
function (data) {
this.siOpenDataOrFile (data, null, null, null, -1, -1, true);
this.appletFrame.validateContent (3);
}, "~S");
$_V(c$, "exportSpectrum", 
function (type, n) {
return this.viewer.$export (type, n);
}, "~S,~N");
$_V(c$, "setFilePath", 
function (tmpFilePath) {
this.runScript ("load " + JSV.util.JSVEscape.eS (tmpFilePath));
}, "~S");
$_V(c$, "setSpectrumNumber", 
function (n) {
this.runScript (JSV.common.ScriptToken.SPECTRUMNUMBER + " " + n);
}, "~N");
$_V(c$, "reversePlot", 
function () {
this.toggle (JSV.common.ScriptToken.REVERSEPLOT);
});
$_V(c$, "toggleGrid", 
function () {
this.toggle (JSV.common.ScriptToken.GRIDON);
});
$_V(c$, "toggleCoordinate", 
function () {
this.toggle (JSV.common.ScriptToken.COORDINATESON);
});
$_V(c$, "toggleIntegration", 
function () {
this.toggle (JSV.common.ScriptToken.INTEGRATE);
});
$_M(c$, "toggle", 
function (st) {
var jsvp = this.viewer.selectedPanel;
if (jsvp != null) this.runScript (st + " TOGGLE");
}, "JSV.common.ScriptToken");
$_V(c$, "addHighlight", 
function (x1, x2, r, g, b, a) {
this.viewer.addHighLight (x1, x2, r, g, b, a);
}, "~N,~N,~N,~N,~N,~N");
$_V(c$, "removeAllHighlights", 
function () {
this.viewer.removeAllHighlights ();
});
$_V(c$, "removeHighlight", 
function (x1, x2) {
this.viewer.removeHighlight (x1, x2);
}, "~N,~N");
$_V(c$, "syncScript", 
function (peakScript) {
this.viewer.syncScript (peakScript);
}, "~S");
$_V(c$, "writeStatus", 
function (msg) {
J.util.Logger.info (msg);
}, "~S");
$_M(c$, "init", 
function () {
this.initParams (this.appletFrame.getParameter ("script"));
});
$_M(c$, "initParams", 
function (params) {
this.parseInitScript (params);
this.newAppletPanel ();
this.viewer.setPopupMenu (this.allowMenu, this.viewer.parameters.getBoolean (JSV.common.ScriptToken.ENABLEZOOM));
this.runScriptNow (params);
}, "~S");
$_M(c$, "newAppletPanel", 
function () {
J.util.Logger.info ("newAppletPanel");
this.appletFrame.addNewPanel (this.viewer);
});
$_V(c$, "siSendPanelChange", 
function (jsvp) {
if (jsvp === this.prevPanel) return;
this.prevPanel = jsvp;
this.viewer.sendPanelChange (jsvp);
}, "JSV.api.JSVPanel");
$_V(c$, "siNewWindow", 
function (isSelected, fromFrame) {
this.isNewWindow = isSelected;
if (fromFrame) {
if (this.viewer.jsvpPopupMenu != null) this.viewer.jsvpPopupMenu.setSelected ("Window", false);
} else {
this.appletFrame.newWindow (isSelected);
}}, "~B,~B");
$_V(c$, "repaint", 
function () {
{
if (typeof Jmol != "undefined" && Jmol._repaint && this.viewer.applet)
Jmol._repaint(this.viewer.applet,true);
}});
$_M(c$, "updateJS", 
function (width, height) {
}, "~N,~N");
$_V(c$, "siValidateAndRepaint", 
function () {
if (this.viewer.selectedPanel != null) this.viewer.selectedPanel.getPanelData ().taintedAll = true;
this.appletFrame.validate ();
this.repaint ();
});
$_V(c$, "siSyncLoad", 
function (filePath) {
this.newAppletPanel ();
J.util.Logger.info ("JSVP syncLoad reading " + filePath);
this.siOpenDataOrFile (null, null, null, filePath, -1, -1, false);
this.appletFrame.validateContent (3);
}, "~S");
$_M(c$, "parseInitScript", 
function (params) {
if (params == null) params = "";
var allParamTokens =  new JSV.common.ScriptTokenizer (params, true);
if (J.util.Logger.debugging) {
J.util.Logger.info ("Running in DEBUG mode");
}while (allParamTokens.hasMoreTokens ()) {
var token = allParamTokens.nextToken ();
var eachParam =  new JSV.common.ScriptTokenizer (token, false);
var key = eachParam.nextToken ();
if (key.equalsIgnoreCase ("SET")) key = eachParam.nextToken ();
key = key.toUpperCase ();
var st = JSV.common.ScriptToken.getScriptToken (key);
var value = JSV.common.ScriptToken.getValue (st, eachParam, token);
J.util.Logger.info ("KEY-> " + key + " VALUE-> " + value + " : " + st);
try {
switch (st) {
default:
this.viewer.parameters.set (null, st, value);
break;
case JSV.common.ScriptToken.UNKNOWN:
break;
case JSV.common.ScriptToken.APPLETID:
this.viewer.appletID = value;
this.viewer.fullName = this.viewer.appletID + "__" + this.viewer.syncID + "__";
{
if(typeof Jmol != "undefined") this.viewer.applet =
Jmol._applets[value];
}break;
case JSV.common.ScriptToken.APPLETREADYCALLBACKFUNCTIONNAME:
this.appletReadyCallbackFunctionName = value;
break;
case JSV.common.ScriptToken.AUTOINTEGRATE:
this.autoIntegrate = JSV.common.Parameters.isTrue (value);
break;
case JSV.common.ScriptToken.COMPOUNDMENUON:
this.allowCompoundMenu = Boolean.parseBoolean (value);
break;
case JSV.common.ScriptToken.COORDCALLBACKFUNCTIONNAME:
case JSV.common.ScriptToken.LOADFILECALLBACKFUNCTIONNAME:
case JSV.common.ScriptToken.PEAKCALLBACKFUNCTIONNAME:
case JSV.common.ScriptToken.SYNCCALLBACKFUNCTIONNAME:
this.siExecSetCallback (st, value);
break;
case JSV.common.ScriptToken.ENDINDEX:
this.initialEndIndex = Integer.parseInt (value);
break;
case JSV.common.ScriptToken.INTERFACE:
this.siExecSetInterface (value);
break;
case JSV.common.ScriptToken.IRMODE:
this.viewer.setIRmode (value);
break;
case JSV.common.ScriptToken.MENUON:
this.allowMenu = Boolean.parseBoolean (value);
break;
case JSV.common.ScriptToken.OBSCURE:
if (this.obscureTitleFromUser == null) this.obscureTitleFromUser = Boolean.$valueOf (value);
break;
case JSV.common.ScriptToken.STARTINDEX:
this.initialStartIndex = Integer.parseInt (value);
break;
case JSV.common.ScriptToken.SYNCID:
this.viewer.syncID = value;
this.viewer.fullName = this.viewer.appletID + "__" + this.viewer.syncID + "__";
break;
}
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}
}, "~S");
$_V(c$, "siOpenDataOrFile", 
function (data, name, specs, url, firstSpec, lastSpec, isAppend) {
var status = this.viewer.openDataOrFile (data, name, specs, url, firstSpec, lastSpec, isAppend);
if (status == -1) return;
if (status != 0) {
this.siSetSelectedPanel (null);
return;
}if (this.viewer.jsvpPopupMenu != null) this.viewer.jsvpPopupMenu.setCompoundMenu (this.viewer.panelNodes, this.allowCompoundMenu);
J.util.Logger.info (this.appletFrame.getAppletInfo () + " File " + this.viewer.currentSource.getFilePath () + " Loaded Successfully");
}, "~S,~S,JU.List,~S,~N,~N,~B");
$_V(c$, "siProcessCommand", 
function (scriptItem) {
this.viewer.runScriptNow (scriptItem);
}, "~S");
$_V(c$, "runScriptNow", 
function (params) {
return this.viewer.runScriptNow (params);
}, "~S");
$_M(c$, "checkCallbacks", 
function () {
if (this.coordCallbackFunctionName == null && this.peakCallbackFunctionName == null) return;
var coord =  new JSV.common.Coordinate ();
var actualCoord = (this.peakCallbackFunctionName == null ? null :  new JSV.common.Coordinate ());
if (!this.viewer.selectedPanel.getPanelData ().getPickedCoordinates (coord, actualCoord)) return;
var iSpec = this.viewer.viewPanel.getCurrentPanelIndex ();
if (actualCoord == null) this.appletFrame.callToJavaScript (this.coordCallbackFunctionName, [Double.$valueOf (coord.getXVal ()), Double.$valueOf (coord.getYVal ()), Integer.$valueOf (iSpec + 1)]);
 else this.appletFrame.callToJavaScript (this.peakCallbackFunctionName, [Double.$valueOf (coord.getXVal ()), Double.$valueOf (coord.getYVal ()), Double.$valueOf (actualCoord.getXVal ()), Double.$valueOf (actualCoord.getYVal ()), Integer.$valueOf (iSpec + 1)]);
});
$_V(c$, "siSetSelectedPanel", 
function (jsvp) {
this.viewer.viewPanel.setSelectedPanel (jsvp, this.viewer.panelNodes);
this.viewer.selectedPanel = jsvp;
this.viewer.spectraTree.setSelectedPanel (this, jsvp);
this.appletFrame.validate ();
if (jsvp != null) {
jsvp.setEnabled (true);
jsvp.setFocusable (true);
}}, "JSV.api.JSVPanel");
$_M(c$, "doAdvanced", 
function (filePath) {
}, "~S");
$_V(c$, "panelEvent", 
function (eventObj) {
if (Clazz_instanceOf (eventObj, JSV.common.PeakPickEvent)) {
this.viewer.processPeakPickEvent (eventObj, false);
} else if (Clazz_instanceOf (eventObj, JSV.common.ZoomEvent)) {
} else if (Clazz_instanceOf (eventObj, JSV.common.SubSpecChangeEvent)) {
}}, "~O");
$_V(c$, "siExecSetCallback", 
function (st, value) {
switch (st) {
case JSV.common.ScriptToken.LOADFILECALLBACKFUNCTIONNAME:
this.loadFileCallbackFunctionName = value;
break;
case JSV.common.ScriptToken.PEAKCALLBACKFUNCTIONNAME:
this.peakCallbackFunctionName = value;
break;
case JSV.common.ScriptToken.SYNCCALLBACKFUNCTIONNAME:
this.syncCallbackFunctionName = value;
break;
case JSV.common.ScriptToken.COORDCALLBACKFUNCTIONNAME:
this.coordCallbackFunctionName = value;
break;
}
}, "JSV.common.ScriptToken,~S");
$_V(c$, "getSolnColour", 
function () {
return this.viewer.getSolutionColor ();
});
$_V(c$, "siExecClose", 
function (value) {
var fromScript = (!value.startsWith ("!"));
if (fromScript) value = value.substring (1);
this.viewer.close (value);
if (!fromScript) this.siValidateAndRepaint ();
}, "~S");
$_V(c$, "siExecLoad", 
function (value) {
this.viewer.load (value);
if (this.viewer.selectedPanel == null) return null;
if (this.loadFileCallbackFunctionName != null) this.appletFrame.callToJavaScript (this.loadFileCallbackFunctionName, [this.viewer.appletID, value]);
return null;
}, "~S");
$_V(c$, "siExecHidden", 
function (b) {
}, "~B");
$_V(c$, "siExecSetInterface", 
function (value) {
this.interfaceOverlaid = (value.equalsIgnoreCase ("single") || value.equalsIgnoreCase ("overlay"));
}, "~S");
$_V(c$, "siExecScriptComplete", 
function (msg, isOK) {
this.siValidateAndRepaint ();
}, "~S,~B");
$_V(c$, "siExecSetAutoIntegrate", 
function (b) {
this.autoIntegrate = b;
}, "~B");
$_V(c$, "syncToJmol", 
function (msg) {
if (this.syncCallbackFunctionName == null) return;
J.util.Logger.info ("JSVApp.syncToJmol JSV>Jmol " + msg);
this.appletFrame.callToJavaScript (this.syncCallbackFunctionName, [this.viewer.fullName, msg]);
}, "~S");
$_V(c$, "setVisible", 
function (b) {
this.appletFrame.setPanelVisible (b);
}, "~B");
$_V(c$, "siUpdateBoolean", 
function (st, TF) {
}, "JSV.common.ScriptToken,~B");
$_V(c$, "siCheckCallbacks", 
function (title) {
this.checkCallbacks ();
}, "~S");
$_V(c$, "siSetNode", 
function (panelNode, fromTree) {
if (panelNode.jsvp !== this.viewer.selectedPanel) this.siSetSelectedPanel (panelNode.jsvp);
this.siSendPanelChange (panelNode.jsvp);
this.appletFrame.validateContent (2);
this.siValidateAndRepaint ();
}, "JSV.common.PanelNode,~B");
$_V(c$, "siCloseSource", 
function (source) {
this.viewer.closeSource (source);
}, "JSV.source.JDXSource");
$_V(c$, "setCursor", 
function (id) {
this.viewer.apiPlatform.setCursor (id, this.appletFrame);
}, "~N");
$_V(c$, "siGetAutoCombine", 
function () {
return this.interfaceOverlaid;
});
$_V(c$, "siCreateSource", 
function (data, filePath, firstSpec, lastSpec) {
return JSV.source.FileReader.createJDXSource (JSV.common.JSVFileManager.getBufferedReaderForString (data), filePath, this.obscureTitleFromUser === Boolean.TRUE, this.loadImaginary, -1, -1);
}, "~S,~S,~N,~N");
$_V(c$, "siGetNewJSVPanel2", 
function (specs) {
var jsvp = this.appletFrame.getJSVPanel (this.viewer, specs, this.initialStartIndex, this.initialEndIndex);
this.initialEndIndex = this.initialStartIndex = -1;
jsvp.getPanelData ().addListener (this);
this.viewer.parameters.setFor (jsvp, null, true);
return jsvp;
}, "JU.List");
$_V(c$, "siGetNewJSVPanel", 
function (spec) {
if (spec == null) {
this.initialEndIndex = this.initialStartIndex = -1;
return null;
}var specs =  new JU.List ();
specs.addLast (spec);
var jsvp = this.appletFrame.getJSVPanel (this.viewer, specs, this.initialStartIndex, this.initialEndIndex);
jsvp.getPanelData ().addListener (this);
this.viewer.parameters.setFor (jsvp, null, true);
return jsvp;
}, "JSV.common.JDXSpectrum");
$_V(c$, "siGetNewPanelNode", 
function (id, fileName, source, jsvp) {
return  new JSV.common.PanelNode (id, fileName, source, jsvp);
}, "~S,~S,JSV.source.JDXSource,JSV.api.JSVPanel");
$_V(c$, "siGetAutoShowLegend", 
function () {
return false;
});
$_V(c$, "siSetReturnFromJmolModel", 
function (model) {
this.returnFromJmolModel = model;
}, "~S");
$_V(c$, "siGetReturnFromJmolModel", 
function () {
return this.returnFromJmolModel;
});
$_V(c$, "siSetPropertiesFromPreferences", 
function (jsvp, includeMeasures) {
if (this.autoIntegrate) jsvp.getPanelData ().integrateAll (this.viewer.parameters);
}, "JSV.api.JSVPanel,~B");
$_V(c$, "siSetLoaded", 
function (fileName, filePath) {
}, "~S,~S");
$_V(c$, "siSetMenuEnables", 
function (node, isSplit) {
}, "JSV.common.PanelNode,~B");
$_V(c$, "siSetRecentURL", 
function (filePath) {
}, "~S");
$_V(c$, "siUpdateRecentMenus", 
function (filePath) {
}, "~S");
$_V(c$, "siExecTest", 
function (value) {
var data = "##TITLE= Acetophenone\n##JCAMP-DX= 5.01\n##DATA TYPE= MASS SPECTRUM\n##DATA CLASS= XYPOINTS\n##ORIGIN= UWI, Mona, JAMAICA\n##OWNER= public domain\n##LONGDATE= 2012/02/19 22:20:06.0416 -0600 $$ export date from JSpecView\n##BLOCK_ID= 4\n##$URL= http://wwwchem.uwimona.edu.jm/spectra\n##SPECTROMETER/DATA SYSTEM= Finnigan\n##.INSTRUMENT PARAMETERS= LOW RESOLUTION\n##.SPECTROMETER TYPE= TRAP\n##.INLET= GC\n##.IONIZATION MODE= EI+\n##MOLFORM= C 8 H 8 O\n##$MODELS= \n<Models>\n<ModelData id=\"acetophenone\" type=\"MOL\">\nacetophenone\nDSViewer          3D                             0\n\n17 17  0  0  0  0  0  0  0  0999 V2000\n-1.6931    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  1\n-0.2141    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  2\n2.5839    0.0872    0.0000 C   0  0  0  0  0  0  0  0  0  3\n0.4615    1.2373   -0.0005 C   0  0  0  0  0  0  0  0  0  4\n0.5257   -1.1809    0.0001 C   0  0  0  0  0  0  0  0  0  5\n1.9188   -1.1393    0.0005 C   0  0  0  0  0  0  0  0  0  6\n1.8539    1.2756   -0.0001 C   0  0  0  0  0  0  0  0  0  7\n-0.1262    2.1703   -0.0009 H   0  0  0  0  0  0  0  0  0  8\n0.0144   -2.1556    0.0002 H   0  0  0  0  0  0  0  0  0  9\n2.4947   -2.0764    0.0009 H   0  0  0  0  0  0  0  0  0 10\n2.3756    2.2439   -0.0001 H   0  0  0  0  0  0  0  0  0 11\n3.6838    0.1161    0.0003 H   0  0  0  0  0  0  0  0  0 12\n-2.3403    1.0639    0.0008 O   0  0  0  0  0  0  0  0  0 13\n-2.3832   -1.3197   -0.0010 C   0  0  0  0  0  0  0  0  0 14\n-2.0973   -1.8988    0.9105 H   0  0  0  0  0  0  0  0  0 15\n-2.0899   -1.9018   -0.9082 H   0  0  0  0  0  0  0  0  0 16\n-3.4920   -1.1799   -0.0059 H   0  0  0  0  0  0  0  0  0 17\n1  2  1  0  0  0\n2  5  4  0  0  0\n2  4  4  0  0  0\n3 12  1  0  0  0\n4  7  4  0  0  0\n5  6  4  0  0  0\n6 10  1  0  0  0\n6  3  4  0  0  0\n7  3  4  0  0  0\n7 11  1  0  0  0\n8  4  1  0  0  0\n9  5  1  0  0  0\n13  1  2  0  0  0\n14 16  1  0  0  0\n14  1  1  0  0  0\n14 15  1  0  0  0\n17 14  1  0  0  0\nM  END\n</ModelData>\n<ModelData id=\"2\" type=\"MOL\">\nacetophenone m/z 120\nDSViewer          3D                             0\n\n17 17  0  0  0  0  0  0  0  0999 V2000\n-1.6931    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  1\n-0.2141    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  2\n2.5839    0.0872    0.0000 C   0  0  0  0  0  0  0  0  0  3\n0.4615    1.2373   -0.0005 C   0  0  0  0  0  0  0  0  0  4\n0.5257   -1.1809    0.0001 C   0  0  0  0  0  0  0  0  0  5\n1.9188   -1.1393    0.0005 C   0  0  0  0  0  0  0  0  0  6\n1.8539    1.2756   -0.0001 C   0  0  0  0  0  0  0  0  0  7\n-0.1262    2.1703   -0.0009 H   0  0  0  0  0  0  0  0  0  8\n0.0144   -2.1556    0.0002 H   0  0  0  0  0  0  0  0  0  9\n2.4947   -2.0764    0.0009 H   0  0  0  0  0  0  0  0  0 10\n2.3756    2.2439   -0.0001 H   0  0  0  0  0  0  0  0  0 11\n3.6838    0.1161    0.0003 H   0  0  0  0  0  0  0  0  0 12\n-2.3403    1.0639    0.0008 O   0  0  0  0  0  0  0  0  0 13\n-2.3832   -1.3197   -0.0010 C   0  0  0  0  0  0  0  0  0 14\n-2.0973   -1.8988    0.9105 H   0  0  0  0  0  0  0  0  0 15\n-2.0899   -1.9018   -0.9082 H   0  0  0  0  0  0  0  0  0 16\n-3.4920   -1.1799   -0.0059 H   0  0  0  0  0  0  0  0  0 17\n1  2  1  0  0  0\n2  5  4  0  0  0\n2  4  4  0  0  0\n3 12  1  0  0  0\n4  7  4  0  0  0\n5  6  4  0  0  0\n6 10  1  0  0  0\n6  3  4  0  0  0\n7  3  4  0  0  0\n7 11  1  0  0  0\n8  4  1  0  0  0\n9  5  1  0  0  0\n13  1  2  0  0  0\n14 16  1  0  0  0\n14  1  1  0  0  0\n14 15  1  0  0  0\n17 14  1  0  0  0\nM  END\nacetophenone m/z 105\n\ncreated with ArgusLab version 4.0.1\n13 13  0  0  0  0  0  0  0  0  0 V2000\n-1.6931    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n-0.2141    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n2.5839    0.0872    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n0.4615    1.2373   -0.0005 C   0  0  0  0  0  0  0  0  0  0  0  0\n0.5257   -1.1809    0.0001 C   0  0  0  0  0  0  0  0  0  0  0  0\n1.9188   -1.1393    0.0005 C   0  0  0  0  0  0  0  0  0  0  0  0\n1.8539    1.2756   -0.0001 C   0  0  0  0  0  0  0  0  0  0  0  0\n-2.3403    1.0639    0.0008 O   0  0  0  0  0  0  0  0  0  0  0  0\n-0.1262    2.1703   -0.0009 H   0  0  0  0  0  0  0  0  0  0  0  0\n0.0144   -2.1556    0.0002 H   0  0  0  0  0  0  0  0  0  0  0  0\n2.4947   -2.0764    0.0009 H   0  0  0  0  0  0  0  0  0  0  0  0\n2.3756    2.2439   -0.0001 H   0  0  0  0  0  0  0  0  0  0  0  0\n3.6838    0.1161    0.0003 H   0  0  0  0  0  0  0  0  0  0  0  0\n1  2  1  0  0  0  0\n1  8  2  0  0  0  0\n2  4  4  0  0  0  0\n2  5  4  0  0  0  0\n3  6  4  0  0  0  0\n3  7  4  0  0  0  0\n3 13  1  0  0  0  0\n4  7  4  0  0  0  0\n4  9  1  0  0  0  0\n5  6  4  0  0  0  0\n5 10  1  0  0  0  0\n6 11  1  0  0  0  0\n7 12  1  0  0  0  0\nM  END\nacetophenone m/z 77\n\ncreated with ArgusLab version 4.0.1\n11 11  0  0  0  0  0  0  0  0  0 V2000\n-0.2141    0.0078    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n2.5839    0.0872    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n0.4615    1.2373   -0.0005 C   0  0  0  0  0  0  0  0  0  0  0  0\n0.5257   -1.1809    0.0001 C   0  0  0  0  0  0  0  0  0  0  0  0\n1.9188   -1.1393    0.0005 C   0  0  0  0  0  0  0  0  0  0  0  0\n1.8539    1.2756   -0.0001 C   0  0  0  0  0  0  0  0  0  0  0  0\n-0.1262    2.1703   -0.0009 H   0  0  0  0  0  0  0  0  0  0  0  0\n0.0144   -2.1556    0.0002 H   0  0  0  0  0  0  0  0  0  0  0  0\n2.4947   -2.0764    0.0009 H   0  0  0  0  0  0  0  0  0  0  0  0\n2.3756    2.2439   -0.0001 H   0  0  0  0  0  0  0  0  0  0  0  0\n3.6838    0.1161    0.0003 H   0  0  0  0  0  0  0  0  0  0  0  0\n1  3  4  0  0  0  0\n1  4  4  0  0  0  0\n2  5  4  0  0  0  0\n2  6  4  0  0  0  0\n2 11  1  0  0  0  0\n3  6  4  0  0  0  0\n3  7  1  0  0  0  0\n4  5  4  0  0  0  0\n4  8  1  0  0  0  0\n5  9  1  0  0  0  0\n6 10  1  0  0  0  0\nM  END\n</ModelData>\n</Models>\n##$PEAKS= \n<Peaks type=\"MS\" xUnits=\"M/Z\" yUnits=\"RELATIVE ABUNDANCE\" >\n<PeakData id=\"1\" title=\"molecular ion (~120)\" peakShape=\"sharp\" model=\"2.1\"  xMax=\"121\" xMin=\"119\"  yMax=\"100\" yMin=\"0\" />\n<PeakData id=\"2\" title=\"fragment 1 (~105)\" peakShape=\"sharp\" model=\"2.2\"  xMax=\"106\" xMin=\"104\"  yMax=\"100\" yMin=\"0\" />\n<PeakData id=\"3\" title=\"fragment 2 (~77)\" peakShape=\"sharp\" model=\"2.3\"  xMax=\"78\" xMin=\"76\"  yMax=\"100\" yMin=\"0\" />\n</Peaks>\n##XUNITS= M/Z\n##YUNITS= RELATIVE ABUNDANCE\n##XFACTOR= 1E0\n##YFACTOR= 1E0\n##FIRSTX= 0\n##FIRSTY= 0\n##LASTX= 121\n##NPOINTS= 19\n##XYPOINTS= (XY..XY)\n0.000000, 0.000000 \n38.000000, 5.200000 \n39.000000, 8.000000 \n43.000000, 21.900000 \n50.000000, 20.200000 \n51.000000, 41.900000 \n52.000000, 4.000000 \n63.000000, 3.800000 \n74.000000, 6.600000 \n75.000000, 3.700000 \n76.000000, 4.600000 \n77.000000, 100.000000 \n78.000000, 10.400000 \n89.000000, 1.000000 \n91.000000, 1.000000 \n105.000000, 80.800000 \n106.000000, 6.000000 \n120.000000, 23.100000 \n121.000000, 2.000000 \n##END=";
this.loadInline (data);
}, "~S");
$_V(c$, "siSetFileAsString", 
function (value) {
return JSV.common.JSVFileManager.getFileAsString (value);
}, "~S");
$_V(c$, "siCreateTree", 
function (source, jsvPanels) {
return this.viewer.spectraTree.createTree (this, source, jsvPanels);
}, "JSV.source.JDXSource,~A");
$_V(c$, "siGetViewer", 
function () {
return this.viewer;
});
$_V(c$, "runScript", 
function (script) {
this.viewer.runScript (script);
}, "~S");
$_V(c$, "getScriptQueue", 
function () {
return this.viewer.scriptQueue;
});
});
___date="$Date: 2013-11-29 20:58:25 -0600 (Fri, 29 Nov 2013) $"
___svnRev="$LastChangedRevision: 1454 $"
___version="2.1.0"
___JmolVersion="13.3.7_dev_2013.10.09"
Clazz_declarePackage ("JSV.common");
c$ = Clazz_declareType (JSV.common, "JSVersion");
Clazz_defineStatics (c$,
"VERSION", null,
"VERSION_SHORT", null);
{
var tmpVersion = null;
var tmpDate = null;
var tmpSVN = null;
{
tmpVersion = self.___version; tmpDate = self.___date; tmpSVN =
self.___svnRev;
}if (tmpDate != null) tmpDate = tmpDate.substring (7, 23);
if (tmpSVN != null) tmpSVN = tmpSVN.substring (22, 27);
JSV.common.JSVersion.VERSION_SHORT = (tmpVersion != null ? tmpVersion : "(Unknown version)");
JSV.common.JSVersion.VERSION = JSV.common.JSVersion.VERSION_SHORT + "/SVN" + tmpSVN + "/" + (tmpDate != null ? tmpDate : "(Unknown date)");
}Clazz_declarePackage ("JSV.api");
Clazz_load (["JSV.api.JSVViewPanel"], "JSV.api.JSVPanel", null, function () {
Clazz_declareInterface (JSV.api, "JSVPanel", JSV.api.JSVViewPanel);
});
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["JSV.api.JSVPanel"], "JSV.js2d.JsPanel", ["javajs.awt.Font", "JSV.common.PDFWriter", "$.PanelData", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.apiPlatform = null;
this.pd = null;
this.mouse = null;
this.viewer = null;
this.name = null;
Clazz_instantialize (this, arguments);
}, JSV.js2d, "JsPanel", null, JSV.api.JSVPanel);
$_V(c$, "finalize", 
function () {
J.util.Logger.info ("JSVPanel " + this + " finalized");
});
$_V(c$, "getApiPlatform", 
function () {
return this.apiPlatform;
});
$_V(c$, "getPanelData", 
function () {
return this.pd;
});
c$.getPanelOne = $_M(c$, "getPanelOne", 
function (viewer, spectrum) {
var p =  new JSV.js2d.JsPanel (viewer);
p.pd.initOne (spectrum);
return p;
}, "JSV.common.JSViewer,JSV.common.JDXSpectrum");
c$.getPanelMany = $_M(c$, "getPanelMany", 
function (viewer, spectra, startIndex, endIndex) {
var p =  new JSV.js2d.JsPanel (viewer);
p.pd.initMany (spectra, startIndex, endIndex);
return p;
}, "JSV.common.JSViewer,JU.List,~N,~N");
Clazz_makeConstructor (c$, 
function (viewer) {
this.viewer = viewer;
this.pd =  new JSV.common.PanelData (this, viewer);
this.apiPlatform = viewer.apiPlatform;
this.mouse = this.apiPlatform.getMouseManager (0, this);
}, "JSV.common.JSViewer");
$_V(c$, "getTitle", 
function () {
return this.pd.getTitle ();
});
$_V(c$, "dispose", 
function () {
if (this.pd != null) this.pd.dispose ();
this.pd = null;
this.mouse.dispose ();
});
$_V(c$, "setTitle", 
function (title) {
this.pd.title = title;
this.name = title;
}, "~S");
$_M(c$, "setColorOrFont", 
function (ds, st) {
this.pd.setColorOrFont (ds, st);
}, "JSV.common.ColorParameters,JSV.common.ScriptToken");
$_V(c$, "setBackgroundColor", 
function (color) {
}, "javajs.api.GenericColor");
$_V(c$, "getInput", 
function (message, title, sval) {
var ret = null;
{
ret = prompt(message, sval);
}this.getFocusNow (true);
return ret;
}, "~S,~S,~S");
$_V(c$, "showMessage", 
function (msg, title) {
J.util.Logger.info (msg);
{
alert(msg);
}this.getFocusNow (true);
}, "~S,~S");
$_V(c$, "getFocusNow", 
function (asThread) {
this.pd.dialogsToFront ();
}, "~B");
$_V(c$, "getFontFaceID", 
function (name) {
return javajs.awt.Font.getFontFaceID ("SansSerif");
}, "~S");
$_V(c$, "doRepaint", 
function (andTaintAll) {
this.pd.taintedAll = new Boolean (this.pd.taintedAll | andTaintAll).valueOf ();
if (!this.pd.isPrinting) this.viewer.requestRepaint ();
}, "~B");
$_M(c$, "paintComponent", 
function (context) {
if (this.viewer == null || this.pd == null || this.pd.graphSets == null || this.pd.isPrinting) return;
var context2 = null;
{
context2 = context.canvas.topLayer.getContext("2d");
}this.pd.g2d = this.pd.g2d0;
this.pd.drawGraph (context, context2, this.getWidth (), this.getHeight (), false);
this.viewer.repaintDone ();
}, "~O");
$_V(c$, "printPanel", 
function (pl, os, title) {
pl.title = title;
pl.date = this.apiPlatform.getDateFormat (true);
this.pd.setPrint (pl, "Helvetica");
try {
( new JSV.common.PDFWriter ()).createPdfDocument (this, pl, os);
} catch (ex) {
if (Clazz_exceptionOf (ex, Exception)) {
this.showMessage (ex.toString (), "creating PDF");
} else {
throw ex;
}
} finally {
this.pd.setPrint (null, null);
}
}, "JSV.common.PrintLayout,java.io.OutputStream,~S");
$_V(c$, "saveImage", 
function (type, file) {
return null;
}, "~S,javajs.api.GenericFileInterface");
$_V(c$, "hasFocus", 
function () {
return false;
});
$_V(c$, "repaint", 
function () {
});
$_V(c$, "setToolTipText", 
function (s) {
}, "~S");
$_V(c$, "getHeight", 
function () {
return this.viewer.getHeight ();
});
$_V(c$, "getWidth", 
function () {
return this.viewer.getWidth ();
});
$_V(c$, "isEnabled", 
function () {
return false;
});
$_V(c$, "isFocusable", 
function () {
return false;
});
$_V(c$, "isVisible", 
function () {
return false;
});
$_V(c$, "setEnabled", 
function (b) {
}, "~B");
$_V(c$, "setFocusable", 
function (b) {
}, "~B");
$_V(c$, "toString", 
function () {
return this.pd.getSpectrumAt (0).toString ();
});
$_V(c$, "processMouseEvent", 
function (id, x, y, modifiers, time) {
return this.mouse.processEvent (id, x, y, modifiers, time);
}, "~N,~N,~N,~N,~N");
$_V(c$, "processTwoPointGesture", 
function (touches) {
this.mouse.processTwoPointGesture (touches);
}, "~A");
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVMainPanel");
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVViewPanel");
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["JSV.api.JSVMainPanel", "$.JSVViewPanel"], "JSV.js2d.JsViewPanel", ["JSV.common.Annotation"], function () {
c$ = Clazz_decorateAsClass (function () {
this.selectedPanel = null;
this.currentPanelIndex = 0;
this.title = null;
this.visible = false;
this.focusable = false;
this.enabled = false;
Clazz_instantialize (this, arguments);
}, JSV.js2d, "JsViewPanel", null, [JSV.api.JSVViewPanel, JSV.api.JSVMainPanel]);
$_V(c$, "getCurrentPanelIndex", 
function () {
return this.currentPanelIndex;
});
$_V(c$, "dispose", 
function () {
});
$_V(c$, "getTitle", 
function () {
return this.title;
});
$_V(c$, "setTitle", 
function (title) {
this.title = title;
}, "~S");
$_V(c$, "setSelectedPanel", 
function (jsvp, panelNodes) {
if (jsvp !== this.selectedPanel) {
this.selectedPanel = jsvp;
}for (var i = panelNodes.size (); --i >= 0; ) {
var j = panelNodes.get (i).jsvp;
if (j === jsvp) {
this.currentPanelIndex = i;
} else {
j.setEnabled (false);
j.setFocusable (false);
j.getPanelData ().closeAllDialogsExcept (JSV.common.Annotation.AType.NONE);
}}
this.markSelectedPanels (panelNodes);
this.visible = (jsvp != null);
}, "JSV.api.JSVPanel,JU.List");
$_V(c$, "markSelectedPanels", 
function (panelNodes) {
for (var i = panelNodes.size (); --i >= 0; ) panelNodes.get (i).isSelected = (this.currentPanelIndex == i);

}, "JU.List");
$_M(c$, "getHeight", 
function () {
return (this.selectedPanel == null ? 0 : this.selectedPanel.getHeight ());
});
$_M(c$, "getWidth", 
function () {
return (this.selectedPanel == null ? 0 : this.selectedPanel.getWidth ());
});
$_V(c$, "isEnabled", 
function () {
return this.enabled;
});
$_V(c$, "isFocusable", 
function () {
return this.focusable;
});
$_V(c$, "isVisible", 
function () {
return this.visible;
});
$_M(c$, "setEnabled", 
function (b) {
this.enabled = b;
}, "~B");
$_M(c$, "setFocusable", 
function (b) {
this.focusable = b;
}, "~B");
});
Clazz_declarePackage ("JSV.common");
c$ = Clazz_declareType (JSV.common, "CoordComparator", null, java.util.Comparator);
$_V(c$, "compare", 
function (c1, c2) {
return (c1.getXVal () > c2.getXVal () ? 1 : c1.getXVal () < c2.getXVal () ? -1 : 0);
}, "JSV.common.Coordinate,JSV.common.Coordinate");
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.CoordComparator"], "JSV.common.Coordinate", ["java.lang.Double", "java.util.Arrays", "$.StringTokenizer", "JU.DF", "$.List"], function () {
c$ = Clazz_decorateAsClass (function () {
this.xVal = 0;
this.yVal = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "Coordinate");
Clazz_makeConstructor (c$, 
function () {
});
$_M(c$, "set", 
function (x, y) {
this.xVal = x;
this.yVal = y;
return this;
}, "~N,~N");
$_M(c$, "getXVal", 
function () {
return this.xVal;
});
$_M(c$, "getYVal", 
function () {
return this.yVal;
});
$_M(c$, "getXString", 
function () {
return JU.DF.formatDecimalTrimmed (this.xVal, 8);
});
$_M(c$, "getYString", 
function () {
return JU.DF.formatDecimalTrimmed (this.yVal, 8);
});
$_M(c$, "setXVal", 
function (val) {
this.xVal = val;
}, "~N");
$_M(c$, "setYVal", 
function (val) {
this.yVal = val;
}, "~N");
$_M(c$, "copy", 
function () {
return  new JSV.common.Coordinate ().set (this.xVal, this.yVal);
});
$_M(c$, "equals", 
function (coord) {
return (coord.xVal == this.xVal && coord.yVal == this.yVal);
}, "JSV.common.Coordinate");
$_V(c$, "toString", 
function () {
return "[" + this.xVal + ", " + this.yVal + "]";
});
c$.isYInRange = $_M(c$, "isYInRange", 
function (xyCoords, min, max) {
return (JSV.common.Coordinate.getMinY (xyCoords, 0, xyCoords.length) >= min && JSV.common.Coordinate.getMaxY (xyCoords, 0, xyCoords.length) >= max);
}, "~A,~N,~N");
c$.normalise = $_M(c$, "normalise", 
function (xyCoords, min, max) {
var newXYCoords =  new Array (xyCoords.length);
var minY = JSV.common.Coordinate.getMinY (xyCoords, 0, xyCoords.length);
var maxY = JSV.common.Coordinate.getMaxY (xyCoords, 0, xyCoords.length);
var factor = (maxY - minY) / (max - min);
for (var i = 0; i < xyCoords.length; i++) newXYCoords[i] =  new JSV.common.Coordinate ().set (xyCoords[i].getXVal (), ((xyCoords[i].getYVal () - minY) / factor) - min);

return newXYCoords;
}, "~A,~N,~N");
c$.reverse = $_M(c$, "reverse", 
function (x) {
var n = x.length;
for (var i = 0; i < n; i++) {
var v = x[i];
x[i] = x[--n];
x[n] = v;
}
return x;
}, "~A");
c$.parseDSV = $_M(c$, "parseDSV", 
function (dataPoints, xFactor, yFactor) {
var point;
var xval = 0;
var yval = 0;
var xyCoords =  new JU.List ();
var delim = " \t\n\r\f,;";
var st =  new java.util.StringTokenizer (dataPoints, delim);
var tmp1;
var tmp2;
while (st.hasMoreTokens ()) {
tmp1 = st.nextToken ().trim ();
tmp2 = st.nextToken ().trim ();
xval = Double.parseDouble (tmp1);
yval = Double.parseDouble (tmp2);
point =  new JSV.common.Coordinate ().set (xval * xFactor, yval * yFactor);
xyCoords.addLast (point);
}
var coord =  new Array (xyCoords.size ());
return xyCoords.toArray (coord);
}, "~S,~N,~N");
c$.deltaX = $_M(c$, "deltaX", 
function (last, first, numPoints) {
var test = (last - first) / (numPoints - 1);
return test;
}, "~N,~N,~N");
c$.removeScale = $_M(c$, "removeScale", 
function (xyCoords, xScale, yScale) {
JSV.common.Coordinate.applyScale (xyCoords, (1 / xScale), (1 / yScale));
}, "~A,~N,~N");
c$.applyScale = $_M(c$, "applyScale", 
function (xyCoords, xScale, yScale) {
if (xScale != 1 || yScale != 1) {
for (var i = 0; i < xyCoords.length; i++) {
xyCoords[i].setXVal (xyCoords[i].getXVal () * xScale);
xyCoords[i].setYVal (xyCoords[i].getYVal () * yScale);
}
}}, "~A,~N,~N");
c$.applyShiftReference = $_M(c$, "applyShiftReference", 
function (xyCoords, dataPointNum, firstX, lastX, offset, observedFreq, shiftRefType) {
if (dataPointNum > xyCoords.length || dataPointNum < 0) return;
var coord;
switch (shiftRefType) {
case 0:
offset = xyCoords[xyCoords.length - dataPointNum].getXVal () - offset * observedFreq;
break;
case 1:
offset = firstX - offset * observedFreq;
break;
case 2:
offset = lastX + offset;
break;
}
for (var index = 0; index < xyCoords.length; index++) {
coord = xyCoords[index];
coord.setXVal (coord.getXVal () - offset);
xyCoords[index] = coord;
}
firstX -= offset;
lastX -= offset;
}, "~A,~N,~N,~N,~N,~N,~N");
c$.getMinX = $_M(c$, "getMinX", 
function (coords, start, end) {
var min = 1.7976931348623157E308;
for (var index = start; index < end; index++) {
var tmp = coords[index].getXVal ();
if (tmp < min) min = tmp;
}
return min;
}, "~A,~N,~N");
c$.getMinX = $_M(c$, "getMinX", 
function (spectra, vd) {
var min = 1.7976931348623157E308;
for (var i = 0; i < spectra.size (); i++) {
var xyCoords = spectra.get (i).getXYCoords ();
var tmp = JSV.common.Coordinate.getMinX (xyCoords, vd.getStartingPointIndex (i), vd.getEndingPointIndex (i));
if (tmp < min) min = tmp;
}
return min;
}, "JU.List,JSV.common.ViewData");
c$.getMaxX = $_M(c$, "getMaxX", 
function (coords, start, end) {
var max = -1.7976931348623157E308;
for (var index = start; index < end; index++) {
var tmp = coords[index].getXVal ();
if (tmp > max) max = tmp;
}
return max;
}, "~A,~N,~N");
c$.getMaxX = $_M(c$, "getMaxX", 
function (spectra, vd) {
var max = -1.7976931348623157E308;
for (var i = 0; i < spectra.size (); i++) {
var xyCoords = spectra.get (i).getXYCoords ();
var tmp = JSV.common.Coordinate.getMaxX (xyCoords, vd.getStartingPointIndex (i), vd.getEndingPointIndex (i));
if (tmp > max) max = tmp;
}
return max;
}, "JU.List,JSV.common.ViewData");
c$.getMinY = $_M(c$, "getMinY", 
function (coords, start, end) {
var min = 1.7976931348623157E308;
for (var index = start; index < end; index++) {
var tmp = coords[index].getYVal ();
if (tmp < min) min = tmp;
}
return min;
}, "~A,~N,~N");
c$.getMinYUser = $_M(c$, "getMinYUser", 
function (spectra, vd) {
var min = 1.7976931348623157E308;
for (var i = 0; i < spectra.size (); i++) {
var u = spectra.get (i).getUserYFactor ();
var yref = spectra.get (i).getYRef ();
var xyCoords = spectra.get (i).getXYCoords ();
var tmp = (JSV.common.Coordinate.getMinY (xyCoords, vd.getStartingPointIndex (i), vd.getEndingPointIndex (i)) - yref) * u + yref;
if (tmp < min) min = tmp;
}
return min;
}, "JU.List,JSV.common.ViewData");
c$.getMaxY = $_M(c$, "getMaxY", 
function (coords, start, end) {
var max = -1.7976931348623157E308;
for (var index = start; index < end; index++) {
var tmp = coords[index].getYVal ();
if (tmp > max) max = tmp;
}
return max;
}, "~A,~N,~N");
c$.getMaxYUser = $_M(c$, "getMaxYUser", 
function (spectra, vd) {
var max = -1.7976931348623157E308;
for (var i = 0; i < spectra.size (); i++) {
var u = spectra.get (i).getUserYFactor ();
var yref = spectra.get (i).getYRef ();
var xyCoords = spectra.get (i).getXYCoords ();
var tmp = (JSV.common.Coordinate.getMaxY (xyCoords, vd.getStartingPointIndex (i), vd.getEndingPointIndex (i)) - yref) * u + yref;
if (tmp > max) max = tmp;
}
return max;
}, "JU.List,JSV.common.ViewData");
c$.getYValueAt = $_M(c$, "getYValueAt", 
function (xyCoords, xPt) {
var i = JSV.common.Coordinate.getNearestIndexForX (xyCoords, xPt);
if (i == 0 || i == xyCoords.length) return NaN;
var x1 = xyCoords[i].getXVal ();
var x0 = xyCoords[i - 1].getXVal ();
var y1 = xyCoords[i].getYVal ();
var y0 = xyCoords[i - 1].getYVal ();
if (x1 == x0) return y1;
return y0 + (y1 - y0) / (x1 - x0) * (xPt - x0);
}, "~A,~N");
c$.intoRange = $_M(c$, "intoRange", 
function (i, i0, i1) {
return Math.max (Math.min (i, i1), i0);
}, "~N,~N,~N");
c$.intoRange = $_M(c$, "intoRange", 
function (x, x0, x1) {
return Math.max (Math.min (x, x1), x0);
}, "~N,~N,~N");
c$.getNearestIndexForX = $_M(c$, "getNearestIndexForX", 
function (xyCoords, xPt) {
var x =  new JSV.common.Coordinate ().set (xPt, 0);
var i = java.util.Arrays.binarySearch (xyCoords, x, JSV.common.Coordinate.c);
if (i < 0) i = -1 - i;
if (i < 0) return 0;
if (i > xyCoords.length - 1) return xyCoords.length - 1;
return i;
}, "~A,~N");
c$.findXForPeakNearest = $_M(c$, "findXForPeakNearest", 
function (xyCoords, x, isMin) {
var pt = JSV.common.Coordinate.getNearestIndexForX (xyCoords, x);
var f = (isMin ? -1 : 1);
while (pt < xyCoords.length - 1 && f * (xyCoords[pt + 1].yVal - xyCoords[pt].yVal) > 0) pt++;

while (pt >= 1 && f * (xyCoords[pt - 1].yVal - xyCoords[pt].yVal) > 0) pt--;

if (pt == 0 || pt == xyCoords.length - 1) return xyCoords[pt].xVal;
return JSV.common.Coordinate.parabolicInterpolation (xyCoords, pt);
}, "~A,~N,~B");
c$.parabolicInterpolation = $_M(c$, "parabolicInterpolation", 
function (xyCoords, pt) {
var alpha = xyCoords[pt - 1].yVal;
var beta = xyCoords[pt].yVal;
var gamma = xyCoords[pt + 1].yVal;
var p = (alpha - gamma) / 2 / (alpha - 2 * beta + gamma);
return xyCoords[pt].xVal + p * (xyCoords[pt + 1].xVal - xyCoords[pt].xVal);
}, "~A,~N");
c$.getPickedCoordinates = $_M(c$, "getPickedCoordinates", 
function (coordsClicked, coordClicked, coord, actualCoord) {
if (coordClicked == null) return false;
var x = coordClicked.getXVal ();
coord.setXVal (x);
coord.setYVal (coordClicked.getYVal ());
if (actualCoord == null) return true;
var pt = JSV.common.Coordinate.getNearestIndexForX (coordsClicked, x);
actualCoord.setXVal (coordsClicked[pt].getXVal ());
actualCoord.setYVal (coordsClicked[pt].getYVal ());
return true;
}, "~A,JSV.common.Coordinate,JSV.common.Coordinate,JSV.common.Coordinate");
c$.shiftX = $_M(c$, "shiftX", 
function (xyCoords, dx) {
for (var i = xyCoords.length; --i >= 0; ) xyCoords[i].xVal += dx;

}, "~A,~N");
c$.getNearestXWithYAbove = $_M(c$, "getNearestXWithYAbove", 
function (xyCoords, x, y, inverted, andGreaterThanX) {
var pt = JSV.common.Coordinate.getNearestIndexForX (xyCoords, x);
var f = (inverted ? -1 : 1);
if (andGreaterThanX) while (pt < xyCoords.length && f * (xyCoords[pt].yVal - y) < 0) pt++;

 else while (pt >= 0 && f * (xyCoords[pt].yVal - y) < 0) pt--;

if (pt == -1 || pt == xyCoords.length) return NaN;
return JSV.common.Coordinate.findXForPeakNearest (xyCoords, xyCoords[pt].getXVal (), inverted);
}, "~A,~N,~N,~B,~B");
c$.c = c$.prototype.c =  new JSV.common.CoordComparator ();
});
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.JSVFileManager", ["java.io.BufferedInputStream", "$.BufferedReader", "$.IOException", "$.InputStreamReader", "$.StringReader", "java.net.URL", "java.util.Hashtable", "JU.AU", "$.Encoding", "$.PT", "$.SB", "JSV.common.JSVersion", "$.JSViewer", "JSV.util.JSVEscape", "J.util.Logger"], function () {
c$ = Clazz_declareType (JSV.common, "JSVFileManager");
$_M(c$, "isApplet", 
function () {
return (JSV.common.JSVFileManager.appletDocumentBase != null);
});
c$.getFileAsString = $_M(c$, "getFileAsString", 
function (name) {
if (name == null) return null;
var br;
var sb =  new JU.SB ();
try {
br = JSV.common.JSVFileManager.getBufferedReaderFromName (name, null);
var line;
while ((line = br.readLine ()) != null) {
sb.append (line);
sb.appendC ('\n');
}
br.close ();
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return null;
} else {
throw e;
}
}
return sb.toString ();
}, "~S");
c$.getBufferedReaderForInputStream = $_M(c$, "getBufferedReaderForInputStream", 
function ($in) {
try {
return  new java.io.BufferedReader ( new java.io.InputStreamReader ($in, "UTF-8"));
} catch (e) {
if (Clazz_exceptionOf (e, java.io.UnsupportedEncodingException)) {
return null;
} else {
throw e;
}
}
}, "java.io.InputStream");
c$.getBufferedReaderForString = $_M(c$, "getBufferedReaderForString", 
function (data) {
return (data == null ? null :  new java.io.BufferedReader ( new java.io.StringReader (data)));
}, "~S");
c$.getBufferedReaderFromName = $_M(c$, "getBufferedReaderFromName", 
function (name, startCode) {
if (name == null) throw  new java.io.IOException ("Cannot find " + name);
J.util.Logger.info ("JSVFileManager getBufferedReaderFromName " + name);
var path = JSV.common.JSVFileManager.getFullPathName (name);
J.util.Logger.info ("JSVFileManager getBufferedReaderFromName " + path);
return JSV.common.JSVFileManager.getUnzippedBufferedReaderFromName (path, startCode);
}, "~S,~S");
c$.getFullPathName = $_M(c$, "getFullPathName", 
function (name) {
if (JSV.common.JSVFileManager.appletDocumentBase == null) {
if (JSV.common.JSVFileManager.isURL (name)) {
var url =  new java.net.URL (Clazz_castNullAs ("java.net.URL"), name, null);
return url.toString ();
}return JSV.common.JSVFileManager.viewer.apiPlatform.newFile (name).getFullPath ();
}if (name.indexOf (":\\") == 1 || name.indexOf (":/") == 1) name = "file:///" + name;
var url =  new java.net.URL (JSV.common.JSVFileManager.appletDocumentBase, name, null);
return url.toString ();
}, "~S");
c$.isURL = $_M(c$, "isURL", 
function (name) {
for (var i = JSV.common.JSVFileManager.urlPrefixes.length; --i >= 0; ) if (name.startsWith (JSV.common.JSVFileManager.urlPrefixes[i])) return true;

return false;
}, "~S");
c$.urlTypeIndex = $_M(c$, "urlTypeIndex", 
function (name) {
for (var i = 0; i < JSV.common.JSVFileManager.urlPrefixes.length; ++i) {
if (name.startsWith (JSV.common.JSVFileManager.urlPrefixes[i])) {
return i;
}}
return -1;
}, "~S");
c$.isLocal = $_M(c$, "isLocal", 
function (fileName) {
if (fileName == null) return false;
var itype = JSV.common.JSVFileManager.urlTypeIndex (fileName);
return (itype < 0 || itype == 4);
}, "~S");
c$.getUnzippedBufferedReaderFromName = $_M(c$, "getUnzippedBufferedReaderFromName", 
function (name, startCode) {
var subFileList = null;
if (name.indexOf ("|") >= 0) {
subFileList = JU.PT.split (name, "|");
if (subFileList != null && subFileList.length > 0) name = subFileList[0];
}if (name.startsWith ("http://SIMULATION/")) return JSV.common.JSVFileManager.getBufferedReaderForString (JSV.common.JSVFileManager.getSimulationJCampDX (name.substring ("http://SIMULATION/".length)));
if (JSV.common.JSVFileManager.viewer.isApplet) {
var ret = JSV.common.JSVFileManager.viewer.apiPlatform.getBufferedURLInputStream ( new java.net.URL (Clazz_castNullAs ("java.net.URL"), name, null), null, null);
if (Clazz_instanceOf (ret, JU.SB) || Clazz_instanceOf (ret, String)) {
return  new java.io.BufferedReader ( new java.io.StringReader (ret.toString ()));
} else if (JSV.common.JSVFileManager.isAB (ret)) {
return  new java.io.BufferedReader ( new java.io.StringReader ( String.instantialize (ret)));
} else {
return  new java.io.BufferedReader ( new java.io.InputStreamReader (ret, "UTF-8"));
}}var $in = JSV.common.JSVFileManager.getInputStream (name, true, null);
var bis =  new java.io.BufferedInputStream ($in);
$in = bis;
if (JSV.common.JSVFileManager.isZipFile (bis)) return (JSV.common.JSViewer.getInterface ("JSV.util.JSVZipUtil")).newJSVZipFileSequentialReader ($in, subFileList, startCode);
if (JSV.common.JSVFileManager.isGzip (bis)) $in = (JSV.common.JSViewer.getInterface ("JSV.util.JSVZipUtil")).newGZIPInputStream ($in);
return  new java.io.BufferedReader ( new java.io.InputStreamReader ($in, "UTF-8"));
}, "~S,~S");
c$.isAB = $_M(c$, "isAB", 
function (x) {
{
return Clazz_isAI(x);
}}, "~O");
c$.isZipFile = $_M(c$, "isZipFile", 
function (is) {
var abMagic =  Clazz_newByteArray (4, 0);
is.mark (5);
var countRead = is.read (abMagic, 0, 4);
is.reset ();
return (countRead == 4 && abMagic[0] == 0x50 && abMagic[1] == 0x4B && abMagic[2] == 0x03 && abMagic[3] == 0x04);
}, "java.io.InputStream");
c$.isGzip = $_M(c$, "isGzip", 
function (is) {
var abMagic =  Clazz_newByteArray (4, 0);
is.mark (5);
var countRead = is.read (abMagic, 0, 4);
is.reset ();
return (countRead == 4 && abMagic[0] == 0x1F && abMagic[1] == 0x8B);
}, "java.io.InputStream");
c$.getStreamAsBytes = $_M(c$, "getStreamAsBytes", 
function (bis, out) {
var buf =  Clazz_newByteArray (1024, 0);
var bytes = (out == null ?  Clazz_newByteArray (4096, 0) : null);
var len = 0;
var totalLen = 0;
while ((len = bis.read (buf, 0, 1024)) > 0) {
totalLen += len;
if (out == null) {
if (totalLen >= bytes.length) bytes = JU.AU.ensureLengthByte (bytes, totalLen * 2);
System.arraycopy (buf, 0, bytes, totalLen - len, len);
} else {
out.write (buf, 0, len);
}}
bis.close ();
if (out == null) {
return JU.AU.arrayCopyByte (bytes, totalLen);
}return totalLen + " bytes";
}, "java.io.BufferedInputStream,JU.OC");
c$.postByteArray = $_M(c$, "postByteArray", 
function (fileName, bytes) {
var ret = null;
try {
ret = JSV.common.JSVFileManager.getInputStream (fileName, false, bytes);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return e.toString ();
} else {
throw e;
}
}
if (Clazz_instanceOf (ret, String)) return ret;
try {
ret = JSV.common.JSVFileManager.getStreamAsBytes (ret, null);
} catch (e) {
if (Clazz_exceptionOf (e, java.io.IOException)) {
try {
(ret).close ();
} catch (e1) {
if (Clazz_exceptionOf (e1, java.io.IOException)) {
} else {
throw e1;
}
}
} else {
throw e;
}
}
return (ret == null ? "" : JSV.common.JSVFileManager.fixUTF (ret));
}, "~S,~A");
c$.getUTFEncoding = $_M(c$, "getUTFEncoding", 
function (bytes) {
if (bytes.length >= 3 && bytes[0] == 0xEF && bytes[1] == 0xBB && bytes[2] == 0xBF) return JU.Encoding.UTF8;
if (bytes.length >= 4 && bytes[0] == 0 && bytes[1] == 0 && bytes[2] == 0xFE && bytes[3] == 0xFF) return JU.Encoding.UTF_32BE;
if (bytes.length >= 4 && bytes[0] == 0xFF && bytes[1] == 0xFE && bytes[2] == 0 && bytes[3] == 0) return JU.Encoding.UTF_32LE;
if (bytes.length >= 2 && bytes[0] == 0xFF && bytes[1] == 0xFE) return JU.Encoding.UTF_16LE;
if (bytes.length >= 2 && bytes[0] == 0xFE && bytes[1] == 0xFF) return JU.Encoding.UTF_16BE;
return JU.Encoding.NONE;
}, "~A");
c$.fixUTF = $_M(c$, "fixUTF", 
function (bytes) {
var encoding = JSV.common.JSVFileManager.getUTFEncoding (bytes);
if (encoding !== JU.Encoding.NONE) try {
var s =  String.instantialize (bytes, encoding.name ().$replace ('_', '-'));
switch (encoding) {
case JU.Encoding.UTF8:
case JU.Encoding.UTF_16BE:
case JU.Encoding.UTF_16LE:
s = s.substring (1);
break;
default:
break;
}
return s;
} catch (e) {
if (Clazz_exceptionOf (e, java.io.UnsupportedEncodingException)) {
System.out.println (e);
} else {
throw e;
}
}
return  String.instantialize (bytes);
}, "~A");
c$.getInputStream = $_M(c$, "getInputStream", 
function (name, showMsg, postBytes) {
var isURL = JSV.common.JSVFileManager.isURL (name);
var isApplet = (JSV.common.JSVFileManager.appletDocumentBase != null);
var $in = null;
var post = null;
var iurl;
if (isURL && (iurl = name.indexOf ("?POST?")) >= 0) {
post = name.substring (iurl + 6);
name = name.substring (0, iurl);
}if (isApplet || isURL) {
var url =  new java.net.URL (JSV.common.JSVFileManager.appletDocumentBase, name, null);
J.util.Logger.info ("JSVFileManager opening URL " + url + (post == null ? "" : " with POST of " + post.length + " bytes"));
$in = JSV.common.JSVFileManager.viewer.apiPlatform.getBufferedURLInputStream (url, postBytes, post);
if (Clazz_instanceOf ($in, String)) {
J.util.Logger.info ("JSVFileManager could not get this URL:" + $in);
return null;
}} else {
if (showMsg) J.util.Logger.info ("JSVFileManager opening file " + name);
$in = JSV.common.JSVFileManager.viewer.apiPlatform.getBufferedFileInputStream (name);
}return $in;
}, "~S,~B,~A");
c$.getSimulationJCampDX = $_M(c$, "getSimulationJCampDX", 
function (name) {
if (JSV.common.JSVFileManager.htSimulate == null) JSV.common.JSVFileManager.htSimulate =  new java.util.Hashtable ();
var key = "" + name.substring (name.indexOf ("V2000") + 1).hashCode ();
var jcamp = JSV.common.JSVFileManager.htSimulate.get (key);
if (jcamp == null) {
System.out.println ("creating " + name);
var isInline = name.startsWith ("MOL=");
var molFile;
if (isInline) molFile = JU.PT.simpleReplace (name.substring (4), "\\n", "\n");
 else molFile = JU.PT.simpleReplace (JSV.common.JSVFileManager.nciResolver, "%FILE", JU.PT.escapeUrl (name.substring (1)));
J.util.Logger.info ("JSVFileManager using \n" + molFile);
molFile = JSV.common.JSVFileManager.getFileAsString (molFile);
if (molFile == null) J.util.Logger.info ("no data returned");
var pt = molFile.indexOf ("\n");
molFile = "/JSpecView " + JSV.common.JSVersion.VERSION + molFile.substring (pt);
molFile = JU.PT.simpleReplace (molFile, "?", "_");
var json = JSV.common.JSVFileManager.getFileAsString (JSV.common.JSVFileManager.nmrdbServer + molFile);
System.out.println (json);
json = JU.PT.simpleReplace (json, "\\r\\n", "\n");
json = JU.PT.simpleReplace (json, "\\t", "\t");
json = JU.PT.simpleReplace (json, "\\n", "\n");
molFile = JSV.common.JSVFileManager.getQuotedJSONAttribute (json, "molfile", null);
var xml = JSV.common.JSVFileManager.getQuotedJSONAttribute (json, "xml", null);
xml = JU.PT.simpleReplace (xml, "</", "\n</");
xml = JU.PT.simpleReplace (xml, "><", ">\n<");
xml = JU.PT.simpleReplace (xml, "\\\"", "\"");
jcamp = JSV.common.JSVFileManager.getQuotedJSONAttribute (json, "jcamp", null);
jcamp = "##TITLE=" + (isInline ? "JMOL SIMULATION" : name) + "\n" + jcamp.substring (jcamp.indexOf ("\n##") + 1);
J.util.Logger.info (jcamp.substring (0, jcamp.indexOf ("##XYDATA") + 40) + "...");
pt = 0;
pt = jcamp.indexOf ("##.");
var id = name;
var pt1 = id.indexOf ("id='");
if (isInline && pt1 > 0) id = id.substring (pt1 + 4, (id + "'").indexOf ("'", pt1 + 4));
jcamp = jcamp.substring (0, pt) + "##$MODELS=\n<Models>\n" + "<ModelData id=" + JSV.util.JSVEscape.eS (id) + "\n type=\"MOL\">\n" + molFile + "</ModelData>\n</Models>\n" + "##$SIGNALS=\n" + xml + "\n" + jcamp.substring (pt);
JSV.common.JSVFileManager.htSimulate.put (key, jcamp);
}return jcamp;
}, "~S");
c$.getResource = $_M(c$, "getResource", 
function (object, fileName, error) {
var url = null;
try {
if ((url = object.getClass ().getResource (fileName)) == null) error[0] = "Couldn't find file: " + fileName;
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
error[0] = "Exception " + e.getMessage () + " in getResource " + fileName;
} else {
throw e;
}
}
return url;
}, "~O,~S,~A");
c$.getResourceString = $_M(c$, "getResourceString", 
function (object, name, error) {
var url = JSV.common.JSVFileManager.getResource (object, name, error);
if (url == null) {
error[0] = "Error loading resource " + name;
return null;
}if (Clazz_instanceOf (url, String)) {
return JSV.common.JSVFileManager.getFileAsString (url);
}var sb =  new JU.SB ();
try {
var br =  new java.io.BufferedReader ( new java.io.InputStreamReader ((url).getContent (), "UTF-8"));
var line;
while ((line = br.readLine ()) != null) sb.append (line).append ("\n");

br.close ();
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
error[0] = e.getMessage ();
} else {
throw e;
}
}
return sb.toString ();
}, "~O,~S,~A");
c$.getJmolFilePath = $_M(c$, "getJmolFilePath", 
function (filePath) {
try {
filePath = JSV.common.JSVFileManager.getFullPathName (filePath);
} catch (e) {
if (Clazz_exceptionOf (e, java.net.MalformedURLException)) {
return null;
} else {
throw e;
}
}
return (JSV.common.JSVFileManager.appletDocumentBase == null ? filePath.$replace ('\\', '/') : filePath);
}, "~S");
c$.getName = $_M(c$, "getName", 
function (fileName) {
if (fileName == null) return "String" + (++JSV.common.JSVFileManager.stringCount);
if (JSV.common.JSVFileManager.isURL (fileName)) {
try {
if (fileName.startsWith ("http://SIMULATION/") && fileName.length > 100) return fileName.substring (0, Math.min (fileName.length, 30)) + "...";
var name = ( new java.net.URL (Clazz_castNullAs ("java.net.URL"), fileName, null)).getFile ();
return name.substring (name.lastIndexOf ('/') + 1);
} catch (e) {
if (Clazz_exceptionOf (e, java.net.MalformedURLException)) {
return null;
} else {
throw e;
}
}
}return JSV.common.JSVFileManager.viewer.apiPlatform.newFile (fileName).getName ();
}, "~S");
c$.getQuotedJSONAttribute = $_M(c$, "getQuotedJSONAttribute", 
function (json, key1, key2) {
if (key2 == null) key2 = key1;
key1 = "\"" + key1 + "\":";
key2 = "\"" + key2 + "\":";
var pt1 = json.indexOf (key1);
var pt2 = json.indexOf (key2, pt1);
return (pt1 < 0 || pt2 < 0 ? null : JU.PT.getQuotedStringAt (json, pt2 + key2.length));
}, "~S,~S,~S");
c$.setDocumentBase = $_M(c$, "setDocumentBase", 
function (v, documentBase) {
JSV.common.JSVFileManager.viewer = v;
JSV.common.JSVFileManager.appletDocumentBase = documentBase;
}, "JSV.common.JSViewer,java.net.URL");
Clazz_defineStatics (c$,
"SIMULATION_PROTOCOL", "http://SIMULATION/",
"appletDocumentBase", null,
"viewer", null,
"jsDocumentBase", "");
c$.urlPrefixes = c$.prototype.urlPrefixes = ["http:", "https:", "ftp:", "http://SIMULATION/", "file:"];
Clazz_defineStatics (c$,
"URL_LOCAL", 4,
"nciResolver", "http://cactus.nci.nih.gov/chemical/structure/%FILE/file?format=sdf&get3d=True",
"nmrdbServer", "http://www.nmrdb.org/tools/jmol/predict.php?POST?molfile=",
"htSimulate", null,
"stringCount", 0);
});
Clazz_declarePackage ("JSV.source");
Clazz_load (["JU.List"], "JSV.source.JDXHeader", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.title = "";
this.jcampdx = "5.01";
this.dataType = "";
this.dataClass = "";
this.origin = "";
this.owner = "PUBLIC DOMAIN";
this.longDate = "";
this.date = "";
this.time = "";
this.qualifiedType = null;
this.headerTable = null;
Clazz_instantialize (this, arguments);
}, JSV.source, "JDXHeader");
Clazz_prepareFields (c$, function () {
this.headerTable =  new JU.List ();
});
$_M(c$, "setTitle", 
function (title) {
this.title = title;
}, "~S");
$_M(c$, "setJcampdx", 
function (versionNum) {
this.jcampdx = versionNum;
}, "~S");
$_M(c$, "setDataType", 
function (dataType) {
this.dataType = dataType;
}, "~S");
$_M(c$, "setDataClass", 
function (dataClass) {
this.dataClass = dataClass;
}, "~S");
$_M(c$, "setOrigin", 
function (origin) {
this.origin = origin;
}, "~S");
$_M(c$, "setOwner", 
function (owner) {
this.owner = owner;
}, "~S");
$_M(c$, "setLongDate", 
function (longDate) {
this.longDate = longDate;
}, "~S");
$_M(c$, "setDate", 
function (date) {
this.date = date;
}, "~S");
$_M(c$, "setTime", 
function (time) {
this.time = time;
}, "~S");
$_M(c$, "getTitle", 
function () {
return this.title;
});
c$.getTypeName = $_M(c$, "getTypeName", 
function (type) {
type = type.toUpperCase ();
for (var i = 0; i < JSV.source.JDXHeader.typeNames.length; i++) if (JSV.source.JDXHeader.typeNames[i].startsWith (type)) {
return JSV.source.JDXHeader.typeNames[i].substring (18);
}
return type;
}, "~S");
$_M(c$, "getQualifiedDataType", 
function () {
return (this.qualifiedType == null ? (this.qualifiedType = JSV.source.JDXHeader.getTypeName (this.dataType)) : this.qualifiedType);
});
$_M(c$, "getJcampdx", 
function () {
return this.jcampdx;
});
$_M(c$, "getDataType", 
function () {
return this.dataType;
});
$_M(c$, "getOrigin", 
function () {
return this.origin;
});
$_M(c$, "getOwner", 
function () {
return this.owner;
});
$_M(c$, "getLongDate", 
function () {
return this.longDate;
});
$_M(c$, "getDate", 
function () {
return this.date;
});
$_M(c$, "getTime", 
function () {
return this.time;
});
$_M(c$, "getDataClass", 
function () {
return this.dataClass;
});
$_M(c$, "setHeaderTable", 
function (table) {
this.headerTable = table;
}, "JU.List");
$_M(c$, "getHeaderTable", 
function () {
return this.headerTable;
});
$_M(c$, "getHeaderRowDataAsArray", 
function (addDataClass, nMore) {
var rowData =  new Array ((addDataClass ? 6 : 5) + this.headerTable.size () + nMore);
var i = 0;
rowData[i++] = ["##TITLE", this.title];
rowData[i++] = ["##JCAMP-DX", this.jcampdx];
rowData[i++] = ["##DATA TYPE", this.dataType];
if (addDataClass) rowData[i++] = ["##DATA CLASS", this.dataClass];
rowData[i++] = ["##ORIGIN", this.origin];
rowData[i++] = ["##OWNER", this.owner];
for (var j = 0; j < this.headerTable.size (); j++) rowData[i++] = this.headerTable.get (j);

return rowData;
}, "~B,~N");
Clazz_defineStatics (c$,
"typeNames", ["ND NMR SPECTRUM   NMR", "NMR SPECTRUM      NMR", "INFRARED SPECTRUM IR", "MASS SPECTRUM     MS", "RAMAN SPECTRUM    RAMAN", "GAS CHROMATOGRAM  GC", "UV/VIS SPECTRUM   UV/VIS"]);
});
Clazz_declarePackage ("JSV.source");
Clazz_load (["JSV.source.JDXHeader"], "JSV.source.JDXDataObject", ["java.lang.Character", "$.Double", "JU.DF", "$.PT", "JSV.common.Annotation", "$.Coordinate", "$.Integral", "JSV.exception.JSpecViewException", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.filePath = null;
this.filePathForwardSlash = null;
this.blockID = 0;
this.fileFirstX = 1.7976931348623157E308;
this.fileLastX = 1.7976931348623157E308;
this.nPointsFile = -1;
this.xFactor = 1.7976931348623157E308;
this.yFactor = 1.7976931348623157E308;
this.varName = "";
this.xUnits = "";
this.yUnits = "";
this.xLabel = null;
this.yLabel = null;
this.nH = 0;
this.observedNucl = "";
this.observedFreq = 1.7976931348623157E308;
this.parent = null;
this.offset = 1.7976931348623157E308;
this.shiftRefType = -1;
this.dataPointNum = -1;
this.numDim = 1;
this.nucleusX = null;
this.nucleusY = "?";
this.freq2dX = NaN;
this.freq2dY = NaN;
this.y2D = NaN;
this.y2DUnits = "";
this.$isHZtoPPM = false;
this.xIncreases = true;
this.continuous = false;
this.xyCoords = null;
this.minX = NaN;
this.minY = NaN;
this.maxX = NaN;
this.maxY = NaN;
this.deltaX = NaN;
Clazz_instantialize (this, arguments);
}, JSV.source, "JDXDataObject", JSV.source.JDXHeader);
$_M(c$, "setFilePath", 
function (filePath) {
if (filePath == null) return;
this.filePath = filePath.trim ();
this.filePathForwardSlash = this.filePath.$replace ('\\', '/');
}, "~S");
$_M(c$, "getFilePath", 
function () {
return this.filePath;
});
$_M(c$, "getFilePathForwardSlash", 
function () {
return this.filePathForwardSlash;
});
$_M(c$, "setBlockID", 
function (id) {
this.blockID = id;
}, "~N");
$_M(c$, "isImaginary", 
function () {
return this.varName.contains ("IMAG");
});
$_M(c$, "setXFactor", 
function (xFactor) {
this.xFactor = xFactor;
}, "~N");
$_M(c$, "getXFactor", 
function () {
return this.xFactor;
});
$_M(c$, "setYFactor", 
function (yFactor) {
this.yFactor = yFactor;
}, "~N");
$_M(c$, "getYFactor", 
function () {
return this.yFactor;
});
$_M(c$, "checkRequiredTokens", 
function () {
if (this.fileFirstX == 1.7976931348623157E308) throw  new JSV.exception.JSpecViewException ("Error Reading Data Set: ##FIRST not found");
if (this.fileLastX == 1.7976931348623157E308) throw  new JSV.exception.JSpecViewException ("Error Reading Data Set: ##LASTX not found");
if (this.nPointsFile == -1) throw  new JSV.exception.JSpecViewException ("Error Reading Data Set: ##NPOINTS not found");
if (this.xFactor == 1.7976931348623157E308) throw  new JSV.exception.JSpecViewException ("Error Reading Data Set: ##XFACTOR not found");
if (this.yFactor == 1.7976931348623157E308) throw  new JSV.exception.JSpecViewException ("Error Reading Data Set: ##YFACTOR not found");
});
$_M(c$, "setXUnits", 
function (xUnits) {
this.xUnits = xUnits;
}, "~S");
$_M(c$, "getXUnits", 
function () {
return this.xUnits;
});
$_M(c$, "setYUnits", 
function (yUnits) {
this.yUnits = yUnits;
}, "~S");
$_M(c$, "getYUnits", 
function () {
return this.yUnits;
});
$_M(c$, "setXLabel", 
function (value) {
this.xLabel = value;
}, "~S");
$_M(c$, "setYLabel", 
function (value) {
this.yLabel = value;
}, "~S");
$_M(c$, "setObservedNucleus", 
function (value) {
this.observedNucl = value;
if (this.numDim == 1) this.parent.nucleusX = this.nucleusX = JU.PT.trim (value, "[]^<>");
}, "~S");
$_M(c$, "setObservedFreq", 
function (observedFreq) {
this.observedFreq = observedFreq;
}, "~N");
$_M(c$, "getObservedFreq", 
function () {
return this.observedFreq;
});
$_M(c$, "is1D", 
function () {
return this.numDim == 1;
});
$_M(c$, "setY2D", 
function (d) {
this.y2D = d;
}, "~N");
$_M(c$, "getY2D", 
function () {
return this.y2D;
});
$_M(c$, "setY2DUnits", 
function (units) {
this.y2DUnits = units;
}, "~S");
$_M(c$, "getY2DPPM", 
function () {
var d = this.y2D;
if (this.y2DUnits.equals ("HZ")) d /= this.freq2dY;
return d;
});
$_M(c$, "setNucleus", 
function (nuc, isX) {
if (isX) this.nucleusX = nuc;
 else this.nucleusY = nuc;
var freq;
if (this.observedNucl.indexOf (nuc) >= 0) {
freq = this.observedFreq;
} else {
var g1 = JSV.source.JDXDataObject.getGyroMagneticRatio (this.observedNucl);
var g2 = JSV.source.JDXDataObject.getGyroMagneticRatio (nuc);
freq = this.observedFreq * g2 / g1;
}if (isX) this.freq2dX = freq;
 else this.freq2dY = freq;
J.util.Logger.info ("Freq for " + nuc + " = " + freq);
}, "~S,~B");
c$.getGyroMagneticRatio = $_M(c$, "getGyroMagneticRatio", 
function (nuc) {
var pt = 0;
while (pt < nuc.length && !Character.isDigit (nuc.charAt (pt))) pt++;

pt = JU.PT.parseInt (nuc.substring (pt));
var i = 0;
for (; i < JSV.source.JDXDataObject.gyroData.length; i += 2) if (JSV.source.JDXDataObject.gyroData[i] >= pt) break;

return (JSV.source.JDXDataObject.gyroData[i] == pt ? JSV.source.JDXDataObject.gyroData[i + 1] : NaN);
}, "~S");
$_M(c$, "isTransmittance", 
function () {
var s = this.yUnits.toLowerCase ();
return (s.equals ("transmittance") || s.contains ("trans") || s.equals ("t"));
});
$_M(c$, "isAbsorbance", 
function () {
var s = this.yUnits.toLowerCase ();
return (s.equals ("absorbance") || s.contains ("abs") || s.equals ("a"));
});
$_M(c$, "canSaveAsJDX", 
function () {
return this.getDataClass ().equals ("XYDATA");
});
$_M(c$, "canIntegrate", 
function () {
return (this.continuous && (this.isHNMR () || this.isGC ()) && this.is1D ());
});
$_M(c$, "isAutoOverlayFromJmolClick", 
function () {
return (this.isGC ());
});
$_M(c$, "isGC", 
function () {
return this.dataType.startsWith ("GC");
});
$_M(c$, "isMS", 
function () {
return this.dataType.startsWith ("MASS") || this.dataType.startsWith ("MS");
});
$_M(c$, "isStackable", 
function () {
return !this.isMS ();
});
$_M(c$, "isScalable", 
function () {
return true;
});
$_M(c$, "getYRef", 
function () {
return (!this.isTransmittance () ? 0.0 : JSV.common.Coordinate.getMaxY (this.xyCoords, 0, this.xyCoords.length) < 2 ? 1.0 : 100.0);
});
$_M(c$, "isInverted", 
function () {
return this.isTransmittance ();
});
$_M(c$, "canConvertTransAbs", 
function () {
return (this.continuous && (this.yUnits.toLowerCase ().contains ("abs")) || this.yUnits.toLowerCase ().contains ("trans"));
});
$_M(c$, "canShowSolutionColor", 
function () {
return (this.canConvertTransAbs () && (this.xUnits.toLowerCase ().contains ("nanometer") || this.xUnits.equalsIgnoreCase ("nm")) && this.getFirstX () < 401 && this.getLastX () > 699);
});
$_M(c$, "isHZtoPPM", 
function () {
return this.$isHZtoPPM;
});
$_M(c$, "setHZtoPPM", 
function (val) {
this.$isHZtoPPM = val;
}, "~B");
$_M(c$, "setIncreasing", 
function (val) {
this.xIncreases = val;
}, "~B");
$_M(c$, "isXIncreasing", 
function () {
return this.xIncreases;
});
$_M(c$, "shouldDisplayXAxisIncreasing", 
function () {
var dt = this.dataType.toUpperCase ();
var xu = this.xUnits.toUpperCase ();
if (dt.contains ("NMR") && !(dt.contains ("FID"))) {
return false;
} else if (dt.contains ("LINK") && xu.contains ("CM")) {
return false;
} else if (dt.startsWith ("IR") || dt.contains ("INFRA") && xu.contains ("CM")) {
return false;
} else if (dt.contains ("RAMAN") && xu.contains ("CM")) {
return false;
} else if (dt.contains ("VIS") && xu.contains ("NANOMETERS")) {
return true;
}return this.xIncreases;
});
$_M(c$, "setContinuous", 
function (val) {
this.continuous = val;
}, "~B");
$_M(c$, "isContinuous", 
function () {
return this.continuous;
});
$_M(c$, "getHeaderRowDataAsArray", 
function () {
var n = 8;
if (this.observedFreq != 1.7976931348623157E308) n++;
if (this.observedNucl !== "") n++;
var rowData = this.getHeaderRowDataAsArray (true, n);
var i = rowData.length - n;
if (this.observedFreq != 1.7976931348623157E308) rowData[i++] = ["##.OBSERVE FREQUENCY", "" + this.observedFreq];
if (this.observedNucl !== "") rowData[i++] = ["##.OBSERVE NUCLEUS", this.observedNucl];
rowData[i++] = ["##XUNITS", this.$isHZtoPPM ? "HZ" : this.xUnits];
rowData[i++] = ["##YUNITS", this.yUnits];
var x = (this.xIncreases ? this.getFirstX () : this.getLastX ());
rowData[i++] = ["##FIRSTX", String.valueOf (this.isHZtoPPM () ? x * this.observedFreq : x)];
x = (this.xIncreases ? this.getLastX () : this.getFirstX ());
rowData[i++] = ["##FIRSTY", String.valueOf (this.xIncreases ? this.getFirstY () : this.getLastY ())];
rowData[i++] = ["##LASTX", String.valueOf (this.isHZtoPPM () ? x * this.observedFreq : x)];
rowData[i++] = ["##XFACTOR", String.valueOf (this.getXFactor ())];
rowData[i++] = ["##YFACTOR", String.valueOf (this.getYFactor ())];
rowData[i++] = ["##NPOINTS", String.valueOf (this.getNumberOfPoints ())];
return rowData;
});
$_M(c$, "getDefaultUnitPrecision", 
function () {
return 2;
});
$_M(c$, "setMeasurementText", 
function (m) {
var dx = m.getValue ();
if (Double.isNaN (dx)) return "";
var precision = 1;
var units = "";
if (this.isNMR ()) {
if (this.numDim == 1) {
var isIntegral = (Clazz_instanceOf (m, JSV.common.Integral));
if (this.isHNMR () || isIntegral) {
if (!isIntegral) {
dx *= this.observedFreq;
units = " Hz";
}} else {
units = " ppm";
precision = 2;
}} else {
return "";
}}return (dx < 0.1 ? "" : JU.DF.formatDecimalDbl (dx, precision) + units);
}, "JSV.common.Measurement");
$_M(c$, "isNMR", 
function () {
return (this.dataType.toUpperCase ().indexOf ("NMR") >= 0);
});
$_M(c$, "isHNMR", 
function () {
return (this.isNMR () && this.observedNucl.toUpperCase ().indexOf ("H") >= 0);
});
$_M(c$, "setXYCoords", 
function (coords) {
this.xyCoords = coords;
}, "~A");
$_M(c$, "getFirstX", 
function () {
return this.xyCoords[0].getXVal ();
});
$_M(c$, "getFirstY", 
function () {
return this.xyCoords[0].getYVal ();
});
$_M(c$, "getLastX", 
function () {
return this.xyCoords[this.getNumberOfPoints () - 1].getXVal ();
});
$_M(c$, "getLastY", 
function () {
return this.xyCoords[this.getNumberOfPoints () - 1].getYVal ();
});
$_M(c$, "getNumberOfPoints", 
function () {
return this.xyCoords.length;
});
$_M(c$, "getMinX", 
function () {
return (Double.isNaN (this.minX) ? (this.minX = JSV.common.Coordinate.getMinX (this.xyCoords, 0, this.xyCoords.length)) : this.minX);
});
$_M(c$, "getMinY", 
function () {
return (Double.isNaN (this.minY) ? (this.minY = JSV.common.Coordinate.getMinY (this.xyCoords, 0, this.xyCoords.length)) : this.minY);
});
$_M(c$, "getMaxX", 
function () {
return (Double.isNaN (this.maxX) ? (this.maxX = JSV.common.Coordinate.getMaxX (this.xyCoords, 0, this.xyCoords.length)) : this.maxX);
});
$_M(c$, "getMaxY", 
function () {
return (Double.isNaN (this.maxY) ? (this.maxY = JSV.common.Coordinate.getMaxY (this.xyCoords, 0, this.xyCoords.length)) : this.maxY);
});
$_M(c$, "getDeltaX", 
function () {
return (Double.isNaN (this.deltaX) ? (this.deltaX = JSV.common.Coordinate.deltaX (this.getLastX (), this.getFirstX (), this.getNumberOfPoints ())) : this.deltaX);
});
$_M(c$, "copyTo", 
function (newObj) {
newObj.setTitle (this.title);
newObj.setJcampdx (this.jcampdx);
newObj.setOrigin (this.origin);
newObj.setOwner (this.owner);
newObj.setDataClass (this.dataClass);
newObj.setDataType (this.dataType);
newObj.setHeaderTable (this.headerTable);
newObj.setXFactor (this.xFactor);
newObj.setYFactor (this.yFactor);
newObj.setXUnits (this.xUnits);
newObj.setYUnits (this.yUnits);
newObj.setXLabel (this.xLabel);
newObj.setYLabel (this.yLabel);
newObj.setXYCoords (this.xyCoords);
newObj.setContinuous (this.continuous);
newObj.setIncreasing (this.xIncreases);
newObj.observedFreq = this.observedFreq;
newObj.observedNucl = this.observedNucl;
newObj.offset = this.offset;
newObj.dataPointNum = this.dataPointNum;
newObj.shiftRefType = this.shiftRefType;
newObj.$isHZtoPPM = this.$isHZtoPPM;
newObj.numDim = this.numDim;
newObj.nucleusX = this.nucleusX;
newObj.nucleusY = this.nucleusY;
newObj.freq2dX = this.freq2dX;
newObj.freq2dY = this.freq2dY;
newObj.setFilePath (this.filePath);
newObj.nH = this.nH;
}, "JSV.source.JDXDataObject");
$_M(c$, "getTypeLabel", 
function () {
return (this.isNMR () ? this.nucleusX + "NMR" : this.dataType);
});
$_M(c$, "getDefaultAnnotationInfo", 
function (type) {
var s1;
var s2;
var isNMR = this.isNMR ();
switch (type) {
case JSV.common.Annotation.AType.Integration:
return [null, [1], null];
case JSV.common.Annotation.AType.Measurements:
s1 = (isNMR ? ["Hz", "ppm"] : [""]);
s2 = (this.isHNMR () ? [1, 4] : [1, 3]);
return [s1, s2, Integer.$valueOf (0)];
case JSV.common.Annotation.AType.PeakList:
s1 = (isNMR ? ["Hz", "ppm"] : [""]);
s2 = (this.isHNMR () ? [1, 2] : [1, 1]);
return [s1, s2, Integer.$valueOf (isNMR ? 1 : 0)];
case JSV.common.Annotation.AType.NONE:
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
return null;
}, "JSV.common.Annotation.AType");
$_M(c$, "getPeakListArray", 
function (m, last, maxY) {
var x = m.getXVal ();
var y = m.getYVal () / maxY;
var dx = Math.abs (x - last[0]);
last[0] = x;
var ddx = dx + last[1];
last[1] = dx;
var dddx = ddx + last[2];
last[2] = ddx;
if (this.isNMR ()) {
return [x, y, x * this.observedFreq, (dx * this.observedFreq > 20 ? 0 : dx * this.observedFreq), (ddx * this.observedFreq > 20 ? 0 : ddx * this.observedFreq), (dddx * this.observedFreq > 20 ? 0 : dddx * this.observedFreq)];
}return [x, y];
}, "JSV.common.Measurement,~A,~N");
Clazz_defineStatics (c$,
"ERROR", 1.7976931348623157E308,
"SCALE_NONE", 0,
"SCALE_TOP", 1,
"SCALE_BOTTOM", 2,
"SCALE_TOP_BOTTOM", 3,
"gyroData", [1, 42.5774806, 2, 6.53590131, 3, 45.4148, 3, 32.436, 6, 6.2661, 7, 16.5483, 9, 5.9842, 10, 4.5752, 11, 13.663, 13, 10.70839657, 14, 3.07770646, 15, 4.31726570, 17, 5.7742, 19, 40.07757016, 21, 3.3631, 23, 11.26952167, 25, 2.6083, 27, 11.1031, 29, 8.4655, 31, 17.25144090, 33, 3.2717, 35, 4.1765, 37, 3.4765, 37, 5.819, 39, 3.46, 39, 1.9893, 40, 2.4737, 41, 1.0919, 43, 2.8688, 45, 10.3591, 47, 2.4041, 49, 2.4048, 50, 4.2505, 51, 11.2133, 53, 2.4115, 55, 10.5763, 57, 1.3816, 59, 10.077, 61, 3.8114, 63, 11.2982, 65, 12.103, 67, 2.6694, 69, 10.2478, 71, 13.0208, 73, 1.4897, 75, 7.315, 77, 8.1571, 79, 10.7042, 81, 11.5384, 83, 1.6442, 85, 4.1254, 87, 13.9811, 87, 1.8525, 89, 2.0949, 91, 3.9748, 93, 10.4523, 95, 2.7874, 97, 2.8463, 99, 9.6294, 99, 1.9553, 101, 2.1916, 103, 1.3477, 105, 1.957, 107, 1.7331, 109, 1.9924, 111, 9.0692, 113, 9.4871, 113, 9.3655, 115, 9.3856, 115, 14.0077, 117, 15.261, 119, 15.966, 121, 10.2551, 123, 5.5532, 123, 11.2349, 125, 13.5454, 127, 8.5778, 129, 11.8604, 131, 3.5159, 133, 5.6234, 135, 4.2582, 137, 4.7634, 138, 5.6615, 139, 6.0612, 137, 4.88, 139, 5.39, 141, 2.37, 141, 13.0359, 143, 2.319, 145, 1.429, 143, 11.59, 147, 5.62, 147, 1.7748, 149, 14631, 151, 10.5856, 153, 4.6745, 155, 1.312, 157, 1.72, 159, 10.23, 161, 1.4654, 163, 2.0508, 165, 9.0883, 167, 1.2281, 169, 3.531, 171, 7.5261, 173, 2.073, 175, 4.8626, 176, 3.451, 177, 1.7282, 179, 1.0856, 180, 4.087, 181, 5.1627, 183, 1.7957, 185, 9.7176, 187, 9.817, 187, 0.9856, 189, 3.3536, 191, 0.7658, 191, 0.8319, 195, 9.2922, 197, 0.7406, 199, 7.7123, 201, 2.8469, 203, 24.7316, 205, 24.9749, 207, 9.034, 209, 6.963, 209, 11.7, 211, 9.16, 223, 5.95, 223, 1.3746, 225, 11.187, 227, 5.6, 229, 1.4, 231, 10.2, 235, 0.83, 237, 9.57, 239, 3.09, 243, 4.6, 1E100]);
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum", "JSV.source.JDXDataObject", "JU.List"], "JSV.common.JDXSpectrum", ["java.lang.Boolean", "$.Double", "java.util.Hashtable", "JSV.common.Coordinate", "$.Parameters", "$.PeakInfo", "JSV.source.JDXSourceStreamTokenizer", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.subSpectra = null;
this.peakList = null;
this.piUnitsX = null;
this.piUnitsY = null;
this.selectedPeak = null;
this.highlightedPeak = null;
this.specShift = 0;
this.currentSubSpectrumIndex = 0;
this.$isForcedSubset = false;
this.id = "";
this.convertedSpectrum = null;
this.userYFactor = 1;
this.exportXAxisLeftToRight = false;
Clazz_instantialize (this, arguments);
}, JSV.common, "JDXSpectrum", JSV.source.JDXDataObject);
Clazz_prepareFields (c$, function () {
this.peakList =  new JU.List ();
});
$_V(c$, "finalize", 
function () {
System.out.println ("JDXSpectrum " + this + " finalized " + this.title);
});
$_M(c$, "dispose", 
function () {
});
$_M(c$, "isForcedSubset", 
function () {
return this.$isForcedSubset;
});
$_M(c$, "setId", 
function (id) {
this.id = id;
}, "~S");
Clazz_makeConstructor (c$, 
function () {
Clazz_superConstructor (this, JSV.common.JDXSpectrum, []);
this.headerTable =  new JU.List ();
this.xyCoords =  new Array (0);
this.parent = this;
});
$_M(c$, "copy", 
function () {
var newSpectrum =  new JSV.common.JDXSpectrum ();
this.copyTo (newSpectrum);
newSpectrum.setPeakList (this.peakList, this.piUnitsX, null);
return newSpectrum;
});
$_M(c$, "getXYCoords", 
function () {
return this.getCurrentSubSpectrum ().xyCoords;
});
$_M(c$, "getPeakList", 
function () {
return this.peakList;
});
$_M(c$, "setPeakList", 
function (list, piUnitsX, piUnitsY) {
this.peakList = list;
System.out.println ("setting peaklist for " + this + " to " + list);
if (this.peakList == null) System.out.println ("ohoh");
this.piUnitsX = piUnitsX;
this.piUnitsY = piUnitsY;
for (var i = list.size (); --i >= 0; ) this.peakList.get (i).spectrum = this;

if (J.util.Logger.debugging) J.util.Logger.info ("Spectrum " + this.getTitle () + " peaks: " + list.size ());
return list.size ();
}, "JU.List,~S,~S");
$_M(c$, "selectPeakByFileIndex", 
function (filePath, index) {
if (this.peakList != null && this.peakList.size () > 0) for (var i = 0; i < this.peakList.size (); i++) if (this.peakList.get (i).checkFileIndex (filePath, index)) {
System.out.println ("selecting peak in " + this + " " + this.peakList.get (i));
return (this.selectedPeak = this.peakList.get (i));
}
return null;
}, "~S,~S");
$_M(c$, "selectPeakByFilePathTypeModel", 
function (filePath, type, model) {
if (this.peakList != null && this.peakList.size () > 0) for (var i = 0; i < this.peakList.size (); i++) if (this.peakList.get (i).checkFileTypeModel (filePath, type, model)) {
System.out.println ("selecting peak in " + this + " " + this.peakList.get (i));
return (this.selectedPeak = this.peakList.get (i));
}
return null;
}, "~S,~S,~S");
$_M(c$, "matchesPeakTypeModel", 
function (type, model) {
if (this.peakList != null && this.peakList.size () > 0) for (var i = 0; i < this.peakList.size (); i++) if (this.peakList.get (i).checkTypeModel (type, model)) return true;

return false;
}, "~S,~S");
$_M(c$, "setSelectedPeak", 
function (peak) {
this.selectedPeak = peak;
}, "JSV.common.PeakInfo");
$_M(c$, "setHighlightedPeak", 
function (peak) {
this.highlightedPeak = peak;
}, "JSV.common.PeakInfo");
$_M(c$, "getSelectedPeak", 
function () {
return this.selectedPeak;
});
$_M(c$, "getModelPeakInfoForAutoSelectOnLoad", 
function () {
if (this.peakList != null) for (var i = 0; i < this.peakList.size (); i++) if (this.peakList.get (i).autoSelectOnLoad ()) return this.peakList.get (i);

return null;
});
$_M(c$, "getAssociatedPeakInfo", 
function (xPixel, coord) {
this.selectedPeak = this.findPeakByCoord (xPixel, coord);
return (this.selectedPeak == null ? this.getBasePeakInfo () : this.selectedPeak);
}, "~N,JSV.common.Coordinate");
$_M(c$, "findPeakByCoord", 
function (xPixel, coord) {
if (coord != null && this.peakList != null && this.peakList.size () > 0) {
var xVal = coord.getXVal ();
var iBest = -1;
var dBest = 1e100;
for (var i = 0; i < this.peakList.size (); i++) {
var d = this.peakList.get (i).checkRange (xPixel, xVal);
if (d < dBest) {
dBest = d;
iBest = i;
}}
if (iBest >= 0) return this.peakList.get (iBest);
}return null;
}, "~N,JSV.common.Coordinate");
$_M(c$, "getPeakTitle", 
function () {
return (this.selectedPeak != null ? this.selectedPeak.getTitle () : this.highlightedPeak != null ? this.highlightedPeak.getTitle () : this.getTitleLabel ());
});
$_M(c$, "getTitleLabel", 
function () {
var type = (this.peakList == null || this.peakList.size () == 0 ? this.getQualifiedDataType () : this.peakList.get (0).getType ());
return (type != null && type.length > 0 ? type + " " : "") + this.getTitle ();
});
$_M(c$, "setNextPeak", 
function (coord, istep) {
if (this.peakList == null || this.peakList.size () == 0) return -1;
var x0 = coord.getXVal () + istep * 0.000001;
var ipt1 = -1;
var ipt2 = -1;
var dmin1 = 1.7976931348623157E308 * istep;
var dmin2 = 0;
for (var i = this.peakList.size (); --i >= 0; ) {
var x = this.peakList.get (i).getX ();
if (istep > 0) {
if (x > x0 && x < dmin1) {
ipt1 = i;
dmin1 = x;
} else if (x < x0 && x - x0 < dmin2) {
ipt2 = i;
dmin2 = x - x0;
}} else {
if (x < x0 && x > dmin1) {
ipt1 = i;
dmin1 = x;
} else if (x > x0 && x - x0 > dmin2) {
ipt2 = i;
dmin2 = x - x0;
}}}
if (ipt1 < 0) {
if (ipt2 < 0) return -1;
ipt1 = ipt2;
}return ipt1;
}, "JSV.common.Coordinate,~N");
$_M(c$, "getPercentYValueAt", 
function (x) {
if (!this.isContinuous ()) return NaN;
return this.getYValueAt (x);
}, "~N");
$_M(c$, "getYValueAt", 
function (x) {
return JSV.common.Coordinate.getYValueAt (this.xyCoords, x);
}, "~N");
$_M(c$, "setUserYFactor", 
function (userYFactor) {
this.userYFactor = userYFactor;
}, "~N");
$_M(c$, "getUserYFactor", 
function () {
return this.userYFactor;
});
$_M(c$, "getConvertedSpectrum", 
function () {
return this.convertedSpectrum;
});
$_M(c$, "setConvertedSpectrum", 
function (spectrum) {
this.convertedSpectrum = spectrum;
}, "JSV.common.JDXSpectrum");
c$.taConvert = $_M(c$, "taConvert", 
function (spectrum, mode) {
if (!spectrum.isContinuous ()) return spectrum;
switch (mode) {
case JSV.common.JDXSpectrum.IRMode.NO_CONVERT:
return spectrum;
case JSV.common.JDXSpectrum.IRMode.TO_ABS:
if (!spectrum.isTransmittance ()) return spectrum;
break;
case JSV.common.JDXSpectrum.IRMode.TO_TRANS:
if (!spectrum.isAbsorbance ()) return spectrum;
break;
case JSV.common.JDXSpectrum.IRMode.TOGGLE:
break;
}
var spec = spectrum.getConvertedSpectrum ();
return (spec != null ? spec : spectrum.isAbsorbance () ? JSV.common.JDXSpectrum.toT (spectrum) : JSV.common.JDXSpectrum.toA (spectrum));
}, "JSV.common.JDXSpectrum,JSV.common.JDXSpectrum.IRMode");
c$.toT = $_M(c$, "toT", 
function (spectrum) {
if (!spectrum.isAbsorbance ()) return null;
var xyCoords = spectrum.getXYCoords ();
var newXYCoords =  new Array (xyCoords.length);
if (!JSV.common.Coordinate.isYInRange (xyCoords, 0, 4.0)) xyCoords = JSV.common.Coordinate.normalise (xyCoords, 0, 4.0);
for (var i = 0; i < xyCoords.length; i++) newXYCoords[i] =  new JSV.common.Coordinate ().set (xyCoords[i].getXVal (), JSV.common.JDXSpectrum.toTransmittance (xyCoords[i].getYVal ()));

return JSV.common.JDXSpectrum.newSpectrum (spectrum, newXYCoords, "TRANSMITTANCE");
}, "JSV.common.JDXSpectrum");
c$.toA = $_M(c$, "toA", 
function (spectrum) {
if (!spectrum.isTransmittance ()) return null;
var xyCoords = spectrum.getXYCoords ();
var newXYCoords =  new Array (xyCoords.length);
var isPercent = JSV.common.Coordinate.isYInRange (xyCoords, -2, 2);
for (var i = 0; i < xyCoords.length; i++) newXYCoords[i] =  new JSV.common.Coordinate ().set (xyCoords[i].getXVal (), JSV.common.JDXSpectrum.toAbsorbance (xyCoords[i].getYVal (), isPercent));

return JSV.common.JDXSpectrum.newSpectrum (spectrum, newXYCoords, "ABSORBANCE");
}, "JSV.common.JDXSpectrum");
c$.newSpectrum = $_M(c$, "newSpectrum", 
function (spectrum, newXYCoords, units) {
var specNew = spectrum.copy ();
specNew.setOrigin ("JSpecView Converted");
specNew.setOwner ("JSpecView Generated");
specNew.setXYCoords (newXYCoords);
specNew.setYUnits (units);
spectrum.setConvertedSpectrum (specNew);
specNew.setConvertedSpectrum (spectrum);
return specNew;
}, "JSV.common.JDXSpectrum,~A,~S");
c$.toAbsorbance = $_M(c$, "toAbsorbance", 
function (x, isPercent) {
return (Math.min (4.0, isPercent ? 2 - JSV.common.JDXSpectrum.log10 (x) : -JSV.common.JDXSpectrum.log10 (x)));
}, "~N,~B");
c$.toTransmittance = $_M(c$, "toTransmittance", 
function (x) {
return (x <= 0 ? 1 : Math.pow (10, -x));
}, "~N");
c$.log10 = $_M(c$, "log10", 
function (value) {
return Math.log (value) / Math.log (10);
}, "~N");
c$.process = $_M(c$, "process", 
function (specs, irMode) {
if (irMode === JSV.common.JDXSpectrum.IRMode.TO_ABS || irMode === JSV.common.JDXSpectrum.IRMode.TO_TRANS) for (var i = 0; i < specs.size (); i++) specs.set (i, JSV.common.JDXSpectrum.taConvert (specs.get (i), irMode));

return true;
}, "JU.List,JSV.common.JDXSpectrum.IRMode");
$_M(c$, "getSubSpectra", 
function () {
return this.subSpectra;
});
$_M(c$, "getCurrentSubSpectrum", 
function () {
return (this.subSpectra == null ? this : this.subSpectra.get (this.currentSubSpectrumIndex));
});
$_M(c$, "advanceSubSpectrum", 
function (dir) {
return this.setCurrentSubSpectrum (this.currentSubSpectrumIndex + dir);
}, "~N");
$_M(c$, "setCurrentSubSpectrum", 
function (n) {
return (this.currentSubSpectrumIndex = JSV.common.Coordinate.intoRange (n, 0, this.subSpectra.size () - 1));
}, "~N");
$_M(c$, "addSubSpectrum", 
function (spectrum, forceSub) {
if (!forceSub && (this.numDim < 2 || this.blockID != spectrum.blockID) || !JSV.common.JDXSpectrum.allowSubSpec (this, spectrum)) return false;
this.$isForcedSubset = forceSub;
if (this.subSpectra == null) {
this.subSpectra =  new JU.List ();
this.addSubSpectrum (this, true);
}this.subSpectra.addLast (spectrum);
spectrum.parent = this;
return true;
}, "JSV.common.JDXSpectrum,~B");
$_M(c$, "getSubIndex", 
function () {
return (this.subSpectra == null ? -1 : this.currentSubSpectrumIndex);
});
$_M(c$, "setExportXAxisDirection", 
function (leftToRight) {
this.exportXAxisLeftToRight = leftToRight;
}, "~B");
$_M(c$, "isExportXAxisLeftToRight", 
function () {
return this.exportXAxisLeftToRight;
});
$_M(c$, "getInfo", 
function (key) {
var info =  new java.util.Hashtable ();
if ("id".equalsIgnoreCase (key)) {
info.put (key, this.id);
return info;
}info.put ("id", this.id);
JSV.common.Parameters.putInfo (key, info, "specShift", Double.$valueOf (this.specShift));
var justHeader = ("header".equals (key));
if (!justHeader && key != null) {
for (var i = this.headerTable.size (); --i >= 0; ) {
var entry = this.headerTable.get (i);
if (entry[0].equalsIgnoreCase (key) || entry[2].equalsIgnoreCase (key)) {
info.put (key, entry[1]);
return info;
}}
}var head =  new java.util.Hashtable ();
var list = this.getHeaderRowDataAsArray ();
for (var i = 0; i < list.length; i++) {
var label = JSV.source.JDXSourceStreamTokenizer.cleanLabel (list[i][0]);
if (key != null && !justHeader && !label.equals (key)) continue;
var val = JSV.common.JDXSpectrum.fixInfoValue (list[i][1]);
if (key == null) {
var data =  new java.util.Hashtable ();
data.put ("value", val);
data.put ("index", Integer.$valueOf (i + 1));
info.put (label, data);
} else {
info.put (label, val);
}}
if (head.size () > 0) info.put ("header", head);
if (!justHeader) {
JSV.common.Parameters.putInfo (key, info, "titleLabel", this.getTitleLabel ());
JSV.common.Parameters.putInfo (key, info, "type", this.getDataType ());
JSV.common.Parameters.putInfo (key, info, "isHZToPPM", Boolean.$valueOf (this.$isHZtoPPM));
JSV.common.Parameters.putInfo (key, info, "subSpectrumCount", Integer.$valueOf (this.subSpectra == null ? 0 : this.subSpectra.size ()));
}return info;
}, "~S");
c$.fixInfoValue = $_M(c$, "fixInfoValue", 
function (info) {
try {
return (Integer.$valueOf (info));
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
try {
return (Double.$valueOf (info));
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
return info;
}, "~S");
$_V(c$, "toString", 
function () {
return this.getTitleLabel ();
});
$_M(c$, "findMatchingPeakInfo", 
function (pi) {
for (var i = 0; i < this.peakList.size (); i++) if (this.peakList.get (i).checkTypeMatch (pi)) return this.peakList.get (i);

return null;
}, "JSV.common.PeakInfo");
$_M(c$, "getBasePeakInfo", 
function () {
return (this.peakList.size () == 0 ?  new JSV.common.PeakInfo () :  new JSV.common.PeakInfo (" baseModel=\"\" " + this.peakList.get (0)));
});
$_M(c$, "getAxisLabel", 
function (isX) {
var units = (isX ? this.piUnitsX : this.piUnitsY);
if (units == null) units = (isX ? this.xLabel : this.yLabel);
if (units == null) units = (isX ? this.xUnits : this.yUnits);
return (units == null ? "" : units.equalsIgnoreCase ("WAVENUMBERS") ? "1/cm" : units.equalsIgnoreCase ("nanometers") ? "nm" : units);
}, "~B");
$_M(c$, "findXForPeakNearest", 
function (x) {
return JSV.common.Coordinate.findXForPeakNearest (this.xyCoords, x, this.isInverted ());
}, "~N");
$_M(c$, "addSpecShift", 
function (dx) {
if (dx != 0) {
this.specShift += dx;
JSV.common.Coordinate.shiftX (this.xyCoords, dx);
if (this.subSpectra != null) for (var i = this.subSpectra.size (); --i >= 0; ) {
var spec = this.subSpectra.get (i);
if (spec !== this && spec !== this.parent) spec.addSpecShift (dx);
}
}return this.specShift;
}, "~N");
c$.allowSubSpec = $_M(c$, "allowSubSpec", 
function (s1, s2) {
return (s1.is1D () == s2.is1D () && s1.xUnits.equalsIgnoreCase (s2.xUnits) && s1.isHNMR () == s2.isHNMR ());
}, "JSV.common.JDXSpectrum,JSV.common.JDXSpectrum");
c$.areXScalesCompatible = $_M(c$, "areXScalesCompatible", 
function (s1, s2, isSubspecCheck, isLinkCheck) {
var isNMR1 = s1.isNMR ();
if (isNMR1 != s2.isNMR ()) return false;
if (!isLinkCheck && !s1.xUnits.equalsIgnoreCase (s2.xUnits)) return false;
if (isSubspecCheck) {
if (s1.is1D () != s2.is1D ()) return false;
} else if (isLinkCheck) {
if (!isNMR1) return true;
} else if (!s1.is1D () || !s2.is1D ()) {
return false;
}return (!isNMR1 || s2.is1D () && s1.parent.nucleusX.equals (s2.parent.nucleusX));
}, "JSV.common.JDXSpectrum,JSV.common.JDXSpectrum,~B,~B");
c$.areLinkableX = $_M(c$, "areLinkableX", 
function (s1, s2) {
return (s1.isNMR () && s2.isNMR () && s1.nucleusX.equals (s2.nucleusX));
}, "JSV.common.JDXSpectrum,JSV.common.JDXSpectrum");
c$.areLinkableY = $_M(c$, "areLinkableY", 
function (s1, s2) {
return (s1.isNMR () && s2.isNMR () && s1.nucleusX.equals (s2.nucleusY));
}, "JSV.common.JDXSpectrum,JSV.common.JDXSpectrum");
$_M(c$, "setNHydrogens", 
function (nH) {
this.nH = nH;
}, "~N");
Clazz_pu$h ();
c$ = Clazz_declareType (JSV.common.JDXSpectrum, "IRMode", Enum);
c$.getMode = $_M(c$, "getMode", 
function (a) {
switch (a == null ? 'I' : a.toUpperCase ().charAt (0)) {
case 'A':
return JSV.common.JDXSpectrum.IRMode.TO_ABS;
case 'T':
return (a.equalsIgnoreCase ("TOGGLE") ? JSV.common.JDXSpectrum.IRMode.TOGGLE : JSV.common.JDXSpectrum.IRMode.TO_TRANS);
case 'N':
return JSV.common.JDXSpectrum.IRMode.NO_CONVERT;
default:
return JSV.common.JDXSpectrum.IRMode.TOGGLE;
}
}, "~S");
Clazz_defineEnumConstant (c$, "NO_CONVERT", 0, []);
Clazz_defineEnumConstant (c$, "TO_TRANS", 1, []);
Clazz_defineEnumConstant (c$, "TO_ABS", 2, []);
Clazz_defineEnumConstant (c$, "TOGGLE", 3, []);
c$ = Clazz_p0p ();
Clazz_defineStatics (c$,
"MAXABS", 4);
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["javajs.api.BytePoster", "$.JSInterface", "$.PlatformViewer", "javajs.awt.Dimension", "JSV.common.JDXSpectrum"], "JSV.common.JSViewer", ["java.lang.Boolean", "$.Double", "$.Float", "$.Thread", "java.net.URL", "java.util.Hashtable", "JU.List", "$.OC", "$.PT", "$.SB", "JSV.common.Annotation", "$.ExportType", "$.JSVFileManager", "$.PanelData", "$.PanelNode", "$.Parameters", "$.PeakInfo", "$.PrintLayout", "$.RepaintManager", "$.ScriptToken", "$.ScriptTokenizer", "JSV.source.JDXSource", "JSV.tree.SimpleTree", "JSV.util.JSVEscape", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.si = null;
this.g2d = null;
this.spectraTree = null;
this.currentSource = null;
this.panelNodes = null;
this.parameters = null;
this.repaintManager = null;
this.selectedPanel = null;
this.viewPanel = null;
this.properties = null;
this.scriptQueue = null;
this.fileHelper = null;
this.jsvpPopupMenu = null;
this.dialogManager = null;
this.viewDialog = null;
this.overlayLegendDialog = null;
this.irMode = null;
this.isSingleThreaded = false;
this.isApplet = false;
this.isJS = false;
this.isSigned = false;
this.recentScript = "";
this.appletID = null;
this.fullName = null;
this.syncID = null;
this.applet = null;
this.display = null;
this.maximumSize = 2147483647;
this.dimScreen = null;
this.apiPlatform = null;
this.popupAllowMenu = true;
this.popupZoomEnabled = true;
this.overlayLegendVisible = false;
this.recentStackPercent = 5;
this.lastPrintLayout = null;
this.offWindowFrame = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "JSViewer", null, [javajs.api.PlatformViewer, javajs.api.JSInterface, javajs.api.BytePoster]);
Clazz_prepareFields (c$, function () {
this.irMode = JSV.common.JDXSpectrum.IRMode.NO_CONVERT;
this.dimScreen =  new javajs.awt.Dimension (0, 0);
});
$_M(c$, "setProperty", 
function (key, value) {
if (this.properties != null) this.properties.setProperty (key, value);
}, "~S,~S");
$_M(c$, "setNode", 
function (node, fromTree) {
this.si.siSetNode (node, fromTree);
}, "JSV.common.PanelNode,~B");
Clazz_makeConstructor (c$, 
function (si, isApplet, isJS) {
this.si = si;
this.isApplet = isApplet;
this.isJS = isApplet && isJS;
this.isSigned = si.isSigned ();
this.apiPlatform = this.getPlatformInterface ("Platform");
this.apiPlatform.setViewer (this, this.display);
this.g2d = this.getPlatformInterface ("G2D");
this.spectraTree =  new JSV.tree.SimpleTree (this);
this.parameters = this.getPlatformInterface ("Parameters");
this.parameters.setName ("applet");
this.fileHelper = (this.getPlatformInterface ("FileHelper")).set (this);
this.isSingleThreaded = this.apiPlatform.isSingleThreaded ();
this.panelNodes =  new JU.List ();
this.repaintManager =  new JSV.common.RepaintManager (this);
if (!isApplet) this.setPopupMenu (true, true);
}, "JSV.api.ScriptInterface,~B,~B");
$_M(c$, "setPopupMenu", 
function (allowMenu, zoomEnabled) {
this.popupAllowMenu = allowMenu;
this.popupZoomEnabled = zoomEnabled;
}, "~B,~B");
$_M(c$, "showMenu", 
function (x, y) {
if (this.jsvpPopupMenu == null) {
try {
this.jsvpPopupMenu = this.getPlatformInterface ("Popup");
this.jsvpPopupMenu.jpiInitialize (this, this.isApplet ? "appletMenu" : "appMenu");
this.jsvpPopupMenu.setEnabled (this.popupAllowMenu, this.popupZoomEnabled);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
System.out.println (e + " initializing popup menu");
return;
} else {
throw e;
}
}
}this.jsvpPopupMenu.jpiShow (x, y);
}, "~N,~N");
$_M(c$, "runScriptNow", 
function (script) {
this.si.siIncrementViewCount (1);
if (script == null) script = "";
script = script.trim ();
if (script.startsWith ("!")) script = script.substring (1).trim ();
System.out.println ("RUNSCRIPT " + script);
var isOK = true;
var nErrorsLeft = 10;
var commandTokens =  new JSV.common.ScriptTokenizer (script, true);
var msg = null;
while (commandTokens.hasMoreTokens () && nErrorsLeft > 0 && isOK) {
var token = commandTokens.nextToken ();
var eachParam =  new JSV.common.ScriptTokenizer (token, false);
var key = JSV.common.ScriptToken.getKey (eachParam);
if (key == null) continue;
var st = JSV.common.ScriptToken.getScriptToken (key);
var value = JSV.common.ScriptToken.getValue (st, eachParam, token);
System.out.println ("KEY-> " + key + " VALUE-> " + value + " : " + st);
try {
switch (st) {
case JSV.common.ScriptToken.UNKNOWN:
J.util.Logger.info ("Unrecognized parameter: " + key);
--nErrorsLeft;
break;
default:
if (this.selectedPanel == null) break;
this.parameters.set (this.pd (), st, value);
this.si.siUpdateBoolean (st, JSV.common.Parameters.isTrue (value));
break;
case JSV.common.ScriptToken.PEAKCALLBACKFUNCTIONNAME:
case JSV.common.ScriptToken.SYNCCALLBACKFUNCTIONNAME:
case JSV.common.ScriptToken.COORDCALLBACKFUNCTIONNAME:
case JSV.common.ScriptToken.LOADFILECALLBACKFUNCTIONNAME:
this.si.siExecSetCallback (st, value);
break;
case JSV.common.ScriptToken.AUTOINTEGRATE:
this.si.siExecSetAutoIntegrate (JSV.common.Parameters.isTrue (value));
break;
case JSV.common.ScriptToken.CLOSE:
this.si.siExecClose (value);
break;
case JSV.common.ScriptToken.DEBUG:
J.util.Logger.setLogLevel (value.toLowerCase ().equals ("high") ? 6 : JSV.common.Parameters.isTrue (value) ? 5 : 4);
break;
case JSV.common.ScriptToken.GETPROPERTY:
var info = (this.selectedPanel == null ? null : this.getPropertyAsJavaObject (value));
if (info != null) this.selectedPanel.showMessage (JU.PT.toJSON (null, info), value);
break;
case JSV.common.ScriptToken.HIDDEN:
this.si.siExecHidden (JSV.common.Parameters.isTrue (value));
break;
case JSV.common.ScriptToken.INTEGRATIONRATIOS:
this.si.siSetIntegrationRatios (value);
if (this.selectedPanel != null) this.execIntegrate (null);
break;
case JSV.common.ScriptToken.INTERFACE:
this.si.siExecSetInterface (value);
break;
case JSV.common.ScriptToken.INTEGRALOFFSET:
case JSV.common.ScriptToken.INTEGRALRANGE:
this.execSetIntegralParameter (st, Double.parseDouble (value));
break;
case JSV.common.ScriptToken.JMOL:
this.si.syncToJmol (value);
break;
case JSV.common.ScriptToken.JSV:
this.syncScript (JU.PT.trimQuotes (value));
break;
case JSV.common.ScriptToken.LOAD:
msg = this.si.siExecLoad (value);
break;
case JSV.common.ScriptToken.LOADIMAGINARY:
this.si.siSetLoadImaginary (JSV.common.Parameters.isTrue (value));
break;
case JSV.common.ScriptToken.PEAK:
this.execPeak (value);
break;
case JSV.common.ScriptToken.PEAKLIST:
this.execPeakList (value);
break;
case JSV.common.ScriptToken.SCALEBY:
this.scaleSelectedBy (this.panelNodes, value);
break;
case JSV.common.ScriptToken.SCRIPT:
if (value.equals ("") || value.toLowerCase ().startsWith ("inline")) {
this.execScriptInline (value);
} else {
var s = this.si.siSetFileAsString (value);
if (s != null && this.si.siIncrementScriptLevelCount (0) < 100) this.runScriptNow (s);
}break;
case JSV.common.ScriptToken.SELECT:
this.execSelect (value);
break;
case JSV.common.ScriptToken.SPECTRUM:
case JSV.common.ScriptToken.SPECTRUMNUMBER:
if (!this.setSpectrum (value)) isOK = false;
break;
case JSV.common.ScriptToken.STACKOFFSETY:
this.execOverlayOffsetY (JU.PT.parseInt ("" + JU.PT.parseFloat (value)));
break;
case JSV.common.ScriptToken.TEST:
this.si.siExecTest (value);
break;
case JSV.common.ScriptToken.OVERLAY:
case JSV.common.ScriptToken.VIEW:
this.execView (value, true);
break;
case JSV.common.ScriptToken.FINDX:
case JSV.common.ScriptToken.GETSOLUTIONCOLOR:
case JSV.common.ScriptToken.INTEGRATION:
case JSV.common.ScriptToken.INTEGRATE:
case JSV.common.ScriptToken.IRMODE:
case JSV.common.ScriptToken.LABEL:
case JSV.common.ScriptToken.LINK:
case JSV.common.ScriptToken.OVERLAYSTACKED:
case JSV.common.ScriptToken.PRINT:
case JSV.common.ScriptToken.SETPEAK:
case JSV.common.ScriptToken.SETX:
case JSV.common.ScriptToken.SHIFTX:
case JSV.common.ScriptToken.SHOWERRORS:
case JSV.common.ScriptToken.SHOWMEASUREMENTS:
case JSV.common.ScriptToken.SHOWMENU:
case JSV.common.ScriptToken.SHOWKEY:
case JSV.common.ScriptToken.SHOWPEAKLIST:
case JSV.common.ScriptToken.SHOWINTEGRATION:
case JSV.common.ScriptToken.SHOWPROPERTIES:
case JSV.common.ScriptToken.SHOWSOURCE:
case JSV.common.ScriptToken.YSCALE:
case JSV.common.ScriptToken.WRITE:
case JSV.common.ScriptToken.ZOOM:
if (this.selectedPanel == null) {
isOK = false;
break;
}switch (st) {
default:
break;
case JSV.common.ScriptToken.FINDX:
this.pd ().findX (null, Double.parseDouble (value));
break;
case JSV.common.ScriptToken.GETSOLUTIONCOLOR:
this.selectedPanel.showMessage (this.getSolutionColorHtml (), "Predicted Colour");
break;
case JSV.common.ScriptToken.INTEGRATION:
case JSV.common.ScriptToken.INTEGRATE:
this.execIntegrate (value);
break;
case JSV.common.ScriptToken.IRMODE:
this.execIRMode (value);
break;
case JSV.common.ScriptToken.LABEL:
this.pd ().addAnnotation (JSV.common.ScriptToken.getTokens (value));
break;
case JSV.common.ScriptToken.LINK:
this.pd ().linkSpectra (JSV.common.PanelData.LinkMode.getMode (value));
break;
case JSV.common.ScriptToken.OVERLAYSTACKED:
this.pd ().splitStack (!JSV.common.Parameters.isTrue (value));
break;
case JSV.common.ScriptToken.PRINT:
msg = this.execWrite (null);
break;
case JSV.common.ScriptToken.SETPEAK:
this.pd ().shiftSpectrum (NaN, value.equalsIgnoreCase ("NONE") ? 1.7976931348623157E308 : value.equalsIgnoreCase ("?") ? 4.9E-324 : Double.parseDouble (value));
break;
case JSV.common.ScriptToken.SETX:
this.pd ().shiftSpectrum (4.9E-324, Double.parseDouble (value));
break;
case JSV.common.ScriptToken.SHIFTX:
this.pd ().shiftSpectrum (value.equalsIgnoreCase ("NONE") ? 1.7976931348623157E308 : Double.parseDouble (value), NaN);
break;
case JSV.common.ScriptToken.SHOWERRORS:
this.show ("errors");
break;
case JSV.common.ScriptToken.SHOWINTEGRATION:
this.pd ().showAnnotation (JSV.common.Annotation.AType.Integration, JSV.common.Parameters.getTFToggle (value));
break;
case JSV.common.ScriptToken.SHOWKEY:
this.setOverlayLegendVisibility (JSV.common.Parameters.getTFToggle (value), true);
break;
case JSV.common.ScriptToken.SHOWMEASUREMENTS:
this.pd ().showAnnotation (JSV.common.Annotation.AType.Measurements, JSV.common.Parameters.getTFToggle (value));
break;
case JSV.common.ScriptToken.SHOWMENU:
this.showMenu (-2147483648, 0);
break;
case JSV.common.ScriptToken.SHOWPEAKLIST:
this.pd ().showAnnotation (JSV.common.Annotation.AType.PeakList, JSV.common.Parameters.getTFToggle (value));
break;
case JSV.common.ScriptToken.SHOWPROPERTIES:
this.show ("properties");
break;
case JSV.common.ScriptToken.SHOWSOURCE:
this.show ("source");
break;
case JSV.common.ScriptToken.YSCALE:
this.setYScale (value);
break;
case JSV.common.ScriptToken.WINDOW:
this.si.siNewWindow (JSV.common.Parameters.isTrue (value), false);
break;
case JSV.common.ScriptToken.WRITE:
msg = this.execWrite (value);
break;
case JSV.common.ScriptToken.ZOOM:
isOK = this.execZoom (value);
break;
}
break;
}
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
{
alert(e + "\n" + Clazz_getStackTrace())
}isOK = false;
--nErrorsLeft;
} else {
throw e;
}
}
}
this.si.siIncrementViewCount (-1);
this.si.siExecScriptComplete (msg, true);
return isOK;
}, "~S");
$_M(c$, "pd", 
function () {
return this.selectedPanel.getPanelData ();
});
$_M(c$, "execPeak", 
function (value) {
try {
var tokens = JSV.common.ScriptToken.getTokens (value);
value = " type=\"" + tokens.get (0).toUpperCase () + "\" _match=\"" + JU.PT.trimQuotes (tokens.get (1).toUpperCase ()) + "\"";
if (tokens.size () > 2 && tokens.get (2).equalsIgnoreCase ("all")) value += " title=\"ALL\"";
this.processPeakPickEvent ( new JSV.common.PeakInfo (value), false);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}, "~S");
$_M(c$, "execPeakList", 
function (value) {
var jsvp = this.selectedPanel;
var p = this.parameters;
var b = JSV.common.Parameters.getTFToggle (value);
if (value.indexOf ("=") < 0) {
if (jsvp != null) jsvp.getPanelData ().getPeakListing (null, b);
} else {
var tokens = JSV.common.ScriptToken.getTokens (value);
for (var i = tokens.size (); --i >= 0; ) {
var token = tokens.get (i);
var pt = token.indexOf ("=");
if (pt <= 0) continue;
var key = token.substring (0, pt);
value = token.substring (pt + 1);
try {
if (key.startsWith ("thr")) {
p.peakListThreshold = Double.$valueOf (value).doubleValue ();
} else if (key.startsWith ("int")) {
p.peakListInterpolation = (value.equalsIgnoreCase ("none") ? "NONE" : "parabolic");
}if (jsvp != null) jsvp.getPanelData ().getPeakListing (p, Boolean.TRUE);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}
}}, "~S");
$_M(c$, "execZoom", 
function (value) {
var x1 = 0;
var x2 = 0;
var y1 = 0;
var y2 = 0;
var tokens;
tokens = JSV.common.ScriptToken.getTokens (value);
switch (tokens.size ()) {
default:
return false;
case 1:
this.zoomTo (tokens.get (0));
return true;
case 2:
x1 = Double.parseDouble (tokens.get (0));
x2 = Double.parseDouble (tokens.get (1));
break;
case 4:
x1 = Double.parseDouble (tokens.get (0));
y1 = Double.parseDouble (tokens.get (1));
x2 = Double.parseDouble (tokens.get (2));
y2 = Double.parseDouble (tokens.get (3));
}
this.pd ().setZoom (x1, y1, x2, y2);
return true;
}, "~S");
$_M(c$, "zoomTo", 
function (value) {
var pd = this.pd ();
if (value.equalsIgnoreCase ("next")) {
pd.nextView ();
} else if (value.toLowerCase ().startsWith ("prev")) {
pd.previousView ();
} else if (value.equalsIgnoreCase ("out")) {
pd.resetView ();
} else if (value.equalsIgnoreCase ("clear")) {
pd.clearAllView ();
}}, "~S");
$_M(c$, "scaleSelectedBy", 
function (nodes, value) {
try {
var f = Double.parseDouble (value);
for (var i = nodes.size (); --i >= 0; ) nodes.get (i).jsvp.getPanelData ().scaleSelectedBy (f);

} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}, "JU.List,~S");
$_M(c$, "execSelect", 
function (value) {
var nodes = this.panelNodes;
for (var i = nodes.size (); --i >= 0; ) nodes.get (i).jsvp.getPanelData ().selectFromEntireSet (-2147483648);

var speclist =  new JU.List ();
this.fillSpecList (value, speclist, false);
}, "~S");
$_M(c$, "execView", 
function (value, fromScript) {
if (value.equals ("")) {
this.checkOverlay ();
return;
}var speclist =  new JU.List ();
var strlist = this.fillSpecList (value, speclist, true);
if (speclist.size () > 0) this.si.siOpenDataOrFile (null, strlist, speclist, strlist, -1, -1, false);
if (!fromScript) {
this.si.siValidateAndRepaint ();
}}, "~S,~B");
$_M(c$, "execIRMode", 
function (value) {
var mode = JSV.common.JDXSpectrum.IRMode.getMode (value);
var pd = this.pd ();
var spec = pd.getSpectrum ();
var spec2 = JSV.common.JDXSpectrum.taConvert (spec, mode);
if (spec2 === spec) return;
pd.setSpectrum (spec2);
this.setIRmode (value);
}, "~S");
$_M(c$, "execIntegrate", 
function (value) {
var jsvp = this.selectedPanel;
if (jsvp == null) return;
jsvp.getPanelData ().checkIntegral (this.parameters, value);
var integrationRatios = this.si.siGetIntegrationRatios ();
if (integrationRatios != null) jsvp.getPanelData ().setIntegrationRatios (integrationRatios);
this.si.siSetIntegrationRatios (null);
jsvp.doRepaint (true);
}, "~S");
$_M(c$, "execSetIntegralParameter", 
function (st, value) {
var p = this.parameters;
switch (st) {
case JSV.common.ScriptToken.INTEGRALRANGE:
p.integralRange = value;
break;
case JSV.common.ScriptToken.INTEGRALOFFSET:
p.integralOffset = value;
break;
}
var jsvp = this.selectedPanel;
if (jsvp == null) return;
jsvp.getPanelData ().checkIntegral (this.parameters, "update");
}, "JSV.common.ScriptToken,~N");
$_M(c$, "setYScale", 
function (value) {
var tokens = JSV.common.ScriptToken.getTokens (value);
var pt = 0;
var isAll = false;
if (tokens.size () > 1 && tokens.get (0).equalsIgnoreCase ("ALL")) {
isAll = true;
pt++;
}var y1 = Double.parseDouble (tokens.get (pt++));
var y2 = Double.parseDouble (tokens.get (pt));
if (isAll) {
var spec = this.pd ().getSpectrum ();
for (var i = this.panelNodes.size (); --i >= 0; ) {
var node = this.panelNodes.get (i);
if (node.source !== this.currentSource) continue;
if (JSV.common.JDXSpectrum.areXScalesCompatible (spec, node.getSpectrum (), false, false)) node.jsvp.getPanelData ().setZoom (0, y1, 0, y2);
}
} else {
this.pd ().setZoom (0, y1, 0, y2);
}}, "~S");
$_M(c$, "setOverlayLegendVisibility", 
function (tftoggle, doSet) {
if (doSet) this.overlayLegendVisible = (tftoggle == null ? !this.overlayLegendVisible : tftoggle === Boolean.TRUE);
var node = JSV.common.PanelNode.findNode (this.selectedPanel, this.panelNodes);
for (var i = this.panelNodes.size (); --i >= 0; ) this.showOverlayLegend (this.panelNodes.get (i), this.panelNodes.get (i) === node && this.overlayLegendVisible);

}, "Boolean,~B");
$_M(c$, "showOverlayLegend", 
function (node, visible) {
var legend = node.legend;
if (legend == null && visible) {
legend = node.setLegend (node.jsvp.getPanelData ().getNumberOfSpectraInCurrentSet () > 1 && node.jsvp.getPanelData ().getNumberOfGraphSets () == 1 ? this.getDialog (JSV.common.Annotation.AType.OverlayLegend, null) : null);
}if (legend != null) legend.setVisible (visible);
}, "JSV.common.PanelNode,~B");
$_M(c$, "addHighLight", 
function (x1, x2, r, g, b, a) {
var jsvp = this.selectedPanel;
if (jsvp != null) {
jsvp.getPanelData ().addHighlight (null, x1, x2, null, r, g, b, a);
jsvp.doRepaint (false);
}}, "~N,~N,~N,~N,~N,~N");
$_M(c$, "syncScript", 
function (peakScript) {
if (peakScript.equals ("TEST")) peakScript = JSV.common.JSViewer.testScript;
J.util.Logger.info ("JSViewer.syncScript Jmol>JSV " + peakScript);
if (peakScript.indexOf ("<PeakData") < 0) {
this.runScriptNow (peakScript);
if (peakScript.indexOf ("#SYNC_PEAKS") >= 0) {
var source = this.currentSource;
if (source == null) return;
try {
var file = "file=" + JSV.util.JSVEscape.eS (source.getFilePath ());
var peaks = source.getSpectra ().get (0).getPeakList ();
var sb =  new JU.SB ();
sb.append ("[");
var n = peaks.size ();
for (var i = 0; i < n; i++) {
var s = peaks.get (i).toString ();
s = s + " " + file;
sb.append (JSV.util.JSVEscape.eS (s));
if (i > 0) sb.append (",");
}
sb.append ("]");
this.si.syncToJmol ("Peaks: " + sb);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}return;
}peakScript = JU.PT.simpleReplace (peakScript, "\\\"", "");
var file = JU.PT.getQuotedAttribute (peakScript, "file");
System.out.println ("file2=" + file);
var index = JU.PT.getQuotedAttribute (peakScript, "index");
if (file == null || index == null) return;
var model = JU.PT.getQuotedAttribute (peakScript, "model");
var jmolSource = JU.PT.getQuotedAttribute (peakScript, "src");
var modelSent = (jmolSource != null && jmolSource.startsWith ("Jmol") ? null : this.si.siGetReturnFromJmolModel ());
if (model != null && modelSent != null && !model.equals (modelSent)) {
J.util.Logger.info ("JSV ignoring model " + model + "; should be " + modelSent);
return;
}this.si.siSetReturnFromJmolModel (null);
if (this.panelNodes.size () == 0 || !this.checkFileAlreadyLoaded (file)) {
J.util.Logger.info ("file " + file + " not found -- JSViewer closing all and reopening");
this.si.siSyncLoad (file);
}var pi = this.selectPanelByPeak (peakScript);
var jsvp = this.selectedPanel;
var type = JU.PT.getQuotedAttribute (peakScript, "type");
jsvp.getPanelData ().selectSpectrum (file, type, model, true);
this.si.siSendPanelChange (jsvp);
jsvp.getPanelData ().addPeakHighlight (pi);
jsvp.doRepaint (true);
if (jmolSource == null || (pi != null && pi.getAtoms () != null)) this.si.syncToJmol (this.jmolSelect (pi));
}, "~S");
$_M(c$, "checkFileAlreadyLoaded", 
function (fileName) {
var jsvp = this.selectedPanel;
if (jsvp != null && jsvp.getPanelData ().hasFileLoaded (fileName)) return true;
for (var i = this.panelNodes.size (); --i >= 0; ) if (this.panelNodes.get (i).jsvp.getPanelData ().hasFileLoaded (fileName)) {
this.si.siSetSelectedPanel (this.panelNodes.get (i).jsvp);
return true;
}
return false;
}, "~S");
$_M(c$, "selectPanelByPeak", 
function (peakScript) {
if (this.panelNodes == null) return null;
var file = JU.PT.getQuotedAttribute (peakScript, "file");
var index = JU.PT.getQuotedAttribute (peakScript, "index");
var pi = null;
for (var i = this.panelNodes.size (); --i >= 0; ) this.panelNodes.get (i).jsvp.getPanelData ().addPeakHighlight (null);

var jsvp = this.selectedPanel;
pi = jsvp.getPanelData ().selectPeakByFileIndex (file, index);
System.out.println (Thread.currentThread () + "JSViewer selectPanelByPeak pi = " + pi);
if (pi != null) {
this.setNode (JSV.common.PanelNode.findNode (jsvp, this.panelNodes), false);
} else {
for (var i = this.panelNodes.size (); --i >= 0; ) {
var node = this.panelNodes.get (i);
if ((pi = node.jsvp.getPanelData ().selectPeakByFileIndex (file, index)) != null) {
this.setNode (node, false);
break;
}}
}return pi;
}, "~S");
$_M(c$, "processPeakPickEvent", 
function (eventObj, isApp) {
var pi;
if (Clazz_instanceOf (eventObj, JSV.common.PeakInfo)) {
pi = eventObj;
var jsvp = this.selectedPanel;
var pi2 = jsvp.getPanelData ().findMatchingPeakInfo (pi);
if (pi2 == null) {
if (!"ALL".equals (pi.getTitle ())) return;
var node = null;
for (var i = 0; i < this.panelNodes.size (); i++) if ((pi2 = this.panelNodes.get (i).jsvp.getPanelData ().findMatchingPeakInfo (pi)) != null) {
node = this.panelNodes.get (i);
break;
}
if (node == null) return;
this.setNode (node, false);
}pi = pi2;
} else {
var e = (eventObj);
this.si.siSetSelectedPanel (e.getSource ());
pi = e.getPeakInfo ();
}this.pd ().addPeakHighlight (pi);
this.syncToJmol (pi);
if (pi.isClearAll ()) this.selectedPanel.doRepaint (false);
 else this.pd ().selectSpectrum (pi.getFilePath (), pi.getType (), pi.getModel (), true);
this.si.siCheckCallbacks (pi.getTitle ());
}, "~O,~B");
$_M(c$, "syncToJmol", 
function (pi) {
this.si.siSetReturnFromJmolModel (pi.getModel ());
this.si.syncToJmol (this.jmolSelect (pi));
}, "JSV.common.PeakInfo");
$_M(c$, "sendPanelChange", 
function (jsvp) {
var pd = jsvp.getPanelData ();
var spec = pd.getSpectrum ();
var pi = spec.getSelectedPeak ();
if (pi == null) pi = spec.getModelPeakInfoForAutoSelectOnLoad ();
if (pi == null) pi = spec.getBasePeakInfo ();
pd.addPeakHighlight (pi);
J.util.Logger.info (Thread.currentThread () + "JSViewer sendFrameChange " + jsvp);
this.syncToJmol (pi);
}, "JSV.api.JSVPanel");
$_M(c$, "jmolSelect", 
function (pi) {
var script = null;
if ("IR".equals (pi.getType ()) || "RAMAN".equals (pi.getType ())) {
script = "vibration ON; selectionHalos OFF;";
} else if (pi.getAtoms () != null) {
script = "vibration OFF; selectionhalos ON;";
} else {
script = "vibration OFF; selectionhalos OFF;";
}script = "Select: " + pi + " script=\"" + script;
System.out.println ("JSViewer.jmolSelect " + script);
return script;
}, "JSV.common.PeakInfo");
$_M(c$, "getPropertyAsJavaObject", 
function (key) {
var isAll = false;
if (key != null && key.toUpperCase ().startsWith ("ALL ") || "all".equalsIgnoreCase (key)) {
key = key.substring (3).trim ();
isAll = true;
}if ("".equals (key)) key = null;
var map =  new java.util.Hashtable ();
var map0 = this.pd ().getInfo (true, key);
if (!isAll && map0 != null) return map0;
if (map0 != null) map.put ("current", map0);
var info =  new JU.List ();
for (var i = 0; i < this.panelNodes.size (); i++) {
var jsvp = this.panelNodes.get (i).jsvp;
if (jsvp == null) continue;
info.addLast (this.panelNodes.get (i).getInfo (key));
}
map.put ("items", info);
return map;
}, "~S");
$_M(c$, "getCoordinate", 
function () {
if (this.selectedPanel != null) {
var coord = this.pd ().getClickedCoordinate ();
if (coord != null) return coord.getXVal () + " " + coord.getYVal ();
}return "";
});
$_M(c$, "fillSpecList", 
function (value, speclist, isView) {
var prefix = "1.";
var list;
var list0 = null;
var isNone = (value.equalsIgnoreCase ("NONE"));
if (isNone || value.equalsIgnoreCase ("all")) value = "*";
if (value.indexOf ("*") < 0) {
var tokens = value.$plit (" ");
var sb =  new JU.SB ();
for (var i = 0; i < tokens.length; i++) {
var pt = tokens[i].indexOf ('.');
if (pt != tokens[i].lastIndexOf ('.')) tokens[i] = tokens[i].substring (0, pt + 1) + tokens[i].substring (pt + 1).$replace ('.', '_');
sb.append (tokens[i]).append (" ");
}
value = sb.toString ().trim ();
}if (value.equals ("*")) {
list = JSV.common.ScriptToken.getTokens (JSV.common.PanelNode.getSpectrumListAsString (this.panelNodes));
} else if (value.startsWith ("\"")) {
list = JSV.common.ScriptToken.getTokens (value);
} else {
value = JU.PT.simpleReplace (value, "_", " _ ");
value = JU.PT.simpleReplace (value, "-", " - ");
list = JSV.common.ScriptToken.getTokens (value);
list0 = JSV.common.ScriptToken.getTokens (JSV.common.PanelNode.getSpectrumListAsString (this.panelNodes));
if (list0.size () == 0) return null;
}var id0 = (this.selectedPanel == null ? prefix : JSV.common.PanelNode.findNode (this.selectedPanel, this.panelNodes).id);
id0 = id0.substring (0, id0.indexOf (".") + 1);
var sb =  new JU.SB ();
var n = list.size ();
var idLast = null;
for (var i = 0; i < n; i++) {
var id = list.get (i);
var userYFactor = NaN;
var isubspec = -1;
if (i + 1 < n && list.get (i + 1).equals ("*")) {
i += 2;
userYFactor = Double.parseDouble (list.get (i));
} else if (i + 1 < n && list.get (i + 1).equals ("_")) {
i += 2;
isubspec = Integer.parseInt (list.get (i));
}if (id.equals ("-")) {
if (idLast == null) idLast = list0.get (0);
id = (i + 1 == n ? list0.get (list0.size () - 1) : list.get (++i));
if (!id.contains (".")) id = id0 + id;
var pt = 0;
while (pt < list0.size () && !list0.get (pt).equals (idLast)) pt++;

pt++;
while (pt < list0.size () && !idLast.equals (id)) {
speclist.addLast (JSV.common.PanelNode.findNodeById (idLast = list0.get (pt++), this.panelNodes).jsvp.getPanelData ().getSpectrumAt (0));
sb.append (",").append (idLast);
}
continue;
}var node;
if (id.startsWith ("\"")) {
id = JU.PT.trim (id, "\"");
for (var j = 0; j < this.panelNodes.size (); j++) {
node = this.panelNodes.get (j);
if (node.fileName != null && node.fileName.startsWith (id) || node.frameTitle != null && node.frameTitle.startsWith (id)) {
this.addSpecToList (node.jsvp.getPanelData (), userYFactor, -1, speclist, isView);
sb.append (",").append (node.id);
}}
continue;
}if (!id.contains (".")) id = id0 + id;
node = JSV.common.PanelNode.findNodeById (id, this.panelNodes);
if (node == null) continue;
idLast = id;
this.addSpecToList (node.jsvp.getPanelData (), userYFactor, isubspec, speclist, isView);
sb.append (",").append (id);
if (isubspec > 0) sb.append (".").appendI (isubspec);
}
if (isView && speclist.size () == 1) {
var node = JSV.common.PanelNode.findNodeById (idLast, this.panelNodes);
if (node != null) {
this.setNode (node, true);
speclist.clear ();
}}return (isNone ? "NONE" : sb.length () > 0 ? sb.toString ().substring (1) : null);
}, "~S,JU.List,~B");
$_M(c$, "addSpecToList", 
function (pd, userYFactor, isubspec, list, isView) {
if (isView) {
var spec = pd.getSpectrumAt (0);
spec.setUserYFactor (Double.isNaN (userYFactor) ? 1 : userYFactor);
pd.addToList (isubspec - 1, list);
} else {
pd.selectFromEntireSet (isubspec - 1);
}}, "JSV.common.PanelData,~N,~N,JU.List,~B");
$_M(c$, "getSolutionColor", 
function () {
var spectrum = this.pd ().getSpectrum ();
return (spectrum.canShowSolutionColor () ? (JSV.common.JSViewer.getInterface ("JSV.common.Visible")).getColour (spectrum.getXYCoords (), spectrum.getYUnits ()) : "255,255,255");
});
$_M(c$, "getSolutionColorHtml", 
function () {
var color = this.getSolutionColor ();
return "<html><body style='background-color:rgb(" + color + ")'><br />Predicted Solution Colour- RGB(" + color + ")<br /><br /></body></html>";
});
$_M(c$, "openDataOrFile", 
function (data, name, specs, strUrl, firstSpec, lastSpec, isAppend) {
if ("NONE".equals (name)) {
this.close ("View*");
return 0;
}this.si.writeStatus ("");
var filePath = null;
var newPath = null;
var fileName = null;
var isView = false;
if (data != null) {
} else if (specs != null) {
isView = true;
newPath = fileName = filePath = "View" + this.si.siIncrementViewCount (1);
} else if (strUrl != null) {
try {
var u =  new java.net.URL (JSV.common.JSVFileManager.appletDocumentBase, strUrl, null);
filePath = u.toString ();
this.si.siSetRecentURL (filePath);
fileName = JSV.common.JSVFileManager.getName (filePath);
} catch (e) {
if (Clazz_exceptionOf (e, java.net.MalformedURLException)) {
var file = this.apiPlatform.newFile (strUrl);
fileName = file.getName ();
newPath = filePath = file.getFullPath ();
this.si.siSetRecentURL (null);
} else {
throw e;
}
}
}if (!isView) if (JSV.common.PanelNode.isOpen (this.panelNodes, filePath) || JSV.common.PanelNode.isOpen (this.panelNodes, strUrl)) {
this.si.writeStatus (filePath + " is already open");
return -1;
}if (!isAppend && !isView) this.close ("all");
this.si.setCursor (3);
try {
this.si.siSetCurrentSource (isView ? JSV.source.JDXSource.createView (specs) : this.si.siCreateSource (data, filePath, firstSpec, lastSpec));
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
{
alert(e + "\n" + Clazz_getStackTrace())
}this.si.setCursor (0);
return -3;
} else {
throw e;
}
}
this.si.setCursor (0);
System.gc ();
if (newPath == null) {
newPath = this.currentSource.getFilePath ();
if (newPath != null) fileName = newPath.substring (newPath.lastIndexOf ("/") + 1);
} else {
this.currentSource.setFilePath (newPath);
}this.si.siSetLoaded (fileName, newPath);
var spec = this.currentSource.getJDXSpectrum (0);
if (spec == null) {
return -4;
}specs = this.currentSource.getSpectra ();
JSV.common.JDXSpectrum.process (specs, this.irMode);
var autoOverlay = this.si.siGetAutoCombine () || spec.isAutoOverlayFromJmolClick ();
var combine = isView || autoOverlay && this.currentSource.isCompoundSource;
if (combine) {
this.combineSpectra ((isView ? strUrl : null));
} else {
this.splitSpectra ();
}this.selectedPanel.getPanelData ().taintedAll = true;
if (!isView) this.si.siUpdateRecentMenus (filePath);
return 0;
}, "~S,~S,JU.List,~S,~N,~N,~B");
$_M(c$, "close", 
function (value) {
if (value == null || value.equalsIgnoreCase ("all") || value.equals ("*")) {
this.si.siCloseSource (null);
return;
}value = value.$replace ('\\', '/');
if (value.endsWith ("*")) {
value = value.substring (0, value.length - 1);
for (var i = this.panelNodes.size (); --i >= 0; ) if (i < this.panelNodes.size () && this.panelNodes.get (i).fileName.startsWith (value)) this.si.siCloseSource (this.panelNodes.get (i).source);

} else if (value.equals ("selected")) {
var list =  new JU.List ();
var lastSource = null;
for (var i = this.panelNodes.size (); --i >= 0; ) {
var source = this.panelNodes.get (i).source;
if (this.panelNodes.get (i).isSelected && (lastSource == null || lastSource !== source)) list.addLast (source);
lastSource = source;
}
for (var i = list.size (); --i >= 0; ) this.si.siCloseSource (list.get (i));

} else {
var source = (value.length == 0 ? this.currentSource : JSV.common.PanelNode.findSourceByNameOrId (value, this.panelNodes));
if (source == null) return;
this.si.siCloseSource (source);
}if (this.selectedPanel == null && this.panelNodes.size () > 0) this.si.siSetSelectedPanel (JSV.common.PanelNode.getLastFileFirstNode (this.panelNodes));
}, "~S");
$_M(c$, "load", 
function (value) {
var tokens = JSV.common.ScriptToken.getTokens (value);
var filename = tokens.get (0);
var pt = 0;
var isAppend = filename.equalsIgnoreCase ("APPEND");
var isCheck = filename.equalsIgnoreCase ("CHECK");
if (isAppend || isCheck) filename = tokens.get (++pt);
var isSimulation = filename.equalsIgnoreCase ("MOL");
if (isSimulation) filename = "http://SIMULATION/" + "MOL=" + JU.PT.trimQuotes (tokens.get (++pt));
if (!isCheck && !isAppend) {
if (filename.equals ("\"\"") && this.currentSource != null) filename = this.currentSource.getFilePath ();
this.close ("all");
}filename = JU.PT.trimQuotes (filename);
if (filename.startsWith ("$")) {
isSimulation = true;
filename = "http://SIMULATION/" + filename;
}var firstSpec = (pt + 1 < tokens.size () ? Integer.$valueOf (tokens.get (++pt)).intValue () : -1);
var lastSpec = (pt + 1 < tokens.size () ? Integer.$valueOf (tokens.get (++pt)).intValue () : firstSpec);
this.si.siOpenDataOrFile (null, null, null, filename, firstSpec, lastSpec, isAppend);
}, "~S");
$_M(c$, "combineSpectra", 
function (name) {
var source = this.currentSource;
var specs = source.getSpectra ();
var jsvp = this.si.siGetNewJSVPanel2 (specs);
jsvp.setTitle (source.getTitle ());
if (jsvp.getTitle ().equals ("")) {
jsvp.getPanelData ().setViewTitle (source.getFilePath ());
jsvp.setTitle (name);
}this.si.siSetPropertiesFromPreferences (jsvp, true);
this.si.siCreateTree (source, [jsvp]).getPanelNode ().isView = true;
var node = JSV.common.PanelNode.findNode (this.selectedPanel, this.panelNodes);
node.setFrameTitle (name);
node.isView = true;
if (this.si.siGetAutoShowLegend () && this.pd ().getNumberOfGraphSets () == 1) node.setLegend (this.getDialog (JSV.common.Annotation.AType.OverlayLegend, null));
this.si.siSetMenuEnables (node, false);
}, "~S");
$_M(c$, "closeSource", 
function (source) {
var rootNode = this.spectraTree.getRootNode ();
var fileName = (source == null ? null : source.getFilePath ());
var toDelete =  new JU.List ();
var enume = rootNode.children ();
while (enume.hasMoreElements ()) {
var node = enume.nextElement ();
if (fileName == null || node.getPanelNode ().source.getFilePath ().equals (fileName)) {
for (var e = node.children (); e.hasMoreElements (); ) {
var childNode = e.nextElement ();
toDelete.addLast (childNode);
this.panelNodes.removeObj (childNode.getPanelNode ());
}
toDelete.addLast (node);
if (fileName != null) break;
}}
this.spectraTree.deleteNodes (toDelete);
if (source == null) {
if (this.currentSource != null) this.currentSource.dispose ();
this.currentSource = null;
if (this.selectedPanel != null) this.selectedPanel.dispose ();
} else {
}if (this.currentSource === source) {
this.si.siSetSelectedPanel (null);
this.si.siSetCurrentSource (null);
}var max = 0;
for (var i = 0; i < this.panelNodes.size (); i++) {
var f = JU.PT.parseFloat (this.panelNodes.get (i).id);
if (f >= max + 1) max = Clazz_doubleToInt (Math.floor (f));
}
this.si.siSetFileCount (max);
System.gc ();
J.util.Logger.checkMemory ();
}, "JSV.source.JDXSource");
$_M(c$, "setFrameAndTreeNode", 
function (i) {
if (this.panelNodes == null || i < 0 || i >= this.panelNodes.size ()) return;
this.setNode (this.panelNodes.get (i), false);
}, "~N");
$_M(c$, "selectFrameNode", 
function (jsvp) {
var node = JSV.common.PanelNode.findNode (jsvp, this.panelNodes);
if (node == null) return null;
this.spectraTree.setPath (this.spectraTree.newTreePath (node.treeNode.getPath ()));
this.setOverlayLegendVisibility (null, false);
return node;
}, "JSV.api.JSVPanel");
$_M(c$, "setSpectrum", 
function (value) {
if (value.indexOf ('.') >= 0) {
var node = JSV.common.PanelNode.findNodeById (value, this.panelNodes);
if (node == null) return false;
this.setNode (node, false);
} else {
var n = JU.PT.parseInt (value);
if (n <= 0) {
this.checkOverlay ();
return false;
}this.setFrameAndTreeNode (n - 1);
}return true;
}, "~S");
$_M(c$, "splitSpectra", 
function () {
var source = this.currentSource;
var specs = source.getSpectra ();
var panels =  new Array (specs.size ());
var jsvp = null;
for (var i = 0; i < specs.size (); i++) {
var spec = specs.get (i);
jsvp = this.si.siGetNewJSVPanel (spec);
this.si.siSetPropertiesFromPreferences (jsvp, true);
panels[i] = jsvp;
}
this.si.siCreateTree (source, panels);
this.si.siGetNewJSVPanel (null);
var node = JSV.common.PanelNode.findNode (this.selectedPanel, this.panelNodes);
this.si.siSetMenuEnables (node, true);
});
$_M(c$, "selectedTreeNode", 
function (node) {
if (node == null) {
return;
}if (node.isLeaf ()) {
this.setNode (node.getPanelNode (), true);
} else {
System.out.println ("not a leaf");
}this.si.siSetCurrentSource (node.getPanelNode ().source);
}, "JSV.api.JSVTreeNode");
$_M(c$, "removeAllHighlights", 
function () {
var jsvp = this.selectedPanel;
if (jsvp != null) {
jsvp.getPanelData ().removeAllHighlights ();
jsvp.doRepaint (false);
}});
$_M(c$, "removeHighlight", 
function (x1, x2) {
var jsvp = this.selectedPanel;
if (jsvp != null) {
jsvp.getPanelData ().removeHighlight (x1, x2);
jsvp.doRepaint (false);
}}, "~N,~N");
$_M(c$, "dispose", 
function () {
this.fileHelper = null;
if (this.viewDialog != null) this.viewDialog.dispose ();
this.viewDialog = null;
if (this.overlayLegendDialog != null) this.overlayLegendDialog.dispose ();
this.overlayLegendDialog = null;
if (this.jsvpPopupMenu != null) {
this.jsvpPopupMenu.jpiDispose ();
this.jsvpPopupMenu = null;
}if (this.panelNodes != null) for (var i = this.panelNodes.size (); --i >= 0; ) {
this.panelNodes.get (i).dispose ();
this.panelNodes.remove (i);
}
});
$_M(c$, "runScript", 
function (script) {
if (this.scriptQueue == null) this.si.siProcessCommand (script);
 else this.scriptQueue.addLast (script);
}, "~S");
$_M(c$, "requestRepaint", 
function () {
if (this.selectedPanel != null) this.repaintManager.refresh ();
});
$_M(c$, "repaintDone", 
function () {
this.repaintManager.repaintDone ();
});
$_M(c$, "checkOverlay", 
function () {
if (this.viewPanel != null) this.viewPanel.markSelectedPanels (this.panelNodes);
this.viewDialog = this.getDialog (JSV.common.Annotation.AType.Views, null);
});
$_M(c$, "execOverlayOffsetY", 
function (offset) {
if (this.selectedPanel == null) return;
if (offset == -2147483648) {
var soffset = this.selectedPanel.getInput ("Enter a vertical offset in percent for stacked plots", "Overlay", "" + this.recentStackPercent);
var f = JU.PT.parseFloat (soffset);
if (Float.isNaN (f)) return;
offset = Clazz_floatToInt (f);
}this.recentStackPercent = offset;
this.pd ().setYStackOffsetPercent (offset);
}, "~N");
$_M(c$, "execScriptInline", 
function (script) {
if (script.length > 0) script = script.substring (6).trim ();
if (script.length == 0) script = this.selectedPanel.getInput ("Enter a JSpecView script", "Script", this.recentScript);
if (script == null) return;
this.recentScript = script;
this.runScriptNow (script);
}, "~S");
$_M(c$, "setDisplay", 
function (canvas) {
this.apiPlatform.setViewer (this, this.display = canvas);
var wh =  Clazz_newIntArray (2, 0);
this.apiPlatform.getFullScreenDimensions (canvas, wh);
this.setScreenDimension (wh[0], wh[1]);
}, "~O");
$_V(c$, "setScreenDimension", 
function (width, height) {
height = Math.min (height, this.maximumSize);
width = Math.min (width, this.maximumSize);
if (this.dimScreen.width == width && this.dimScreen.height == height) return;
this.resizeImage (width, height);
}, "~N,~N");
$_M(c$, "resizeImage", 
function (width, height) {
if (width > 0) {
this.dimScreen.width = width;
this.dimScreen.height = height;
} else {
width = (this.dimScreen.width == 0 ? this.dimScreen.width = 500 : this.dimScreen.width);
height = (this.dimScreen.height == 0 ? this.dimScreen.height = 500 : this.dimScreen.height);
}this.g2d.setWindowParameters (width, height);
}, "~N,~N");
$_V(c$, "updateJS", 
function (width, height) {
if (this.selectedPanel == null) return;
{
this.selectedPanel.paintComponent(this.apiPlatform.context);
}}, "~N,~N");
$_V(c$, "processMouseEvent", 
function (id, x, y, modifiers, time) {
return (this.selectedPanel != null && this.selectedPanel.processMouseEvent (id, x, y, modifiers, time));
}, "~N,~N,~N,~N,~N");
$_V(c$, "processTwoPointGesture", 
function (touches) {
if (this.selectedPanel != null) this.selectedPanel.processTwoPointGesture (touches);
}, "~A");
$_V(c$, "getApplet", 
function () {
return this.applet;
});
$_V(c$, "startHoverWatcher", 
function (enable) {
}, "~B");
$_V(c$, "cacheFileByName", 
function (fileName, isAdd) {
return 0;
}, "~S,~B");
$_V(c$, "cachePut", 
function (key, data) {
}, "~S,~O");
$_V(c$, "openFileAsyncSpecial", 
function (fileName, flags) {
}, "~S,~N");
$_M(c$, "getHeight", 
function () {
return this.dimScreen.height;
});
$_M(c$, "getWidth", 
function () {
return this.dimScreen.width;
});
$_M(c$, "getPlatformInterface", 
function (type) {
return JSV.common.JSViewer.getInterface ("JSV." + (this.isJS ? "js2d.Js" : "java.Awt") + type);
}, "~S");
$_M(c$, "getDialogManager", 
function () {
if (this.dialogManager != null) return this.dialogManager;
this.dialogManager = this.getPlatformInterface ("DialogManager");
return this.dialogManager.set (this);
});
$_M(c$, "getDialog", 
function (type, spec) {
var root = "JSV.dialog.";
switch (type) {
case JSV.common.Annotation.AType.Integration:
return (JSV.common.JSViewer.getInterface (root + "IntegrationDialog")).setParams ("Integration for " + spec, this, spec);
case JSV.common.Annotation.AType.Measurements:
return (JSV.common.JSViewer.getInterface (root + "MeasurementsDialog")).setParams ("Measurements for " + spec, this, spec);
case JSV.common.Annotation.AType.PeakList:
return (JSV.common.JSViewer.getInterface (root + "PeakListDialog")).setParams ("Peak List for " + spec, this, spec);
case JSV.common.Annotation.AType.OverlayLegend:
return this.overlayLegendDialog = (JSV.common.JSViewer.getInterface (root + "OverlayLegendDialog")).setParams (this.pd ().getViewTitle (), this, null);
case JSV.common.Annotation.AType.Views:
return this.viewDialog = (JSV.common.JSViewer.getInterface (root + "ViewsDialog")).setParams ("View/Combine/Close Spectra", this, null);
default:
return null;
}
}, "JSV.common.Annotation.AType,JSV.common.JDXSpectrum");
$_M(c$, "show", 
function (what) {
this.getDialogManager ();
if (what.equals ("properties")) {
this.dialogManager.showProperties (null, this.pd ().getSpectrum ());
} else if (what.equals ("errors")) {
this.dialogManager.showSourceErrors (null, this.currentSource);
} else if (what.equals ("source")) {
if (this.currentSource == null) {
if (this.panelNodes.size () > 0) this.dialogManager.showMessageDialog (null, "Please Select a Spectrum", "Select Spectrum", 0);
return;
}this.dialogManager.showSource (this, this.currentSource);
}}, "~S");
$_M(c$, "getDialogPrint", 
function (isJob) {
if (!this.isJS) try {
var pl = (this.getPlatformInterface ("PrintDialog")).set (this.offWindowFrame, this.lastPrintLayout, isJob).getPrintLayout ();
if (pl != null) this.lastPrintLayout = pl;
return pl;
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
return  new JSV.common.PrintLayout ();
}, "~B");
$_M(c$, "setIRmode", 
function (mode) {
if (mode.equals ("AtoT")) {
this.irMode = JSV.common.JDXSpectrum.IRMode.TO_TRANS;
} else if (mode.equals ("TtoA")) {
this.irMode = JSV.common.JDXSpectrum.IRMode.TO_ABS;
} else {
this.irMode = JSV.common.JDXSpectrum.IRMode.getMode (mode);
}}, "~S");
$_M(c$, "getOptionFromDialog", 
function (items, title, label) {
this.getDialogManager ().getOptionFromDialog (null, items, this.selectedPanel, title, label);
return 0;
}, "~A,~S,~S");
$_M(c$, "execWrite", 
function (value) {
if (this.isJS && value == null) value = "PDF";
var msg = (JSV.common.JSViewer.getInterface ("JSV.export.Exporter")).write (this, value == null ? null : JSV.common.ScriptToken.getTokens (value), false);
this.si.writeStatus (msg);
return msg;
}, "~S");
$_M(c$, "$export", 
function (type, n) {
if (type == null) type = "XY";
var pd = this.pd ();
var nMax = pd.getNumberOfSpectraInCurrentSet ();
if (n < -1 || n >= nMax) return "Maximum spectrum index (0-based) is " + (nMax - 1) + ".";
var spec = (n < 0 ? pd.getSpectrum () : pd.getSpectrumAt (n));
try {
return (JSV.common.JSViewer.getInterface ("JSV.export.Exporter")).exportTheSpectrum (this, JSV.common.ExportType.getType (type), null, spec, 0, spec.getXYCoords ().length - 1, null);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
System.out.println (e);
return null;
} else {
throw e;
}
}
}, "~S,~N");
$_V(c$, "postByteArray", 
function (fileName, bytes) {
return JSV.common.JSVFileManager.postByteArray (fileName, bytes);
}, "~S,~A");
$_M(c$, "getOutputChannel", 
function (fileName, isBinary) {
var os = null;
{
while (fileName.startsWith("/"))
fileName = fileName.substring(1);
}return  new JU.OC ().setParams (this, fileName, !isBinary, os);
}, "~S,~B");
c$.getInterface = $_M(c$, "getInterface", 
function (name) {
try {
var x = Class.forName (name);
return (x == null ? null : x.newInstance ());
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
J.util.Logger.error ("Interface.java Error creating instance for " + name + ": \n" + e);
return null;
} else {
throw e;
}
}
}, "~S");
Clazz_defineStatics (c$,
"sourceLabel", "Original...",
"FILE_OPEN_OK", 0,
"FILE_OPEN_ALREADY", -1,
"FILE_OPEN_ERROR", -3,
"FILE_OPEN_NO_DATA", -4,
"OVERLAY_DIALOG", -1,
"OVERLAY_OFFSET", 99,
"PORTRAIT", 1,
"PAGE_EXISTS", 0,
"NO_SUCH_PAGE", 1,
"noColor", "255,255,255",
"testScript", "<PeakData  index=\"1\" title=\"\" model=\"~1.1\" type=\"1HNMR\" xMin=\"3.2915\" xMax=\"3.2965\" atoms=\"15,16,17,18,19,20\" multiplicity=\"\" integral=\"1\"> src=\"JPECVIEW\" file=\"http://SIMULATION/$caffeine\"",
"NLEVEL_MAX", 100);
});
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.PanelNode", ["JU.SB", "JSV.common.Parameters"], function () {
c$ = Clazz_decorateAsClass (function () {
this.treeNode = null;
this.source = null;
this.fileName = null;
this.jsvp = null;
this.id = null;
this.legend = null;
this.isSelected = false;
this.isView = false;
this.frameTitle = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PanelNode");
Clazz_makeConstructor (c$, 
function (id, fileName, source, jsvp) {
this.id = id;
this.source = source;
this.fileName = fileName;
this.jsvp = jsvp;
if (jsvp != null) {
jsvp.getPanelData ().getSpectrumAt (0).setId (id);
this.frameTitle = jsvp.getTitle ();
}}, "~S,~S,JSV.source.JDXSource,JSV.api.JSVPanel");
$_M(c$, "setTreeNode", 
function (node) {
this.treeNode = node;
}, "JSV.api.JSVTreeNode");
$_M(c$, "getTreeNode", 
function () {
return this.treeNode;
});
$_M(c$, "dispose", 
function () {
this.source.dispose ();
if (this.jsvp != null) this.jsvp.dispose ();
this.source = null;
this.jsvp = null;
this.legend = null;
});
$_M(c$, "getSpectrum", 
function () {
return this.jsvp.getPanelData ().getSpectrum ();
});
$_M(c$, "setLegend", 
function (legend) {
if (this.legend != null) this.legend.dispose ();
this.legend = legend;
return legend;
}, "JSV.dialog.JSVDialog");
$_V(c$, "toString", 
function () {
return ((this.id == null ? "" : this.id + ": ") + (this.frameTitle == null ? this.fileName : this.frameTitle));
});
c$.findSourceByNameOrId = $_M(c$, "findSourceByNameOrId", 
function (id, panelNodes) {
for (var i = panelNodes.size (); --i >= 0; ) {
var node = panelNodes.get (i);
if (id.equals (node.id) || id.equalsIgnoreCase (node.source.getFilePath ())) return node.source;
}
for (var i = panelNodes.size (); --i >= 0; ) {
var node = panelNodes.get (i);
if (id.equals (node.fileName)) return node.source;
}
return null;
}, "~S,JU.List");
c$.findNodeById = $_M(c$, "findNodeById", 
function (id, panelNodes) {
for (var i = panelNodes.size (); --i >= 0; ) if (id.equals (panelNodes.get (i).id)) return panelNodes.get (i);

return null;
}, "~S,JU.List");
c$.findNode = $_M(c$, "findNode", 
function (jsvp, panelNodes) {
for (var i = panelNodes.size (); --i >= 0; ) if (panelNodes.get (i).jsvp === jsvp) return panelNodes.get (i);

return null;
}, "JSV.api.JSVPanel,JU.List");
c$.getSpectrumListAsString = $_M(c$, "getSpectrumListAsString", 
function (panelNodes) {
var sb =  new JU.SB ();
for (var i = 0; i < panelNodes.size (); i++) {
var id = panelNodes.get (i).id;
sb.append (" ").append (id);
}
return sb.toString ().trim ();
}, "JU.List");
c$.isOpen = $_M(c$, "isOpen", 
function (panelNodes, filePath) {
if (filePath != null) for (var i = panelNodes.size (); --i >= 0; ) {
if (filePath.equals (panelNodes.get (i).source.getFilePath ()) || filePath.equals (panelNodes.get (i).frameTitle)) return true;
}
return false;
}, "JU.List,~S");
$_M(c$, "setFrameTitle", 
function (name) {
this.frameTitle = name;
}, "~S");
c$.getLastFileFirstNode = $_M(c$, "getLastFileFirstNode", 
function (panelNodes) {
var n = panelNodes.size ();
var node = (n == 0 ? null : panelNodes.get (n - 1));
for (var i = n - 1; --i >= 0; ) {
if (panelNodes.get (i).source !== node.source) break;
node = panelNodes.get (i);
}
return (node == null ? null : node.jsvp);
}, "JU.List");
$_M(c$, "getInfo", 
function (key) {
var info = this.jsvp.getPanelData ().getInfo (false, key);
JSV.common.Parameters.putInfo (key, info, "panelId", this.id);
JSV.common.Parameters.putInfo (key, info, "panelFileName", this.fileName);
JSV.common.Parameters.putInfo (key, info, "panelSource", this.source.getFilePath ());
return info;
}, "~S");
});
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.Parameters", ["java.lang.Boolean", "java.util.Hashtable", "JSV.common.ScriptToken"], function () {
c$ = Clazz_decorateAsClass (function () {
this.name = null;
this.integralMinY = 0.1;
this.integralRange = 50.0;
this.integralOffset = 30.0;
this.integralDrawAll = false;
this.peakListThreshold = NaN;
this.peakListInterpolation = "parabolic";
this.precision = 2;
this.htBooleans = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "Parameters");
Clazz_makeConstructor (c$, 
function () {
this.htBooleans =  new java.util.Hashtable ();
this.setBoolean (JSV.common.ScriptToken.TITLEON, true);
this.setBoolean (JSV.common.ScriptToken.ENABLEZOOM, true);
this.setBoolean (JSV.common.ScriptToken.DISPLAY2D, true);
this.setBoolean (JSV.common.ScriptToken.COORDINATESON, true);
this.setBoolean (JSV.common.ScriptToken.GRIDON, true);
this.setBoolean (JSV.common.ScriptToken.XSCALEON, true);
this.setBoolean (JSV.common.ScriptToken.YSCALEON, true);
this.setBoolean (JSV.common.ScriptToken.XUNITSON, true);
this.setBoolean (JSV.common.ScriptToken.YUNITSON, true);
});
$_M(c$, "setName", 
function (name) {
this.name = name;
return this;
}, "~S");
$_M(c$, "getBooleans", 
function () {
return this.htBooleans;
});
$_M(c$, "setBoolean", 
function (st, val) {
this.htBooleans.put (st, Boolean.$valueOf (val));
return val;
}, "JSV.common.ScriptToken,~B");
$_M(c$, "getBoolean", 
function (t) {
return Boolean.TRUE === this.htBooleans.get (t);
}, "JSV.common.ScriptToken");
c$.isTrue = $_M(c$, "isTrue", 
function (value) {
return (value.length == 0 || Boolean.parseBoolean (value));
}, "~S");
c$.getTFToggle = $_M(c$, "getTFToggle", 
function (value) {
return (value.equalsIgnoreCase ("TOGGLE") ? null : JSV.common.Parameters.isTrue (value) ? Boolean.TRUE : Boolean.FALSE);
}, "~S");
$_M(c$, "setP", 
function (pd, st, value) {
switch (st) {
default:
return;
case JSV.common.ScriptToken.COORDINATESON:
case JSV.common.ScriptToken.DISPLAY1D:
case JSV.common.ScriptToken.DISPLAY2D:
case JSV.common.ScriptToken.ENABLEZOOM:
case JSV.common.ScriptToken.GRIDON:
case JSV.common.ScriptToken.REVERSEPLOT:
case JSV.common.ScriptToken.TITLEON:
case JSV.common.ScriptToken.TITLEBOLDON:
case JSV.common.ScriptToken.XSCALEON:
case JSV.common.ScriptToken.XUNITSON:
case JSV.common.ScriptToken.YSCALEON:
case JSV.common.ScriptToken.YUNITSON:
var tfToggle = JSV.common.Parameters.getTFToggle (value);
if (tfToggle != null) {
this.setBoolean (st, tfToggle.booleanValue ());
break;
}if (pd == null) return;
var b = !pd.getBoolean (st);
switch (st) {
default:
break;
case JSV.common.ScriptToken.XSCALEON:
this.setBoolean (JSV.common.ScriptToken.XUNITSON, b);
pd.setBoolean (JSV.common.ScriptToken.XUNITSON, b);
break;
case JSV.common.ScriptToken.YSCALEON:
this.setBoolean (JSV.common.ScriptToken.YUNITSON, b);
pd.setBoolean (JSV.common.ScriptToken.YUNITSON, b);
break;
}
this.setBoolean (st, b);
break;
}
if (pd == null) return;
pd.setBooleans (this, st);
}, "JSV.common.PanelData,JSV.common.ScriptToken,~S");
c$.isMatch = $_M(c$, "isMatch", 
function (match, key) {
return match == null || key.equalsIgnoreCase (match);
}, "~S,~S");
c$.putInfo = $_M(c$, "putInfo", 
function (match, info, key, value) {
if (value != null && JSV.common.Parameters.isMatch (match, key)) info.put (match == null ? key : match, value);
}, "~S,java.util.Map,~S,~O");
});
Clazz_declarePackage ("JSV.util");
Clazz_load (null, "JSV.util.JSVEscape", ["JU.SB"], function () {
c$ = Clazz_declareType (JSV.util, "JSVEscape");
c$.eS = $_M(c$, "eS", 
function (str) {
if (str == null) return "\"\"";
var haveEscape = false;
var i = 0;
for (; i < "\\\\\tt\rr\nn\"\"".length; i += 2) if (str.indexOf ("\\\\\tt\rr\nn\"\"".charAt (i)) >= 0) {
haveEscape = true;
break;
}
if (haveEscape) while (i < "\\\\\tt\rr\nn\"\"".length) {
var pt = -1;
var ch = "\\\\\tt\rr\nn\"\"".charAt (i++);
var ch2 = "\\\\\tt\rr\nn\"\"".charAt (i++);
var sb =  new JU.SB ();
var pt0 = 0;
while ((pt = str.indexOf (ch, pt + 1)) >= 0) {
sb.append (str.substring (pt0, pt)).appendC ('\\').appendC (ch2);
pt0 = pt + 1;
}
sb.append (str.substring (pt0, str.length));
str = sb.toString ();
}
for (i = str.length; --i >= 0; ) if (str.charCodeAt (i) > 0x7F) str = str.substring (0, i) + JSV.util.JSVEscape.unicode (str.charAt (i)) + str.substring (i + 1);

return "\"" + str + "\"";
}, "~S");
c$.unicode = $_M(c$, "unicode", 
function (c) {
var s = "0000" + Integer.toHexString (c.charCodeAt (0));
return "\\u" + s.substring (s.length - 4);
}, "~S");
Clazz_defineStatics (c$,
"escapable", "\\\\\tt\rr\nn\"\"");
});
Clazz_declarePackage ("JU");
Clazz_load (["java.io.OutputStream"], "JU.OC", ["java.io.BufferedWriter", "$.ByteArrayOutputStream", "$.OutputStreamWriter", "JU.SB"], function () {
c$ = Clazz_decorateAsClass (function () {
this.bytePoster = null;
this.fileName = null;
this.bw = null;
this.isLocalFile = false;
this.byteCount = 0;
this.isCanceled = false;
this.closed = false;
this.os = null;
this.sb = null;
this.type = null;
Clazz_instantialize (this, arguments);
}, JU, "OC", java.io.OutputStream);
$_M(c$, "setParams", 
function (bytePoster, fileName, asWriter, os) {
this.bytePoster = bytePoster;
this.fileName = fileName;
this.os = os;
this.isLocalFile = (fileName != null && !(fileName.startsWith ("http://") || fileName.startsWith ("https://")));
if (asWriter && os != null) this.bw =  new java.io.BufferedWriter ( new java.io.OutputStreamWriter (os));
return this;
}, "javajs.api.BytePoster,~S,~B,java.io.OutputStream");
$_M(c$, "getFileName", 
function () {
return this.fileName;
});
$_M(c$, "getByteCount", 
function () {
return this.byteCount;
});
$_M(c$, "setType", 
function (type) {
this.type = type;
}, "~S");
$_M(c$, "getType", 
function () {
return this.type;
});
$_M(c$, "append", 
function (s) {
try {
if (this.bw != null) {
this.bw.write (s);
} else if (this.os == null) {
if (this.sb == null) this.sb =  new JU.SB ();
this.sb.append (s);
} else {
var b = s.getBytes ();
this.os.write (b, 0, b.length);
this.byteCount += b.length;
return this;
}} catch (e) {
if (Clazz_exceptionOf (e, java.io.IOException)) {
} else {
throw e;
}
}
this.byteCount += s.length;
return this;
}, "~S");
$_V(c$, "write", 
function (buf, i, len) {
if (this.os == null) this.os =  new java.io.ByteArrayOutputStream ();
{
this.os.write(buf, i, len);
}this.byteCount += len;
}, "~A,~N,~N");
$_V(c$, "writeByteAsInt", 
function (b) {
if (this.os == null) this.os =  new java.io.ByteArrayOutputStream ();
{
this.os.writeByteAsInt(b);
}this.byteCount++;
}, "~N");
$_M(c$, "cancel", 
function () {
this.isCanceled = true;
this.closeChannel ();
});
$_M(c$, "closeChannel", 
function () {
if (this.closed) return null;
try {
if (this.bw != null) {
this.bw.flush ();
this.bw.close ();
} else if (this.os != null) {
this.os.flush ();
this.os.close ();
}} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
if (this.isCanceled) {
this.closed = true;
return null;
}if (this.fileName == null || this.closed) return (this.sb == null ? null : this.sb.toString ());
this.closed = true;
{
var data = (this.sb == null ? this.toByteArray() :
this.sb.toString()); if (typeof this.fileName == "function") {
this.fileName(data); } else { Jmol._doAjax(this.fileName,
null, data); }
}return null;
});
$_M(c$, "toByteArray", 
function () {
return (Clazz_instanceOf (this.os, java.io.ByteArrayOutputStream) ? (this.os).toByteArray () : null);
});
$_M(c$, "close", 
function () {
this.closeChannel ();
});
$_V(c$, "toString", 
function () {
if (this.bw != null) try {
this.bw.flush ();
} catch (e) {
if (Clazz_exceptionOf (e, java.io.IOException)) {
} else {
throw e;
}
}
if (this.sb != null) return this.closeChannel ();
return this.byteCount + " bytes";
});
$_M(c$, "postByteArray", 
function () {
var bytes = (this.sb == null ? this.toByteArray () : this.sb.toString ().getBytes ());
return this.bytePoster.postByteArray (this.fileName, bytes);
});
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum", "JSV.common.Coordinate"], "JSV.common.Annotation", ["java.lang.Double", "JU.CU"], function () {
c$ = Clazz_decorateAsClass (function () {
this.text = "";
this.$isPixels = false;
this.is2D = false;
this.offsetX = 0;
this.offsetY = 0;
this.spec = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "Annotation", JSV.common.Coordinate);
$_M(c$, "setA", 
function (x, y, spec, text, isPixels, is2D, offsetX, offsetY) {
this.set (x, y);
this.spec = spec;
this.text = text;
this.$isPixels = isPixels;
this.is2D = is2D;
this.offsetX = offsetX;
this.offsetY = offsetY;
return this;
}, "~N,~N,JSV.common.JDXSpectrum,~S,~B,~B,~N,~N");
$_M(c$, "setSpec", 
function (spec) {
this.spec = spec;
return this;
}, "JSV.common.JDXSpectrum");
$_M(c$, "addSpecShift", 
function (dx) {
this.setXVal (this.getXVal () + dx);
}, "~N");
$_M(c$, "isPixels", 
function () {
return this.$isPixels;
});
$_V(c$, "toString", 
function () {
return "[" + this.getXVal () + ", " + this.getYVal () + "," + this.text + "]";
});
c$.getColoredAnnotation = $_M(c$, "getColoredAnnotation", 
function (g2d, spec, args, lastAnnotation) {
var arg;
var xPt = 0;
var yPt = 1;
var colorPt = 2;
var textPt = 3;
var nArgs = args.size ();
try {
switch (nArgs) {
default:
return null;
case 1:
arg = args.get (0);
xPt = yPt = -1;
if (arg.charAt (0) == '\"') {
textPt = 0;
colorPt = -1;
} else {
colorPt = 0;
textPt = -1;
}break;
case 2:
xPt = yPt = -1;
arg = args.get (0);
if (arg.charAt (0) == '\"') {
textPt = 0;
colorPt = 1;
} else {
colorPt = 0;
textPt = 1;
}break;
case 3:
case 4:
arg = args.get (2);
if (arg.charAt (0) == '\"') {
textPt = 2;
colorPt = (nArgs == 4 ? 3 : -1);
} else {
colorPt = 2;
textPt = (nArgs == 4 ? 3 : -1);
}arg = args.get (2);
if (arg.charAt (0) == '\"') {
textPt = 2;
colorPt = -1;
} else {
colorPt = 2;
textPt = -1;
}}
if (lastAnnotation == null && (xPt < 0 || yPt < 0 || textPt < 0 || colorPt < 0)) return null;
var x = (xPt < 0 ? lastAnnotation.getXVal () : Double.$valueOf (args.get (xPt)).doubleValue ());
var y = (yPt < 0 ? lastAnnotation.getYVal () : Double.$valueOf (args.get (yPt)).doubleValue ());
var color = (colorPt < 0 ? (lastAnnotation).getColor () : g2d.getColor1 (JU.CU.getArgbFromString (args.get (colorPt))));
var text;
if (textPt < 0) {
text = lastAnnotation.text;
} else {
text = args.get (textPt);
if (text.charAt (0) == '\"') text = text.substring (1, text.length - 1);
}return  new JSV.common.ColoredAnnotation ().setCA (x, y, spec, text, color, false, false, 0, 0);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return null;
} else {
throw e;
}
}
}, "JSV.api.JSVGraphics,JSV.common.JDXSpectrum,JU.List,JSV.common.Annotation");
Clazz_pu$h ();
c$ = Clazz_declareType (JSV.common.Annotation, "AType", Enum);
Clazz_defineEnumConstant (c$, "Integration", 0, []);
Clazz_defineEnumConstant (c$, "PeakList", 1, []);
Clazz_defineEnumConstant (c$, "Measurements", 2, []);
Clazz_defineEnumConstant (c$, "OverlayLegend", 3, []);
Clazz_defineEnumConstant (c$, "Views", 4, []);
Clazz_defineEnumConstant (c$, "NONE", 5, []);
c$ = Clazz_p0p ();
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum"], "JSV.common.ExportType", null, function () {
c$ = Clazz_declareType (JSV.common, "ExportType", Enum);
c$.getType = $_M(c$, "getType", 
function (type) {
type = type.toUpperCase ();
if (type.equalsIgnoreCase ("Original...")) return JSV.common.ExportType.SOURCE;
if (type.startsWith ("XML")) return JSV.common.ExportType.AML;
for (var mode, $mode = 0, $$mode = JSV.common.ExportType.values (); $mode < $$mode.length && ((mode = $$mode[$mode]) || true); $mode++) if (mode.name ().equals (type)) return mode;

return JSV.common.ExportType.UNK;
}, "~S");
c$.isExportMode = $_M(c$, "isExportMode", 
function (ext) {
return (JSV.common.ExportType.getType (ext) !== JSV.common.ExportType.UNK);
}, "~S");
Clazz_defineEnumConstant (c$, "UNK", 0, []);
Clazz_defineEnumConstant (c$, "SOURCE", 1, []);
Clazz_defineEnumConstant (c$, "DIF", 2, []);
Clazz_defineEnumConstant (c$, "FIX", 3, []);
Clazz_defineEnumConstant (c$, "SQZ", 4, []);
Clazz_defineEnumConstant (c$, "PAC", 5, []);
Clazz_defineEnumConstant (c$, "XY", 6, []);
Clazz_defineEnumConstant (c$, "DIFDUP", 7, []);
Clazz_defineEnumConstant (c$, "PNG", 8, []);
Clazz_defineEnumConstant (c$, "JPG", 9, []);
Clazz_defineEnumConstant (c$, "SVG", 10, []);
Clazz_defineEnumConstant (c$, "SVGI", 11, []);
Clazz_defineEnumConstant (c$, "CML", 12, []);
Clazz_defineEnumConstant (c$, "AML", 13, []);
Clazz_defineEnumConstant (c$, "PDF", 14, []);
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum", "javajs.api.EventManager", "java.util.Hashtable", "JU.List"], "JSV.common.PanelData", ["java.lang.Boolean", "$.Double", "javajs.awt.Font", "JU.CU", "JSV.api.JSVGraphics", "JSV.common.Annotation", "$.Coordinate", "$.GraphSet", "$.JDXSpectrum", "$.JSVFileManager", "$.JSVersion", "$.MeasurementData", "$.Parameters", "$.PeakPickEvent", "$.ScriptToken", "$.SubSpecChangeEvent", "$.ZoomEvent", "JSV.dialog.JSVDialog", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.g2d = null;
this.g2d0 = null;
this.viewer = null;
this.listeners = null;
this.currentGraphSet = null;
this.options = null;
this.jsvp = null;
this.graphSets = null;
this.currentSplitPoint = 0;
this.thisWidget = null;
this.coordClicked = null;
this.coordsClicked = null;
this.ctrlPressed = false;
this.shiftPressed = false;
this.drawXAxisLeftToRight = false;
this.isIntegralDrag = false;
this.xAxisLeftToRight = true;
this.scalingFactor = 1;
this.integralShiftMode = 0;
this.left = 60;
this.right = 50;
this.coordStr = "";
this.startupPinTip = "Click to set.";
this.title = null;
this.clickCount = 0;
this.nSpectra = 0;
this.thisWidth = 0;
this.thisHeight = 0;
this.startIndex = 0;
this.endIndex = 0;
this.commonFilePath = null;
this.viewTitle = null;
this.displayFontName = null;
this.titleFontName = null;
this.isPrinting = false;
this.doReset = true;
this.printingFontName = null;
this.printGraphPosition = "default";
this.titleDrawn = false;
this.display1D = false;
this.isLinked = false;
this.printJobTitle = null;
this.spectra = null;
this.taintedAll = true;
this.currentFont = null;
this.mouseState = null;
this.gridOn = false;
this.titleOn = false;
this.mouseX = 0;
this.mouseY = 0;
this.linking = false;
this.xPixelClicked = 0;
this.coordinatesColor = null;
this.gridColor = null;
this.integralPlotColor = null;
this.peakTabColor = null;
this.plotAreaColor = null;
this.scaleColor = null;
this.titleColor = null;
this.unitsColor = null;
this.highlightColor = null;
this.zoomBoxColor = null;
this.zoomBoxColor2 = null;
this.BLACK = null;
this.bgcolor = null;
this.optionsSaved = null;
this.gMain = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PanelData", null, javajs.api.EventManager);
Clazz_prepareFields (c$, function () {
this.listeners =  new JU.List ();
this.options =  new java.util.Hashtable ();
});
Clazz_makeConstructor (c$, 
function (panel, viewer) {
this.viewer = viewer;
this.jsvp = panel;
this.g2d = this.g2d0 = viewer.g2d;
this.BLACK = this.g2d.getColor1 (0);
this.highlightColor = this.g2d.getColor4 (255, 0, 0, 200);
this.zoomBoxColor = this.g2d.getColor4 (150, 150, 100, 130);
this.zoomBoxColor2 = this.g2d.getColor4 (150, 100, 100, 130);
}, "JSV.api.JSVPanel,JSV.common.JSViewer");
$_M(c$, "addListener", 
function (listener) {
if (!this.listeners.contains (listener)) {
this.listeners.addLast (listener);
}}, "JSV.api.PanelListener");
$_M(c$, "getCurrentGraphSet", 
function () {
return this.currentGraphSet;
});
$_M(c$, "dispose", 
function () {
this.jsvp = null;
for (var i = 0; i < this.graphSets.size (); i++) this.graphSets.get (i).dispose ();

this.graphSets = null;
this.currentFont = null;
this.currentGraphSet = null;
this.coordClicked = null;
this.coordsClicked = null;
this.thisWidget = null;
this.options = null;
this.listeners = null;
});
$_M(c$, "setViewTitle", 
function (title) {
this.viewTitle = title;
}, "~S");
$_M(c$, "getViewTitle", 
function () {
return (this.viewTitle == null ? this.getTitle () : this.viewTitle);
});
$_M(c$, "getInfo", 
function (selectedOnly, key) {
var info =  new java.util.Hashtable ();
var sets = null;
if (selectedOnly) return this.currentGraphSet.getInfo (key, this.getCurrentSpectrumIndex ());
var entries = this.options.entrySet ();
for (var entry, $entry = entries.iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) JSV.common.Parameters.putInfo (key, info, entry.getKey ().name (), entry.getValue ());

JSV.common.Parameters.putInfo (key, info, "type", this.getSpectrumAt (0).getDataType ());
JSV.common.Parameters.putInfo (key, info, "title", this.title);
JSV.common.Parameters.putInfo (key, info, "nSets", Integer.$valueOf (this.graphSets.size ()));
sets =  new JU.List ();
for (var i = this.graphSets.size (); --i >= 0; ) sets.addLast (this.graphSets.get (i).getInfo (key, -1));

info.put ("sets", sets);
return info;
}, "~B,~S");
$_M(c$, "setBooleans", 
function (parameters, st) {
if (st == null) {
var booleans = parameters.getBooleans ();
for (var entry, $entry = booleans.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) this.setBooleans (parameters, entry.getKey ());

return;
}this.setBoolean (st, parameters.getBoolean (st));
}, "JSV.common.Parameters,JSV.common.ScriptToken");
$_M(c$, "setBoolean", 
function (st, isTrue) {
this.taintedAll = true;
if (st === JSV.common.ScriptToken.REVERSEPLOT) {
this.currentGraphSet.setReversePlot (isTrue);
return;
}this.options.put (st, Boolean.$valueOf (isTrue));
switch (st) {
case JSV.common.ScriptToken.DISPLAY1D:
case JSV.common.ScriptToken.DISPLAY2D:
this.doReset = true;
break;
}
}, "JSV.common.ScriptToken,~B");
$_M(c$, "getBoolean", 
function (st) {
if (st === JSV.common.ScriptToken.REVERSEPLOT) return this.currentGraphSet.reversePlot;
if (this.options == null) return false;
var b = this.options.get (st);
return (b != null && (Clazz_instanceOf (b, Boolean)) && (b) === Boolean.TRUE);
}, "JSV.common.ScriptToken");
$_M(c$, "setFontName", 
function (st, fontName) {
switch (st) {
case JSV.common.ScriptToken.DISPLAYFONTNAME:
this.displayFontName = fontName;
break;
case JSV.common.ScriptToken.TITLEFONTNAME:
this.titleFontName = fontName;
break;
}
if (fontName != null) this.options.put (st, fontName);
}, "JSV.common.ScriptToken,~S");
$_M(c$, "getDisplay1D", 
function () {
return this.display1D;
});
$_M(c$, "initOne", 
function (spectrum) {
this.spectra =  new JU.List ();
this.spectra.addLast (spectrum);
this.initMany (this.spectra, 0, 0);
}, "JSV.common.JDXSpectrum");
$_M(c$, "initMany", 
function (spectra, startIndex, endIndex) {
this.startIndex = startIndex;
this.endIndex = endIndex;
this.nSpectra = spectra.size ();
this.spectra = spectra;
this.commonFilePath = spectra.get (0).getFilePath ();
for (var i = 0; i < this.nSpectra; i++) if (!this.commonFilePath.equalsIgnoreCase (spectra.get (i).getFilePath ())) {
this.commonFilePath = null;
break;
}
this.setGraphSets (JSV.common.PanelData.LinkMode.NONE);
}, "JU.List,~N,~N");
$_M(c$, "setGraphSets", 
function (linkMode) {
this.graphSets = JSV.common.GraphSet.createGraphSetsAndSetLinkMode (this, this.jsvp, this.spectra, this.startIndex, this.endIndex, linkMode);
this.currentGraphSet = this.graphSets.get (0);
this.title = this.getSpectrum ().getTitleLabel ();
}, "JSV.common.PanelData.LinkMode");
$_M(c$, "findMatchingPeakInfo", 
function (pi) {
var pi2 = null;
for (var i = 0; i < this.graphSets.size (); i++) if ((pi2 = this.graphSets.get (i).findMatchingPeakInfo (pi)) != null) break;

return pi2;
}, "JSV.common.PeakInfo");
$_M(c$, "integrateAll", 
function (parameters) {
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).integrate (-1, parameters);

}, "JSV.common.ColorParameters");
$_M(c$, "getNumberOfGraphSets", 
function () {
return this.graphSets.size ();
});
$_M(c$, "getTitle", 
function () {
return this.title;
});
$_M(c$, "refresh", 
function () {
this.doReset = true;
});
$_M(c$, "addAnnotation", 
function (tokens) {
var title = this.currentGraphSet.addAnnotation (tokens, this.getTitle ());
if (title != null) this.title = title;
}, "JU.List");
$_M(c$, "addPeakHighlight", 
function (peakInfo) {
for (var i = 0; i < this.graphSets.size (); i++) this.graphSets.get (i).addPeakHighlight (peakInfo);

}, "JSV.common.PeakInfo");
$_M(c$, "selectPeakByFileIndex", 
function (filePath, index) {
var pi = this.currentGraphSet.selectPeakByFileIndex (filePath, index);
if (pi == null) for (var i = this.graphSets.size (); --i >= 0; ) if (this.graphSets.get (i) !== this.currentGraphSet && (pi = this.graphSets.get (i).selectPeakByFileIndex (filePath, index)) != null) break;

return pi;
}, "~S,~S");
$_M(c$, "setPlotColors", 
function (colors) {
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).setPlotColors (colors);

}, "~A");
$_M(c$, "selectSpectrum", 
function (filePath, type, model, andCurrent) {
if (andCurrent) this.currentGraphSet.selectSpectrum (filePath, type, model);
for (var i = 0; i < this.graphSets.size (); i += 1) if (this.graphSets.get (i) !== this.currentGraphSet) this.graphSets.get (i).selectSpectrum (filePath, type, model);

}, "~S,~S,~S,~B");
$_M(c$, "hasFileLoaded", 
function (filePath) {
for (var i = this.graphSets.size (); --i >= 0; ) if (this.graphSets.get (i).hasFileLoaded (filePath)) return true;

return false;
}, "~S");
$_M(c$, "clearAllView", 
function () {
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).clearViews ();

});
$_M(c$, "drawGraph", 
function (gMain, gTop, width, height, addFilePath) {
var withCoords;
this.gMain = gMain;
this.display1D = !this.isLinked && this.getBoolean (JSV.common.ScriptToken.DISPLAY1D);
var top = 40;
var bottom = 50;
var isResized = (this.isPrinting || this.doReset || this.thisWidth != width || this.thisHeight != height);
if (isResized) this.taintedAll = true;
if (this.taintedAll) this.g2d.fillBackground (gMain, this.bgcolor);
if (gTop !== gMain) {
this.g2d.fillBackground (gTop, null);
this.g2d.setStrokeBold (gMain, false);
}if (this.isPrinting) {
top *= 3;
bottom *= 3;
this.scalingFactor = 10;
withCoords = false;
} else {
this.scalingFactor = 1;
withCoords = this.getBoolean (JSV.common.ScriptToken.COORDINATESON);
this.titleOn = this.getBoolean (JSV.common.ScriptToken.TITLEON);
this.gridOn = this.getBoolean (JSV.common.ScriptToken.GRIDON);
}this.doReset = false;
this.titleDrawn = false;
this.thisWidth = width;
this.thisHeight = height;
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).drawGraphSet (gMain, gTop, width, height, this.left, this.right, top, bottom, isResized, this.taintedAll);

if (this.titleOn && !this.titleDrawn && this.taintedAll) this.drawTitle (gMain, height * this.scalingFactor, width * this.scalingFactor, this.getDrawTitle (this.isPrinting));
if (withCoords && this.coordStr != null) this.drawCoordinates (gTop, top, this.thisWidth - this.right, top - 20);
if (addFilePath && this.taintedAll) {
var s = (this.commonFilePath != null ? this.commonFilePath : this.graphSets.size () == 1 && this.currentGraphSet.getTitle (true) != null ? this.getSpectrum ().getFilePath () : null);
if (s != null) {
this.printFilePath (gMain, this.left, height, s);
}}if (this.isPrinting) {
this.printVersion (gMain, height);
}this.taintedAll = (this.isPrinting || gMain === gTop);
}, "~O,~O,~N,~N,~B");
$_M(c$, "drawCoordinates", 
function (g, top, x, y) {
this.g2d.setGraphicsColor (g, this.coordinatesColor);
var font = this.setFont (g, this.jsvp.getWidth (), 0, 12, true);
this.g2d.drawString (g, this.coordStr, x - font.stringWidth (this.coordStr), y);
}, "~O,~N,~N,~N");
$_M(c$, "setFont", 
function (g, width, style, size, isLabel) {
return this.g2d.setFont (g, this.getFont (g, width, style, size, isLabel));
}, "~O,~N,~N,~N,~B");
$_M(c$, "printFilePath", 
function (g, x, y, s) {
x *= this.scalingFactor;
y *= this.scalingFactor;
if (s.indexOf ("?") > 0) s = s.substring (s.indexOf ("?") + 1);
s = s.substring (s.lastIndexOf ("/") + 1);
s = s.substring (s.lastIndexOf ("\\") + 1);
this.g2d.setGraphicsColor (g, this.BLACK);
var font = this.setFont (g, 1000, 0, 9, true);
if (x != this.left * this.scalingFactor) x -= font.stringWidth (s);
this.g2d.drawString (g, s, x, y - font.getHeight ());
}, "~O,~N,~N,~S");
$_M(c$, "printVersion", 
function (g, pageHeight) {
this.g2d.setGraphicsColor (g, this.BLACK);
var font = this.setFont (g, 100, 0, 12, true);
var s = this.jsvp.getApiPlatform ().getDateFormat (false) + " JSpecView " + JSV.common.JSVersion.VERSION_SHORT;
var w = font.stringWidth (s);
this.g2d.drawString (g, s, (this.thisWidth - this.right) * this.scalingFactor - w, pageHeight * this.scalingFactor - font.getHeight () * 3);
}, "~O,~N");
$_M(c$, "drawTitle", 
function (g, pageHeight, pageWidth, title) {
title = title.$replace ('\n', ' ');
var font = this.getFont (g, pageWidth, this.isPrinting || this.getBoolean (JSV.common.ScriptToken.TITLEBOLDON) ? 1 : 0, 14, true);
var nPixels = font.stringWidth (title);
if (nPixels > pageWidth) {
var size = Clazz_doubleToInt (14.0 * pageWidth / nPixels);
if (size < 10) size = 10;
font = this.getFont (g, pageWidth, this.isPrinting || this.getBoolean (JSV.common.ScriptToken.TITLEBOLDON) ? 1 : 0, size, true);
}this.g2d.setGraphicsColor (g, this.titleColor);
this.setCurrentFont (this.g2d.setFont (g, font));
this.g2d.drawString (g, title, (this.isPrinting ? this.left * this.scalingFactor : 5), pageHeight - Clazz_doubleToInt (font.getHeight () * (this.isPrinting ? 2 : 0.5)));
}, "~O,~N,~N,~S");
$_M(c$, "setCurrentFont", 
function (font) {
this.currentFont = font;
}, "javajs.awt.Font");
$_M(c$, "getFontHeight", 
function () {
return this.currentFont.getAscent ();
});
$_M(c$, "getStringWidth", 
function (s) {
return this.currentFont.stringWidth (s);
}, "~S");
$_M(c$, "selectFromEntireSet", 
function (iSpec) {
for (var i = 0, pt = 0; i < this.graphSets.size (); i++) {
if (iSpec == -2147483648) {
this.graphSets.get (i).setSelected (-1);
continue;
}var specs = this.graphSets.get (i).spectra;
for (var j = 0; j < specs.size (); j++, pt++) if (iSpec < 0 || iSpec == pt) this.graphSets.get (i).setSelected (j);

}
}, "~N");
$_M(c$, "addToList", 
function (iSpec, list) {
for (var i = 0; i < this.spectra.size (); i++) if (iSpec < 0 || i == iSpec) list.addLast (this.spectra.get (i));

}, "~N,JU.List");
$_M(c$, "scaleSelectedBy", 
function (f) {
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).scaleSelectedBy (f);

}, "~N");
$_M(c$, "setCurrentGraphSet", 
function (gs, yPixel, clickCount) {
var splitPoint = gs.getSplitPoint (yPixel);
var isNewSet = (this.currentGraphSet !== gs);
var isNewSplitPoint = (isNewSet || this.currentSplitPoint != splitPoint);
this.currentGraphSet = gs;
this.currentSplitPoint = splitPoint;
if (isNewSet || gs.nSplit > 1 && isNewSplitPoint) this.setSpectrum (this.currentSplitPoint, true);
if (!isNewSet) {
isNewSet = gs.checkSpectrumClickedEvent (this.mouseX, this.mouseY, clickCount);
if (!isNewSet) return;
}if (isNewSet || gs.nSplit > 1 && isNewSplitPoint) this.setSpectrum (this.currentSplitPoint, true);
var spec = gs.getSpectrum ();
this.notifySubSpectrumChange (spec.getSubIndex (), spec);
}, "JSV.common.GraphSet,~N,~N");
$_M(c$, "splitStack", 
function (doSplit) {
this.currentGraphSet.splitStack (this.graphSets, doSplit);
}, "~B");
$_M(c$, "getNumberOfSpectraInCurrentSet", 
function () {
return this.currentGraphSet.nSpectra;
});
$_M(c$, "getStartingPointIndex", 
function (index) {
return this.currentGraphSet.viewData.getStartingPointIndex (index);
}, "~N");
$_M(c$, "getEndingPointIndex", 
function (index) {
return this.currentGraphSet.viewData.getEndingPointIndex (index);
}, "~N");
$_M(c$, "haveSelectedSpectrum", 
function () {
return this.currentGraphSet.haveSelectedSpectrum ();
});
$_M(c$, "getShowAnnotation", 
function (type) {
return this.currentGraphSet.getShowAnnotation (type, -1);
}, "JSV.common.Annotation.AType");
$_M(c$, "showAnnotation", 
function (type, tfToggle) {
this.currentGraphSet.setShowAnnotation (type, tfToggle);
}, "JSV.common.Annotation.AType,Boolean");
$_M(c$, "setYStackOffsetPercent", 
function (offset) {
this.currentGraphSet.yStackOffsetPercent = offset;
}, "~N");
$_M(c$, "setSpectrum", 
function (iSpec, isSplit) {
this.currentGraphSet.setSpectrum (iSpec, isSplit);
}, "~N,~B");
$_M(c$, "getSpectrum", 
function () {
return this.currentGraphSet.getSpectrum ();
});
$_M(c$, "setSpectrum", 
function (spec) {
this.taintedAll = true;
var spec0 = this.currentGraphSet.getSpectrum ();
this.currentGraphSet.setSpectrumJDX (spec);
for (var i = 0; i < this.spectra.size (); i++) if (this.spectra.get (i) === spec0) this.spectra.set (i, spec);

}, "JSV.common.JDXSpectrum");
$_M(c$, "isShowAllStacked", 
function () {
return this.currentGraphSet.showAllStacked;
});
$_M(c$, "getCurrentSpectrumIndex", 
function () {
return this.currentGraphSet.getCurrentSpectrumIndex ();
});
$_M(c$, "getSpectrumAt", 
function (index) {
return this.currentGraphSet.getSpectrumAt (index);
}, "~N");
$_M(c$, "addHighlight", 
function (gs, x1, x2, spec, r, g, b, a) {
(gs == null ? this.currentGraphSet : gs).addHighlight (x1, x2, spec, this.g2d.getColor4 (r, g, b, a));
}, "JSV.common.GraphSet,~N,~N,JSV.common.JDXSpectrum,~N,~N,~N,~N");
$_M(c$, "removeHighlight", 
function (x1, x2) {
this.currentGraphSet.removeHighlight (x1, x2);
}, "~N,~N");
$_M(c$, "removeAllHighlights", 
function () {
this.currentGraphSet.removeAllHighlights ();
});
$_M(c$, "setZoom", 
function (x1, y1, x2, y2) {
this.currentGraphSet.setZoom (x1, y1, x2, y2);
this.doReset = true;
this.taintedAll = true;
this.notifyListeners ( new JSV.common.ZoomEvent (x1, y1, x2, y2));
}, "~N,~N,~N,~N");
$_M(c$, "resetView", 
function () {
this.currentGraphSet.resetView ();
});
$_M(c$, "previousView", 
function () {
this.currentGraphSet.previousView ();
});
$_M(c$, "nextView", 
function () {
this.currentGraphSet.nextView ();
});
$_M(c$, "getSelectedIntegral", 
function () {
return this.currentGraphSet.getSelectedIntegral ();
});
$_M(c$, "advanceSubSpectrum", 
function (dir) {
this.currentGraphSet.advanceSubSpectrum (dir);
}, "~N");
$_M(c$, "setSelectedIntegral", 
function (val) {
this.currentGraphSet.setSelectedIntegral (val);
}, "~N");
$_M(c$, "scaleYBy", 
function (f) {
this.currentGraphSet.scaleYBy (f);
}, "~N");
$_M(c$, "toPeak", 
function (i) {
this.currentGraphSet.toPeak (i);
}, "~N");
$_M(c$, "getClickedCoordinate", 
function () {
return this.coordClicked;
});
$_M(c$, "getPickedCoordinates", 
function (coord, actualCoord) {
return JSV.common.Coordinate.getPickedCoordinates (this.coordsClicked, this.coordClicked, coord, actualCoord);
}, "JSV.common.Coordinate,JSV.common.Coordinate");
$_M(c$, "shiftSpectrum", 
function (dx, x1) {
return this.currentGraphSet.shiftSpectrum (dx, x1);
}, "~N,~N");
$_M(c$, "findX", 
function (spec, d) {
this.currentGraphSet.setXPointer (spec, d);
}, "JSV.common.JDXSpectrum,~N");
$_M(c$, "setXPointers", 
function (spec, x1, spec2, x2) {
this.currentGraphSet.setXPointer (spec, x1);
this.currentGraphSet.setXPointer2 (spec2, x2);
}, "JSV.common.JDXSpectrum,~N,JSV.common.JDXSpectrum,~N");
$_M(c$, "isCurrentGraphSet", 
function (graphSet) {
return graphSet === this.currentGraphSet;
}, "JSV.common.GraphSet");
$_M(c$, "repaint", 
function () {
this.jsvp.doRepaint (false);
});
$_M(c$, "setToolTipText", 
function (s) {
this.jsvp.setToolTipText (s);
}, "~S");
$_M(c$, "setHighlightColor", 
function (color) {
this.jsvp.getPanelData ().setColor (JSV.common.ScriptToken.HIGHLIGHTCOLOR, color);
}, "javajs.api.GenericColor");
$_M(c$, "getInput", 
function (message, title, sval) {
return this.jsvp.getInput (message, title, sval);
}, "~S,~S,~S");
$_M(c$, "getFont", 
function (g, width, style, size, isLabel) {
size *= this.scalingFactor;
if (isLabel) {
if (width < 400) size = ((width * size) / 400);
} else {
if (width < 250) size = ((width * size) / 250);
}var face = this.jsvp.getFontFaceID (this.isPrinting ? this.printingFontName : this.displayFontName);
return this.currentFont = javajs.awt.Font.createFont3D (face, style, size, size, this.jsvp.getApiPlatform (), g);
}, "~O,~N,~N,~N,~B");
$_M(c$, "notifySubSpectrumChange", 
function (isub, spec) {
this.notifyListeners ( new JSV.common.SubSpecChangeEvent (isub, (spec == null ? null : spec.getTitleLabel ())));
}, "~N,JSV.common.JDXSpectrum");
$_M(c$, "notifyPeakPickedListeners", 
function (p) {
if (p == null) {
p =  new JSV.common.PeakPickEvent (this.jsvp, this.coordClicked, this.getSpectrum ().getAssociatedPeakInfo (this.xPixelClicked, this.coordClicked));
}this.notifyListeners (p);
}, "JSV.common.PeakPickEvent");
$_M(c$, "notifyListeners", 
function (eventObj) {
for (var i = 0; i < this.listeners.size (); i++) if (this.listeners.get (i) != null) this.listeners.get (i).panelEvent (eventObj);

}, "~O");
$_M(c$, "escapeKeyPressed", 
function (isDEL) {
this.currentGraphSet.escapeKeyPressed (isDEL);
}, "~B");
$_M(c$, "hasFocus", 
function () {
return this.jsvp.hasFocus ();
});
$_M(c$, "isMouseUp", 
function () {
return (this.mouseState === JSV.common.PanelData.Mouse.UP);
});
$_M(c$, "doMouseMoved", 
function (xPixel, yPixel) {
this.mouseX = xPixel;
this.mouseY = yPixel;
this.mouseState = JSV.common.PanelData.Mouse.UP;
this.clickCount = 0;
var gs = JSV.common.GraphSet.findGraphSet (this.graphSets, xPixel, yPixel);
if (gs == null) return;
gs.mouseMovedEvent (xPixel, yPixel);
}, "~N,~N");
$_M(c$, "doMousePressed", 
function (xPixel, yPixel) {
this.mouseState = JSV.common.PanelData.Mouse.DOWN;
var gs = JSV.common.GraphSet.findGraphSet (this.graphSets, xPixel, yPixel);
if (gs == null) return;
this.setCurrentGraphSet (gs, yPixel, 0);
this.clickCount = (++this.clickCount % 3);
gs.checkWidgetEvent (xPixel, yPixel, true);
}, "~N,~N");
$_M(c$, "doMouseDragged", 
function (xPixel, yPixel) {
this.isIntegralDrag = new Boolean (this.isIntegralDrag | this.ctrlPressed).valueOf ();
this.mouseState = JSV.common.PanelData.Mouse.DOWN;
if (JSV.common.GraphSet.findGraphSet (this.graphSets, xPixel, yPixel) !== this.currentGraphSet) return;
this.currentGraphSet.checkWidgetEvent (xPixel, yPixel, false);
this.currentGraphSet.mouseMovedEvent (xPixel, yPixel);
}, "~N,~N");
$_M(c$, "doMouseReleased", 
function (xPixel, yPixel, isButton1) {
this.mouseState = JSV.common.PanelData.Mouse.UP;
if (this.thisWidget == null && this.currentGraphSet.pendingMeasurement == null || !isButton1) return;
this.currentGraphSet.mouseReleasedEvent (xPixel, yPixel);
this.thisWidget = null;
this.isIntegralDrag = false;
this.integralShiftMode = 0;
}, "~N,~N,~B");
$_M(c$, "doMouseClicked", 
function (xPixel, yPixel, isControlDown) {
var gs = JSV.common.GraphSet.findGraphSet (this.graphSets, xPixel, yPixel);
if (gs == null) return;
this.setCurrentGraphSet (gs, yPixel, this.clickCount);
gs.mouseClickedEvent (xPixel, yPixel, this.clickCount, isControlDown);
this.repaint ();
}, "~N,~N,~B");
$_M(c$, "hasCurrentMeasurements", 
function (type) {
return this.currentGraphSet.hasCurrentMeasurement (type);
}, "JSV.common.Annotation.AType");
$_M(c$, "getDialog", 
function (type) {
return this.currentGraphSet.getDialog (type, -1);
}, "JSV.common.Annotation.AType");
$_M(c$, "addDialog", 
function (iSpec, type, dialog) {
this.currentGraphSet.addDialog (iSpec, type, dialog);
}, "~N,JSV.common.Annotation.AType,JSV.api.AnnotationData");
$_M(c$, "getPeakListing", 
function (p, tfToggle) {
if (p != null) this.currentGraphSet.getPeakListing (-1, p, true);
this.currentGraphSet.setPeakListing (tfToggle);
}, "JSV.common.Parameters,Boolean");
$_M(c$, "checkIntegral", 
function (parameters, value) {
this.currentGraphSet.checkIntegral (parameters, value);
}, "JSV.common.Parameters,~S");
$_M(c$, "setIntegrationRatios", 
function (value) {
this.currentGraphSet.setIntegrationRatios (value);
}, "~S");
$_M(c$, "getView", 
function () {
return this.currentGraphSet.getCurrentView ();
});
$_M(c$, "closeAllDialogsExcept", 
function (type) {
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).closeDialogsExcept (type);

}, "JSV.common.Annotation.AType");
$_M(c$, "removeDialog", 
function (dialog) {
this.currentGraphSet.removeDialog (dialog);
}, "JSV.dialog.JSVDialog");
$_M(c$, "normalizeIntegral", 
function () {
var integral = this.getSelectedIntegral ();
if (integral == null) return;
var sValue = integral.text;
if (sValue.length == 0) sValue = "" + integral.getValue ();
var newValue = this.getInput ("Enter a new value for this integral", "Normalize Integral", sValue);
try {
this.setSelectedIntegral (Double.parseDouble (newValue));
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
});
$_M(c$, "getDrawTitle", 
function (isPrinting) {
var title = null;
if (isPrinting) title = this.printJobTitle;
 else if (this.nSpectra == 1) {
title = this.getSpectrum ().getPeakTitle ();
} else if (this.viewTitle != null) {
if (this.currentGraphSet.getTitle (false) != null) title = this.getSpectrum ().getPeakTitle ();
if (title == null) title = this.viewTitle;
} else {
title = this.jsvp.getTitle ().trim ();
}if (title.indexOf ("\n") >= 0) title = title.substring (0, title.indexOf ("\n")).trim ();
return title;
}, "~B");
$_M(c$, "getPrintJobTitle", 
function (isPrinting) {
var title = null;
if (this.nSpectra == 1) {
title = this.getSpectrum ().getTitle ();
} else if (this.viewTitle != null) {
if (this.graphSets.size () == 1) title = this.currentGraphSet.getTitle (isPrinting);
if (title == null) title = this.viewTitle;
} else {
title = this.jsvp.getTitle ().trim ();
}if (title.indexOf ("\n") >= 0) title = title.substring (0, title.indexOf ("\n")).trim ();
return title;
}, "~B");
$_M(c$, "linkSpectra", 
function (mode) {
if (mode === JSV.common.PanelData.LinkMode.ALL) mode = (this.nSpectra == 2 ? JSV.common.PanelData.LinkMode.AB : this.nSpectra == 3 ? JSV.common.PanelData.LinkMode.ABC : JSV.common.PanelData.LinkMode.NONE);
if (mode !== JSV.common.PanelData.LinkMode.NONE && mode.toString ().length != this.nSpectra) return;
this.setGraphSets (mode);
}, "JSV.common.PanelData.LinkMode");
$_M(c$, "doZoomLinked", 
function (graphSet, initX, finalX, addZoom, checkRange, is1d) {
if (this.linking) return;
this.linking = true;
var spec = graphSet.getSpectrumAt (0);
for (var i = this.graphSets.size (); --i >= 0; ) {
var gs = this.graphSets.get (i);
if (gs !== graphSet && JSV.common.JDXSpectrum.areXScalesCompatible (spec, this.graphSets.get (i).getSpectrumAt (0), false, true)) gs.doZoom (initX, 0, finalX, 0, is1d, false, checkRange, false, addZoom);
}
this.linking = false;
}, "JSV.common.GraphSet,~N,~N,~B,~B,~B");
$_M(c$, "clearLinkViews", 
function (graphSet) {
if (this.linking) return;
this.linking = true;
var spec = graphSet.getSpectrum ();
for (var i = this.graphSets.size (); --i >= 0; ) {
var gs = this.graphSets.get (i);
if (gs !== graphSet && JSV.common.JDXSpectrum.areXScalesCompatible (spec, this.graphSets.get (i).getSpectrum (), false, true)) gs.clearViews ();
}
this.linking = false;
}, "JSV.common.GraphSet");
$_M(c$, "setlinkedXMove", 
function (graphSet, x, isX2) {
if (this.linking) return;
this.linking = true;
var spec = graphSet.getSpectrum ();
for (var i = this.graphSets.size (); --i >= 0; ) {
var gs = this.graphSets.get (i);
if (gs !== graphSet && JSV.common.JDXSpectrum.areXScalesCompatible (spec, this.graphSets.get (i).getSpectrum (), false, true)) {
if (gs.imageView == null) if (isX2) {
gs.setXPixelMovedTo (1.7976931348623157E308, x, 0, 0);
} else {
gs.setXPixelMovedTo (x, 1.7976931348623157E308, 0, 0);
}}}
this.linking = false;
}, "JSV.common.GraphSet,~N,~B");
$_M(c$, "set2DCrossHairsLinked", 
function (graphSet, x, y, isLocked) {
if (Math.abs (x - y) < 0.1) x = y = 1.7976931348623157E308;
for (var i = this.graphSets.size (); --i >= 0; ) {
var gs = this.graphSets.get (i);
if (gs !== graphSet) gs.set2DXY (x, y, isLocked);
}
}, "JSV.common.GraphSet,~N,~N,~B");
$_M(c$, "dialogsToFront", 
function () {
this.currentGraphSet.dialogsToFront ();
});
$_M(c$, "setColor", 
function (st, color) {
if (color != null) this.options.put (st, JU.CU.toRGBHexString (color));
switch (st) {
case JSV.common.ScriptToken.BACKGROUNDCOLOR:
this.jsvp.setBackgroundColor (this.bgcolor = color);
this.taintedAll = true;
break;
case JSV.common.ScriptToken.COORDINATESCOLOR:
this.coordinatesColor = color;
break;
case JSV.common.ScriptToken.GRIDCOLOR:
this.gridColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.HIGHLIGHTCOLOR:
this.highlightColor = color;
if (this.highlightColor.getOpacity255 () == 255) this.highlightColor.setOpacity255 (150);
break;
case JSV.common.ScriptToken.INTEGRALPLOTCOLOR:
this.integralPlotColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.PEAKTABCOLOR:
this.peakTabColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.PLOTCOLOR:
for (var i = this.graphSets.size (); --i >= 0; ) this.graphSets.get (i).setPlotColor0 (color);

this.taintedAll = true;
break;
case JSV.common.ScriptToken.PLOTAREACOLOR:
this.plotAreaColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.SCALECOLOR:
this.scaleColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.TITLECOLOR:
this.titleColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.UNITSCOLOR:
this.unitsColor = color;
this.taintedAll = true;
break;
case JSV.common.ScriptToken.ZOOMBOXCOLOR:
this.zoomBoxColor = color;
break;
case JSV.common.ScriptToken.ZOOMBOXCOLOR2:
this.zoomBoxColor2 = color;
break;
default:
J.util.Logger.warn ("AwtPanel --- unrecognized color: " + st);
break;
}
}, "JSV.common.ScriptToken,javajs.api.GenericColor");
$_M(c$, "getColor", 
function (whatColor) {
switch (whatColor) {
default:
J.util.Logger.error ("awtgraphset missing color " + whatColor);
return this.BLACK;
case JSV.common.ScriptToken.ZOOMBOXCOLOR2:
return this.zoomBoxColor2;
case JSV.common.ScriptToken.ZOOMBOXCOLOR:
return this.zoomBoxColor;
case JSV.common.ScriptToken.HIGHLIGHTCOLOR:
return this.highlightColor;
case JSV.common.ScriptToken.INTEGRALPLOTCOLOR:
return this.integralPlotColor;
case JSV.common.ScriptToken.GRIDCOLOR:
return this.gridColor;
case JSV.common.ScriptToken.PEAKTABCOLOR:
return this.peakTabColor;
case JSV.common.ScriptToken.PLOTAREACOLOR:
return this.plotAreaColor;
case JSV.common.ScriptToken.SCALECOLOR:
return this.scaleColor;
case JSV.common.ScriptToken.TITLECOLOR:
return this.titleColor;
case JSV.common.ScriptToken.UNITSCOLOR:
return this.unitsColor;
}
}, "JSV.common.ScriptToken");
$_M(c$, "getOverlayLegendData", 
function () {
var numSpectra = this.currentGraphSet.nSpectra;
var data =  new Array (numSpectra);
var f1 = this.getSpectrumAt (0).getFilePath ();
var f2 = this.getSpectrumAt (numSpectra - 1).getFilePath ();
var useFileName = !f1.equals (f2);
for (var index = 0; index < numSpectra; index++) {
var cols =  new Array (3);
var spectrum = this.getSpectrumAt (index);
this.title = spectrum.getTitle ();
if (useFileName) this.title = JSV.common.JSVFileManager.getName (spectrum.getFilePath ()) + " - " + this.title;
var plotColor = this.getCurrentPlotColor (index);
cols[0] =  new Integer (index + 1);
cols[1] = plotColor;
cols[2] = " " + this.title;
data[index] = cols;
}
return data;
});
$_M(c$, "setColorOrFont", 
function (params, st) {
if (st == null) {
var colors = params.elementColors;
for (var entry, $entry = colors.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) this.setColorOrFont (params, entry.getKey ());

this.setColorOrFont (params, JSV.common.ScriptToken.DISPLAYFONTNAME);
this.setColorOrFont (params, JSV.common.ScriptToken.TITLEFONTNAME);
return;
}switch (st) {
case JSV.common.ScriptToken.DISPLAYFONTNAME:
this.setFontName (st, params.displayFontName);
return;
case JSV.common.ScriptToken.TITLEFONTNAME:
this.setFontName (st, params.titleFontName);
return;
}
this.setColor (st, params.getElementColor (st));
}, "JSV.common.ColorParameters,JSV.common.ScriptToken");
$_M(c$, "getCurrentPlotColor", 
function (i) {
return this.currentGraphSet.getPlotColor (i);
}, "~N");
$_M(c$, "setPrint", 
function (pl, fontName) {
if (pl == null) {
this.options.putAll (this.optionsSaved);
this.optionsSaved = null;
return;
}this.printJobTitle = pl.title;
this.printingFontName = fontName;
this.printGraphPosition = pl.position;
this.optionsSaved =  new java.util.Hashtable ();
this.optionsSaved.putAll (this.options);
this.gridOn = pl.showGrid;
this.titleOn = pl.showTitle;
this.setBoolean (JSV.common.ScriptToken.XSCALEON, pl.showXScale);
this.setBoolean (JSV.common.ScriptToken.XUNITSON, pl.showXScale);
this.setBoolean (JSV.common.ScriptToken.YSCALEON, pl.showYScale);
this.setBoolean (JSV.common.ScriptToken.YUNITSON, pl.showYScale);
}, "JSV.common.PrintLayout,~S");
$_M(c$, "showDialog", 
function (type) {
var ad = this.getDialog (type);
this.closeAllDialogsExcept (type);
if (ad != null && Clazz_instanceOf (ad, JSV.dialog.JSVDialog)) return (ad).reEnable ();
var iSpec = this.getCurrentSpectrumIndex ();
if (iSpec < 0) {
this.jsvp.showMessage ("To enable " + type + " first select a spectrum by clicking on it.", "" + type);
return null;
}var spec = this.getSpectrum ();
var dialog = this.viewer.getDialog (type, spec);
if (ad == null && type === JSV.common.Annotation.AType.Measurements) ad =  new JSV.common.MeasurementData (JSV.common.Annotation.AType.Measurements, spec);
if (ad != null) dialog.setData (ad);
this.addDialog (iSpec, type, dialog);
dialog.reEnable ();
return dialog;
}, "JSV.common.Annotation.AType");
$_M(c$, "printPdf", 
function (pdfCreator, pl) {
var isPortrait = !pl.layout.equals ("landscape");
this.print (pdfCreator, (isPortrait ? pl.imageableHeight : pl.imageableWidth), (isPortrait ? pl.imageableWidth : pl.imageableHeight), pl.imageableX, pl.imageableY, pl.paperHeight, pl.paperWidth, isPortrait, 0);
}, "JSV.api.JSVGraphics,JSV.common.PrintLayout");
$_M(c$, "print", 
function (g, height, width, x, y, paperHeight, paperWidth, isPortrait, pi) {
this.g2d = this.g2d0;
if (pi == 0) {
this.isPrinting = true;
var addFilePath = false;
if (Clazz_instanceOf (g, JSV.api.JSVGraphics)) {
this.g2d = g;
g = this.gMain;
}if (this.printGraphPosition.equals ("default")) {
if (isPortrait) {
height = 450;
width = 280;
} else {
height = 280;
width = 450;
}} else if (this.printGraphPosition.equals ("fit to page")) {
addFilePath = true;
} else {
if (isPortrait) {
height = 450;
width = 280;
x = Clazz_doubleToInt (Clazz_doubleToInt (paperWidth - width) / 2);
y = Clazz_doubleToInt (Clazz_doubleToInt (paperHeight - height) / 2);
} else {
height = 280;
width = 450;
y = Clazz_doubleToInt (Clazz_doubleToInt (paperWidth - 280) / 2);
x = Clazz_doubleToInt (Clazz_doubleToInt (paperHeight - 450) / 2);
}}this.g2d.translateScale (g, x, y, 0.1);
this.taintedAll = true;
this.drawGraph (g, g, Clazz_doubleToInt (width), Clazz_doubleToInt (height), addFilePath);
this.isPrinting = false;
return 0;
}this.isPrinting = false;
return 1;
}, "~O,~N,~N,~N,~N,~N,~N,~B,~N");
$_V(c$, "keyPressed", 
function (code, modifiers) {
if (this.isPrinting) return false;
this.checkKeyControl (code, true);
switch (code) {
case 27:
case 127:
case 8:
this.escapeKeyPressed (code != 27);
this.isIntegralDrag = false;
this.repaint ();
return true;
}
var scaleFactor = 0;
var doConsume = false;
if (modifiers == 0) {
switch (code) {
case 37:
case 39:
this.doMouseMoved ((code == 39 ? ++this.mouseX : --this.mouseX), this.mouseY);
this.repaint ();
doConsume = true;
break;
case 33:
case 34:
scaleFactor = (code == 33 ? JSV.common.GraphSet.RT2 : 1 / JSV.common.GraphSet.RT2);
doConsume = true;
break;
case 40:
case 38:
var dir = (code == 40 ? -1 : 1);
if (this.getSpectrumAt (0).getSubSpectra () == null) {
this.notifySubSpectrumChange (dir, null);
} else {
this.advanceSubSpectrum (dir);
this.repaint ();
}doConsume = true;
break;
}
} else if (this.checkMod (code, 2)) {
switch (code) {
case 40:
case 38:
case 45:
case 61:
scaleFactor = (code == 61 || code == 38 ? JSV.common.GraphSet.RT2 : 1 / JSV.common.GraphSet.RT2);
doConsume = true;
break;
case 37:
case 39:
this.toPeak (code == 39 ? 1 : -1);
doConsume = true;
break;
}
}if (scaleFactor != 0) {
this.scaleYBy (scaleFactor);
this.repaint ();
}return doConsume;
}, "~N,~N");
$_V(c$, "keyReleased", 
function (keyCode) {
if (this.isPrinting) return;
this.checkKeyControl (keyCode, false);
}, "~N");
$_V(c$, "keyTyped", 
function (ch, mods) {
if (this.isPrinting) return false;
switch (ch) {
case 'n':
if (mods != 0) break;
this.normalizeIntegral ();
return true;
case 26:
if (mods != 2) break;
this.previousView ();
this.repaint ();
return true;
case 25:
if (mods != 2) break;
this.nextView ();
this.repaint ();
return true;
}
return false;
}, "~N,~N");
$_V(c$, "mouseAction", 
function (mode, time, x, y, countIgnored, buttonMods) {
if (this.isPrinting) return;
switch (mode) {
case 4:
if (!this.checkMod (buttonMods, 16)) return;
this.doMousePressed (x, y);
break;
case 5:
this.doMouseReleased (x, y, this.checkMod (buttonMods, 16));
this.repaint ();
break;
case 1:
this.doMouseDragged (x, y);
this.repaint ();
break;
case 0:
this.jsvp.getFocusNow (false);
if ((buttonMods & 28) != 0) {
this.doMouseDragged (x, y);
this.repaint ();
return;
}this.doMouseMoved (x, y);
if (this.coordStr != null) this.repaint ();
break;
case 2:
if (this.checkMod (buttonMods, 4)) {
this.viewer.showMenu (x, y);
return;
}this.ctrlPressed = false;
this.doMouseClicked (x, y, this.updateControlPressed (buttonMods));
break;
}
}, "~N,~N,~N,~N,~N,~N");
$_M(c$, "checkMod", 
function (buttonMods, mask) {
return ((buttonMods & mask) == mask);
}, "~N,~N");
$_M(c$, "checkKeyControl", 
function (keyCode, isPressed) {
switch (keyCode) {
case 17:
case 157:
this.ctrlPressed = isPressed;
break;
case 16:
this.shiftPressed = isPressed;
break;
}
}, "~N,~B");
$_M(c$, "updateControlPressed", 
function (mods) {
return (this.ctrlPressed = new Boolean (this.ctrlPressed | (this.checkMod (mods, 2) || this.checkMod (mods, 20))).valueOf ());
}, "~N");
$_V(c$, "mouseEnterExit", 
function (time, x, y, isExit) {
if (isExit) {
this.thisWidget = null;
this.isIntegralDrag = false;
this.integralShiftMode = 0;
} else {
this.jsvp.getFocusNow (false);
}}, "~N,~N,~N,~B");
Clazz_pu$h ();
c$ = Clazz_declareType (JSV.common.PanelData, "LinkMode", Enum);
c$.getMode = $_M(c$, "getMode", 
function (a) {
if (a.equals ("*")) return JSV.common.PanelData.LinkMode.ALL;
for (var mode, $mode = 0, $$mode = JSV.common.PanelData.LinkMode.values (); $mode < $$mode.length && ((mode = $$mode[$mode]) || true); $mode++) if (mode.name ().equalsIgnoreCase (a)) return mode;

return JSV.common.PanelData.LinkMode.NONE;
}, "~S");
Clazz_defineEnumConstant (c$, "ALL", 0, []);
Clazz_defineEnumConstant (c$, "NONE", 1, []);
Clazz_defineEnumConstant (c$, "AB", 2, []);
Clazz_defineEnumConstant (c$, "ABC", 3, []);
c$ = Clazz_p0p ();
Clazz_pu$h ();
c$ = Clazz_declareType (JSV.common.PanelData, "Mouse", Enum);
Clazz_defineEnumConstant (c$, "UP", 0, []);
Clazz_defineEnumConstant (c$, "DOWN", 1, []);
c$ = Clazz_p0p ();
Clazz_defineStatics (c$,
"defaultPrintHeight", 450,
"defaultPrintWidth", 280,
"topMargin", 40,
"bottomMargin", 50,
"leftMargin", 60,
"rightMargin", 50);
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum"], "JSV.common.ScriptToken", ["java.util.Hashtable", "JU.List", "$.SB", "JSV.common.ScriptTokenizer"], function () {
c$ = Clazz_decorateAsClass (function () {
this.tip = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "ScriptToken", Enum);
$_M(c$, "getTip", 
function () {
return "  " + (this.tip === "T" ? "TRUE/FALSE/TOGGLE" : this.tip === "TF" ? "TRUE or FALSE" : this.tip === "C" ? "<color>" : this.tip);
});
Clazz_makeConstructor (c$, 
function () {
});
Clazz_makeConstructor (c$, 
function (tip) {
this.tip = tip;
}, "~S");
c$.getScriptToken = $_M(c$, "getScriptToken", 
function (name) {
if (JSV.common.ScriptToken.htParams == null) {
JSV.common.ScriptToken.htParams =  new java.util.Hashtable ();
for (var item, $item = 0, $$item = JSV.common.ScriptToken.values (); $item < $$item.length && ((item = $$item[$item]) || true); $item++) JSV.common.ScriptToken.htParams.put (item.name (), item);

}var st = JSV.common.ScriptToken.htParams.get (name.toUpperCase ());
return (st == null ? JSV.common.ScriptToken.UNKNOWN : st);
}, "~S");
c$.getScriptTokenList = $_M(c$, "getScriptTokenList", 
function (name, isExact) {
name = name.toUpperCase ();
var list =  new JU.List ();
var st = JSV.common.ScriptToken.getScriptToken (name);
if (isExact) {
if (st != null) list.addLast (st);
} else {
for (var entry, $entry = JSV.common.ScriptToken.htParams.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) if (entry.getKey ().startsWith (name) && entry.getValue ().tip != null) list.addLast (entry.getValue ());

}return list;
}, "~S,~B");
c$.getValue = $_M(c$, "getValue", 
function (st, params, cmd) {
if (!params.hasMoreTokens ()) return "";
switch (st) {
default:
return JSV.common.ScriptTokenizer.nextStringToken (params, true);
case JSV.common.ScriptToken.CLOSE:
case JSV.common.ScriptToken.GETPROPERTY:
case JSV.common.ScriptToken.INTEGRATION:
case JSV.common.ScriptToken.INTEGRATE:
case JSV.common.ScriptToken.JMOL:
case JSV.common.ScriptToken.LABEL:
case JSV.common.ScriptToken.LOAD:
case JSV.common.ScriptToken.PEAK:
case JSV.common.ScriptToken.PLOTCOLORS:
case JSV.common.ScriptToken.YSCALE:
case JSV.common.ScriptToken.WRITE:
return JSV.common.ScriptToken.removeCommandName (cmd);
case JSV.common.ScriptToken.SELECT:
case JSV.common.ScriptToken.OVERLAY:
case JSV.common.ScriptToken.VIEW:
case JSV.common.ScriptToken.ZOOM:
return JSV.common.ScriptToken.removeCommandName (cmd).$replace (',', ' ').trim ();
}
}, "JSV.common.ScriptToken,JSV.common.ScriptTokenizer,~S");
c$.removeCommandName = $_M(c$, "removeCommandName", 
function (cmd) {
var pt = cmd.indexOf (" ");
if (pt < 0) return "";
return cmd.substring (pt).trim ();
}, "~S");
c$.getKey = $_M(c$, "getKey", 
function (eachParam) {
var key = eachParam.nextToken ();
if (key.startsWith ("#") || key.startsWith ("//")) return null;
if (key.equalsIgnoreCase ("SET")) key = eachParam.nextToken ();
return key.toUpperCase ();
}, "JSV.common.ScriptTokenizer");
c$.getTokens = $_M(c$, "getTokens", 
function (value) {
var tokens =  new JU.List ();
var st =  new JSV.common.ScriptTokenizer (value, false);
while (st.hasMoreTokens ()) {
var s = JSV.common.ScriptTokenizer.nextStringToken (st, false);
if (s.startsWith ("//") || s.startsWith ("#")) break;
tokens.addLast (s);
}
return tokens;
}, "~S");
c$.getNameList = $_M(c$, "getNameList", 
function (list) {
if (list.size () == 0) return "";
var sb =  new JU.SB ();
for (var i = 0; i < list.size (); i++) sb.append (",").append (list.get (i).toString ());

return sb.toString ().substring (1);
}, "JU.List");
c$.htParams = null;
Clazz_defineEnumConstant (c$, "UNKNOWN", 0, []);
Clazz_defineEnumConstant (c$, "APPLETID", 1, []);
Clazz_defineEnumConstant (c$, "APPLETREADYCALLBACKFUNCTIONNAME", 2, []);
Clazz_defineEnumConstant (c$, "AUTOINTEGRATE", 3, ["TF"]);
Clazz_defineEnumConstant (c$, "BACKGROUNDCOLOR", 4, ["C"]);
Clazz_defineEnumConstant (c$, "CLOSE", 5, ["spectrumId or fileName or ALL"]);
Clazz_defineEnumConstant (c$, "COMPOUNDMENUON", 6, ["TF"]);
Clazz_defineEnumConstant (c$, "COORDCALLBACKFUNCTIONNAME", 7, []);
Clazz_defineEnumConstant (c$, "COORDINATESCOLOR", 8, ["C"]);
Clazz_defineEnumConstant (c$, "COORDINATESON", 9, ["T"]);
Clazz_defineEnumConstant (c$, "DEBUG", 10, ["TF"]);
Clazz_defineEnumConstant (c$, "DISPLAYFONTNAME", 11, ["fontName"]);
Clazz_defineEnumConstant (c$, "DISPLAY1D", 12, ["T"]);
Clazz_defineEnumConstant (c$, "DISPLAY2D", 13, ["T"]);
Clazz_defineEnumConstant (c$, "ENABLEZOOM", 14, ["T"]);
Clazz_defineEnumConstant (c$, "ENDINDEX", 15, []);
Clazz_defineEnumConstant (c$, "FINDX", 16, ["x-value"]);
Clazz_defineEnumConstant (c$, "GETPROPERTY", 17, ["[ALL] [propertyName]"]);
Clazz_defineEnumConstant (c$, "GETSOLUTIONCOLOR", 18, []);
Clazz_defineEnumConstant (c$, "GRIDCOLOR", 19, ["C"]);
Clazz_defineEnumConstant (c$, "GRIDON", 20, ["T"]);
Clazz_defineEnumConstant (c$, "HIDDEN", 21, ["TF"]);
Clazz_defineEnumConstant (c$, "HIGHLIGHTCOLOR", 22, ["C"]);
Clazz_defineEnumConstant (c$, "INTEGRALOFFSET", 23, ["percent"]);
Clazz_defineEnumConstant (c$, "INTEGRALRANGE", 24, ["percent"]);
Clazz_defineEnumConstant (c$, "INTEGRATE", 25, []);
Clazz_defineEnumConstant (c$, "INTEGRATION", 26, ["ON/OFF/AUTO/TOGGLE/MIN value/MARK ppm1-ppm2:norm,ppm3-ppm4,... (start with 0-0 to clear)"]);
Clazz_defineEnumConstant (c$, "INTEGRALPLOTCOLOR", 27, []);
Clazz_defineEnumConstant (c$, "INTEGRATIONRATIOS", 28, []);
Clazz_defineEnumConstant (c$, "INTERFACE", 29, []);
Clazz_defineEnumConstant (c$, "IRMODE", 30, ["A or T or TOGGLE"]);
Clazz_defineEnumConstant (c$, "JMOL", 31, ["...Jmol command..."]);
Clazz_defineEnumConstant (c$, "JSV", 32, []);
Clazz_defineEnumConstant (c$, "LABEL", 33, ["x y [color and/or \"text\"]"]);
Clazz_defineEnumConstant (c$, "LINK", 34, ["AB or ABC or NONE or ALL"]);
Clazz_defineEnumConstant (c$, "LOAD", 35, ["[APPEND] \"fileName\" [first] [last]; use \"\" to reload current file"]);
Clazz_defineEnumConstant (c$, "LOADFILECALLBACKFUNCTIONNAME", 36, []);
Clazz_defineEnumConstant (c$, "LOADIMAGINARY", 37, ["T/F - default is to NOT load imaginary spectra"]);
Clazz_defineEnumConstant (c$, "MENUON", 38, []);
Clazz_defineEnumConstant (c$, "OBSCURE", 39, []);
Clazz_defineEnumConstant (c$, "OVERLAY", 40, []);
Clazz_defineEnumConstant (c$, "OVERLAYSTACKED", 41, ["TF"]);
Clazz_defineEnumConstant (c$, "PEAK", 42, ["<type(IR,CNMR,HNMR,MS, etc)> id=xxx or \"match\" [ALL], for example: PEAK HNMR id=3"]);
Clazz_defineEnumConstant (c$, "PEAKCALLBACKFUNCTIONNAME", 43, []);
Clazz_defineEnumConstant (c$, "PEAKLIST", 44, [" Example: PEAKLIST threshold=20 [%, or include=10] skip=0 interpolate=parabolic [or NONE]"]);
Clazz_defineEnumConstant (c$, "PEAKTABCOLOR", 45, ["C"]);
Clazz_defineEnumConstant (c$, "PLOTAREACOLOR", 46, ["C"]);
Clazz_defineEnumConstant (c$, "PLOTCOLOR", 47, ["C"]);
Clazz_defineEnumConstant (c$, "PLOTCOLORS", 48, ["color,color,color,..."]);
Clazz_defineEnumConstant (c$, "PRINT", 49, []);
Clazz_defineEnumConstant (c$, "REVERSEPLOT", 50, ["T"]);
Clazz_defineEnumConstant (c$, "SCALEBY", 51, ["factor"]);
Clazz_defineEnumConstant (c$, "SCALECOLOR", 52, ["C"]);
Clazz_defineEnumConstant (c$, "SCRIPT", 53, ["filename.jsv"]);
Clazz_defineEnumConstant (c$, "SELECT", 54, ["spectrumID, spectrumID,..."]);
Clazz_defineEnumConstant (c$, "SETPEAK", 55, ["x (ppm) or NONE does peak search, unlike SETX -- NMR only"]);
Clazz_defineEnumConstant (c$, "SETX", 56, ["x (ppm) does no peak search, unlike SETPEAK -- NMR only"]);
Clazz_defineEnumConstant (c$, "SHIFTX", 57, ["dx (ppm) or NONE -- NMR only"]);
Clazz_defineEnumConstant (c$, "SHOWERRORS", 58, []);
Clazz_defineEnumConstant (c$, "SHOWINTEGRATION", 59, ["T"]);
Clazz_defineEnumConstant (c$, "SHOWKEY", 60, ["T"]);
Clazz_defineEnumConstant (c$, "SHOWMEASUREMENTS", 61, ["T"]);
Clazz_defineEnumConstant (c$, "SHOWMENU", 62, []);
Clazz_defineEnumConstant (c$, "SHOWPEAKLIST", 63, ["T"]);
Clazz_defineEnumConstant (c$, "SHOWPROPERTIES", 64, []);
Clazz_defineEnumConstant (c$, "SHOWSOURCE", 65, []);
Clazz_defineEnumConstant (c$, "SPECTRUM", 66, ["spectrumID"]);
Clazz_defineEnumConstant (c$, "SPECTRUMNUMBER", 67, []);
Clazz_defineEnumConstant (c$, "STACKOFFSETY", 68, ["percent"]);
Clazz_defineEnumConstant (c$, "STARTINDEX", 69, []);
Clazz_defineEnumConstant (c$, "SYNCCALLBACKFUNCTIONNAME", 70, []);
Clazz_defineEnumConstant (c$, "SYNCID", 71, []);
Clazz_defineEnumConstant (c$, "TEST", 72, []);
Clazz_defineEnumConstant (c$, "TITLEON", 73, ["T"]);
Clazz_defineEnumConstant (c$, "TITLEBOLDON", 74, ["T"]);
Clazz_defineEnumConstant (c$, "TITLECOLOR", 75, ["C"]);
Clazz_defineEnumConstant (c$, "TITLEFONTNAME", 76, ["fontName"]);
Clazz_defineEnumConstant (c$, "UNITSCOLOR", 77, ["C"]);
Clazz_defineEnumConstant (c$, "VERSION", 78, []);
Clazz_defineEnumConstant (c$, "VIEW", 79, ["spectrumID, spectrumID, ... Example: VIEW 3.1, 3.2  or  VIEW \"acetophenone\""]);
Clazz_defineEnumConstant (c$, "XSCALEON", 80, ["T"]);
Clazz_defineEnumConstant (c$, "XUNITSON", 81, ["T"]);
Clazz_defineEnumConstant (c$, "YSCALE", 82, ["[ALL] lowValue highValue"]);
Clazz_defineEnumConstant (c$, "YSCALEON", 83, ["T"]);
Clazz_defineEnumConstant (c$, "YUNITSON", 84, ["T"]);
Clazz_defineEnumConstant (c$, "WINDOW", 85, []);
Clazz_defineEnumConstant (c$, "WRITE", 86, ["[XY,DIF,DIFDUP,PAC,FIX,SQZ,AML,CML,JPG,PDF,PNG,SVG] \"filename\""]);
Clazz_defineEnumConstant (c$, "ZOOM", 87, ["OUT or x1,x2 or x1,y1 x2,y2"]);
Clazz_defineEnumConstant (c$, "ZOOMBOXCOLOR", 88, []);
Clazz_defineEnumConstant (c$, "ZOOMBOXCOLOR2", 89, []);
});
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.PeakInfo", ["JU.PT"], function () {
c$ = Clazz_decorateAsClass (function () {
this.xMin = 0;
this.xMax = 0;
this.yMin = 0;
this.yMax = 0;
this.px0 = 0;
this.px1 = 0;
this.stringInfo = null;
this.type = null;
this.type2 = null;
this.index = null;
this.file = null;
this.filePathForwardSlash = null;
this.title = null;
this.model = null;
this.atoms = null;
this.id = null;
this.spectrum = null;
this._match = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PeakInfo");
Clazz_makeConstructor (c$, 
function () {
});
Clazz_makeConstructor (c$, 
function (s) {
this.stringInfo = s;
this.type = JU.PT.getQuotedAttribute (s, "type");
if (this.type == null) this.type = "";
this.type = this.type.toUpperCase ();
var pt = this.type.indexOf ('/');
this.type2 = (pt < 0 ? "" : JSV.common.PeakInfo.fixType (this.type.substring (this.type.indexOf ('/') + 1)));
if (pt >= 0) this.type = JSV.common.PeakInfo.fixType (this.type.substring (0, pt)) + "/" + this.type2;
 else this.type = JSV.common.PeakInfo.fixType (this.type);
this.id = JU.PT.getQuotedAttribute (s, "id");
this.index = JU.PT.getQuotedAttribute (s, "index");
this.file = JU.PT.getQuotedAttribute (s, "file");
System.out.println ("pi file=" + this.file);
this.filePathForwardSlash = (this.file == null ? null : this.file.$replace ('\\', '/'));
this.model = JU.PT.getQuotedAttribute (s, "model");
var isBaseModel = s.contains ("baseModel=\"\"");
if (!isBaseModel) this.atoms = JU.PT.getQuotedAttribute (s, "atoms");
this.title = JU.PT.getQuotedAttribute (s, "title");
this._match = JU.PT.getQuotedAttribute (s, "_match");
this.xMax = JU.PT.parseFloat (JU.PT.getQuotedAttribute (s, "xMax"));
this.xMin = JU.PT.parseFloat (JU.PT.getQuotedAttribute (s, "xMin"));
this.yMax = JU.PT.parseFloat (JU.PT.getQuotedAttribute (s, "yMax"));
this.yMin = JU.PT.parseFloat (JU.PT.getQuotedAttribute (s, "yMin"));
}, "~S");
$_M(c$, "isClearAll", 
function () {
return (this.spectrum == null);
});
$_M(c$, "getType", 
function () {
return this.type;
});
$_M(c$, "getAtoms", 
function () {
return this.atoms;
});
$_M(c$, "getXMax", 
function () {
return this.xMax;
});
$_M(c$, "getXMin", 
function () {
return this.xMin;
});
$_M(c$, "getYMin", 
function () {
return this.yMin;
});
$_M(c$, "getYMax", 
function () {
return this.yMax;
});
$_M(c$, "getX", 
function () {
return (this.xMax + this.xMin) / 2;
});
$_M(c$, "getMatch", 
function () {
return this._match;
});
c$.fixType = $_M(c$, "fixType", 
function (type) {
return (type.equals ("HNMR") ? "1HNMR" : type.equals ("CNMR") ? "13CNMR" : type);
}, "~S");
$_V(c$, "toString", 
function () {
return this.stringInfo;
});
$_M(c$, "getIndex", 
function () {
return this.index;
});
$_M(c$, "getTitle", 
function () {
return this.title;
});
$_M(c$, "checkFileIndex", 
function (filePath, sIndex) {
return (sIndex.equals (this.index) && (filePath.equals (this.file) || filePath.equals (this.filePathForwardSlash)));
}, "~S,~S");
$_M(c$, "checkFileTypeModel", 
function (filePath, type, model) {
return filePath.equals (this.file) && this.checkModel (model) && this.type.endsWith (type);
}, "~S,~S,~S");
$_M(c$, "checkTypeModel", 
function (type, model) {
return this.checkType (type) && this.checkModel (model);
}, "~S,~S");
$_M(c$, "checkModel", 
function (model) {
return (model != null && model.equals (this.model));
}, "~S");
$_M(c$, "checkType", 
function (type) {
return (type.endsWith (this.type));
}, "~S");
$_M(c$, "checkTypeMatch", 
function (pi) {
return (this.checkType (pi.type) && (this.checkId (pi._match) || this.checkModel (pi._match) || this.title.toUpperCase ().indexOf (pi._match) >= 0));
}, "JSV.common.PeakInfo");
$_M(c$, "checkId", 
function (match) {
return (this.id != null && match != null && match.toUpperCase ().startsWith ("ID=") && match.substring (3).equals (this.id));
}, "~S");
$_M(c$, "getModel", 
function () {
return this.model;
});
$_M(c$, "getFilePath", 
function () {
return this.file;
});
$_M(c$, "autoSelectOnLoad", 
function () {
return (this.type.startsWith ("GC"));
});
$_M(c$, "setPixelRange", 
function (x0, x1) {
this.px0 = x0;
this.px1 = x1;
}, "~N,~N");
$_M(c$, "checkRange", 
function (xPixel, xVal) {
if (xPixel != 2147483647 ? (this.px0 <= xPixel && this.px1 >= xPixel) : xVal >= this.xMin && xVal <= this.xMax) {
return Math.abs (xVal - this.getX ());
}return 1e100;
}, "~N,~N");
$_M(c$, "getXPixel", 
function () {
return Clazz_doubleToInt ((this.px0 + this.px1) / 2);
});
c$.nullPeakInfo = c$.prototype.nullPeakInfo =  new JSV.common.PeakInfo ();
});
Clazz_declarePackage ("JSV.source");
Clazz_load (null, "JSV.source.JDXSourceStreamTokenizer", ["java.lang.Character", "JU.SB", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.br = null;
this.label = null;
this.value = null;
this.labelLineNo = 0;
this.line = null;
this.lineNo = 0;
Clazz_instantialize (this, arguments);
}, JSV.source, "JDXSourceStreamTokenizer");
Clazz_makeConstructor (c$, 
function (br) {
this.br = br;
}, "java.io.BufferedReader");
$_M(c$, "peakLabel", 
function () {
return this.nextLabel (false);
});
$_M(c$, "getLabel", 
function () {
return this.nextLabel (true);
});
$_M(c$, "nextLabel", 
function (isGet) {
this.label = null;
this.value = null;
while (this.line == null) {
try {
this.readLine ();
if (this.line == null) {
this.line = "";
return null;
}this.line = this.line.trim ();
} catch (e) {
if (Clazz_exceptionOf (e, java.io.IOException)) {
this.line = "";
return null;
} else {
throw e;
}
}
if (this.line.startsWith ("##")) break;
this.line = null;
}
var pt = this.line.indexOf ("=");
if (pt < 0) {
if (isGet) J.util.Logger.info ("BAD JDX LINE -- no '=' (line " + this.lineNo + "): " + this.line);
this.label = this.line;
if (!isGet) this.line = "";
} else {
this.label = this.line.substring (0, pt).trim ();
if (isGet) this.line = this.line.substring (pt + 1);
}this.labelLineNo = this.lineNo;
if (J.util.Logger.debugging) J.util.Logger.info (this.label);
return JSV.source.JDXSourceStreamTokenizer.cleanLabel (this.label);
}, "~B");
c$.cleanLabel = $_M(c$, "cleanLabel", 
function (label) {
if (label == null) return null;
var i;
var str =  new JU.SB ();
for (i = 0; i < label.length; i++) {
switch (label.charAt (i)) {
case '/':
case '\\':
case ' ':
case '-':
case '_':
break;
default:
str.appendC (label.charAt (i));
break;
}
}
return str.toString ().toUpperCase ();
}, "~S");
$_M(c$, "getRawLabel", 
function () {
return this.label;
});
$_M(c$, "getLabelLineNo", 
function () {
return this.labelLineNo;
});
$_M(c$, "getValue", 
function () {
if (this.value != null) return this.value;
var sb =  new JU.SB ().append (this.line);
if (sb.length () > 0) sb.appendC ('\n');
try {
while (this.readLine () != null) {
if (this.line.indexOf ("##") >= 0 && this.line.trim ().startsWith ("##")) break;
sb.append (this.line).appendC ('\n');
}
} catch (e) {
if (Clazz_exceptionOf (e, java.io.IOException)) {
e.printStackTrace ();
} else {
throw e;
}
}
this.value = JSV.source.JDXSourceStreamTokenizer.trimLines (sb);
if (J.util.Logger.debugging) J.util.Logger.info (this.value);
return this.value;
});
$_M(c$, "readLineTrimmed", 
function () {
this.readLine ();
if (this.line == null) return null;
if (this.line.indexOf ("$$") < 0) return this.line.trim ();
var sb =  new JU.SB ().append (this.line);
return JSV.source.JDXSourceStreamTokenizer.trimLines (sb);
});
$_M(c$, "flushLine", 
function () {
var sb =  new JU.SB ().append (this.line);
this.line = null;
return JSV.source.JDXSourceStreamTokenizer.trimLines (sb);
});
$_M(c$, "readLine", 
function () {
this.line = this.br.readLine ();
this.lineNo++;
return this.line;
});
c$.trimLines = $_M(c$, "trimLines", 
function (v) {
var n = v.length ();
var ilast = n - 1;
var vpt = JSV.source.JDXSourceStreamTokenizer.ptNonWhite (v, 0, n);
if (vpt >= n) return "";
if (v.charAt (vpt) == '<') {
n = v.lastIndexOf (">") + 1;
if (n == 0) n = v.length ();
return v.toString ().substring (vpt, n);
}var buffer =  Clazz_newCharArray (n - vpt, '\0');
var pt = 0;
for (; vpt < n; vpt++) {
var ch;
switch (ch = v.charAt (vpt)) {
case '\r':
if (vpt < ilast && v.charAt (vpt + 1) == '\n') continue;
ch = '\n';
break;
case '\n':
if (pt > 0 && buffer[pt - 1] != '\n') pt -= vpt - JSV.source.JDXSourceStreamTokenizer.ptNonSpaceRev (v, vpt) - 1;
vpt = JSV.source.JDXSourceStreamTokenizer.ptNonSpace (v, ++vpt, n) - 1;
break;
case '$':
if (vpt < ilast && v.charAt (vpt + 1) == '$') {
vpt++;
while (++vpt < n && "\n\r".indexOf (v.charAt (vpt)) < 0) {
}
continue;
}break;
}
if (ch == '\n' && pt > 0 && buffer[pt - 1] == '\n') continue;
buffer[pt++] = ch;
}
if (pt > 0 && buffer[pt - 1] == '\n') --pt;
return ( String.instantialize (buffer)).substring (0, pt).trim ();
}, "JU.SB");
c$.ptNonWhite = $_M(c$, "ptNonWhite", 
function (v, pt, n) {
while (pt < n && Character.isWhitespace (v.charAt (pt))) pt++;

return pt;
}, "JU.SB,~N,~N");
c$.ptNonSpace = $_M(c$, "ptNonSpace", 
function (v, pt, n) {
while (pt < n && (v.charAt (pt) == ' ' || v.charAt (pt) == '\t')) pt++;

return pt;
}, "JU.SB,~N,~N");
c$.ptNonSpaceRev = $_M(c$, "ptNonSpaceRev", 
function (v, pt) {
while (--pt >= 0 && (v.charAt (pt) == ' ' || v.charAt (pt) == '\t')) {
}
return pt;
}, "JU.SB,~N");
});
$_L(["java.io.Closeable","$.Flushable","java.lang.Appendable"],"java.io.Writer",["java.lang.NullPointerException","$.StringIndexOutOfBoundsException"],function(){
c$=$_C(function(){
this.lock=null;
$_Z(this,arguments);
},java.io,"Writer",null,[Appendable,java.io.Closeable,java.io.Flushable]);
$_K(c$,
function(){
this.lock=this;
});
$_K(c$,
function(lock){
if(lock!=null){
this.lock=lock;
}else{
throw new NullPointerException();
}},"~O");
$_M(c$,"write",
function(buf){
this.write(buf,0,buf.length);
},"~A");
$_M(c$,"write",
function(oneChar){
{
var oneCharArray=$_A(1,'\0');
oneCharArray[0]=String.fromCharCode(oneChar);
this.write(oneCharArray);
}},"~N");
$_M(c$,"write",
function(str){
var buf=$_A(str.length,'\0');
str.getChars(0,buf.length,buf,0);
{
this.write(buf);
}},"~S");
$_M(c$,"write",
function(str,offset,count){
if(count>=0){
var buf=$_A(count,'\0');
str.getChars(offset,offset+count,buf,0);
{
this.write(buf);
}}else{
throw new StringIndexOutOfBoundsException();
}},"~S,~N,~N");
$_M(c$,"append",
function(c){
this.write(c.charCodeAt(0));
return this;
},"~N");
$_M(c$,"append",
function(csq){
if(null==csq){
this.write("null");
}else{
this.write(csq.toString());
}return this;
},"CharSequence");
$_M(c$,"append",
function(csq,start,end){
if(null==csq){
this.write("null".substring(start,end));
}else{
this.write(csq.subSequence(start,end).toString());
}return this;
},"CharSequence,~N,~N");
$_S(c$,
"TOKEN_NULL","null");
});
$_L(["java.io.Writer"],"java.io.BufferedWriter",["java.io.IOException","java.lang.IllegalArgumentException","$.IndexOutOfBoundsException","$.StringIndexOutOfBoundsException"],function(){
c$=$_C(function(){
this.out=null;
this.buf=null;
this.pos=0;
this.lineSeparator="\r\n";
$_Z(this,arguments);
},java.io,"BufferedWriter",java.io.Writer);
$_K(c$,
function(out){
$_R(this,java.io.BufferedWriter,[out]);
this.out=out;
this.buf=$_A(8192,'\0');
},"java.io.Writer");
$_K(c$,
function(out,size){
$_R(this,java.io.BufferedWriter,[out]);
if(size>0){
this.out=out;
this.buf=$_A(size,'\0');
}else{
throw new IllegalArgumentException(("K0058"));
}},"java.io.Writer,~N");
$_M(c$,"close",
function(){
{
if(this.isOpen()){
this.flush();
this.out.close();
this.buf=null;
this.out=null;
}}});
$_M(c$,"flush",
function(){
{
if(this.isOpen()){
if(this.pos>0){
this.out.write(this.buf,0,this.pos);
}this.pos=0;
this.out.flush();
}else{
throw new java.io.IOException(("K005d"));
}}});
$_M(c$,"isOpen",
($fz=function(){
return this.out!=null;
},$fz.isPrivate=true,$fz));
$_M(c$,"newLine",
function(){
this.write("\r\n",0,"\r\n".length);
});
$_M(c$,"write",
function(cbuf,offset,count){
{
if(!this.isOpen()){
throw new java.io.IOException(("K005d"));
}if(offset<0||offset>cbuf.length-count||count<0){
throw new IndexOutOfBoundsException();
}if(this.pos==0&&count>=this.buf.length){
this.out.write(cbuf,offset,count);
return;
}var available=this.buf.length-this.pos;
if(count<available){
available=count;
}if(available>0){
System.arraycopy(cbuf,offset,this.buf,this.pos,available);
this.pos+=available;
}if(this.pos==this.buf.length){
this.out.write(this.buf,0,this.buf.length);
this.pos=0;
if(count>available){
offset+=available;
available=count-available;
if(available>=this.buf.length){
this.out.write(cbuf,offset,available);
return;
}System.arraycopy(cbuf,offset,this.buf,this.pos,available);
this.pos+=available;
}}}},"~A,~N,~N");
$_M(c$,"write",
function(oneChar){
{
if(this.isOpen()){
if(this.pos>=this.buf.length){
this.out.write(this.buf,0,this.buf.length);
this.pos=0;
}this.buf[this.pos++]=String.fromCharCode(oneChar);
}else{
throw new java.io.IOException(("K005d"));
}}},"~N");
$_M(c$,"write",
function(str,offset,count){
{
if(!this.isOpen()){
throw new java.io.IOException(("K005d"));
}if(count<=0){
return;
}if(offset>str.length-count||offset<0){
throw new StringIndexOutOfBoundsException();
}if(this.pos==0&&count>=this.buf.length){
var chars=$_A(count,'\0');
str.getChars(offset,offset+count,chars,0);
this.out.write(chars,0,count);
return;
}var available=this.buf.length-this.pos;
if(count<available){
available=count;
}if(available>0){
str.getChars(offset,offset+available,this.buf,this.pos);
this.pos+=available;
}if(this.pos==this.buf.length){
this.out.write(this.buf,0,this.buf.length);
this.pos=0;
if(count>available){
offset+=available;
available=count-available;
if(available>=this.buf.length){
var chars=$_A(count,'\0');
str.getChars(offset,offset+available,chars,0);
this.out.write(chars,0,available);
return;
}str.getChars(offset,offset+available,this.buf,this.pos);
this.pos+=available;
}}}},"~S,~N,~N");
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVGraphics");
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.ScriptTokenizer", ["JU.PT"], function () {
c$ = Clazz_decorateAsClass (function () {
this.str = null;
this.pt = -1;
this.len = 0;
this.isCmd = false;
this.doCheck = true;
Clazz_instantialize (this, arguments);
}, JSV.common, "ScriptTokenizer");
Clazz_makeConstructor (c$, 
function (str, isCmd) {
this.str = str;
this.len = str.length;
this.isCmd = isCmd;
}, "~S,~B");
c$.nextStringToken = $_M(c$, "nextStringToken", 
function (eachParam, removeQuotes) {
var s = eachParam.nextToken ();
return (removeQuotes && s.charAt (0) == '"' && s.endsWith ("\"") && s.length > 1 ? JU.PT.trimQuotes (s) : s);
}, "JSV.common.ScriptTokenizer,~B");
$_M(c$, "nextToken", 
function () {
if (this.doCheck) this.hasMoreTokens ();
var pt0 = this.pt;
var inQuote = (this.str.charAt (this.pt) == '"');
while (++this.pt < this.len) {
switch (this.str.charAt (this.pt)) {
case '"':
if (inQuote) {
if (this.isCmd) {
inQuote = false;
continue;
}this.pt++;
break;
}if (this.isCmd) inQuote = true;
continue;
case ' ':
if (!this.isCmd && !inQuote) break;
continue;
case ';':
case '\n':
if (this.isCmd && !inQuote) break;
continue;
default:
continue;
}
break;
}
this.doCheck = true;
return this.str.substring (pt0, this.pt);
});
$_M(c$, "hasMoreTokens", 
function () {
while (++this.pt < this.len) {
switch (this.str.charAt (this.pt)) {
case ' ':
case ';':
case '\n':
continue;
}
break;
}
this.doCheck = false;
return (this.pt < this.len);
});
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.Annotation", "$.Coordinate"], "JSV.common.Measurement", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.pt2 = null;
this.value = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "Measurement", JSV.common.Annotation);
Clazz_prepareFields (c$, function () {
this.pt2 =  new JSV.common.Coordinate ();
});
$_M(c$, "setM1", 
function (x, y, spec) {
this.setA (x, y, spec, "", false, false, 0, 6);
this.setPt2 (this.getXVal (), this.getYVal ());
return this;
}, "~N,~N,JSV.common.JDXSpectrum");
$_M(c$, "copyM", 
function () {
var m =  new JSV.common.Measurement ();
m.setA (this.getXVal (), this.getYVal (), this.spec, this.text, false, false, this.offsetX, this.offsetY);
m.setPt2 (this.pt2.getXVal (), this.pt2.getYVal ());
return m;
});
$_M(c$, "setPt2", 
function (spec, doSetPt2) {
this.spec = spec;
if (doSetPt2) this.setPt2 (this.getXVal (), this.getYVal ());
return this;
}, "JSV.common.JDXSpectrum,~B");
$_M(c$, "setPt2", 
function (x, y) {
this.pt2.setXVal (x);
this.pt2.setYVal (y);
this.value = Math.abs (x - this.getXVal ());
this.text = this.spec.setMeasurementText (this);
}, "~N,~N");
$_M(c$, "getSpectrum", 
function () {
return this.spec;
});
$_M(c$, "setValue", 
function (value) {
this.value = value;
this.text = this.spec.setMeasurementText (this);
}, "~N");
$_M(c$, "getValue", 
function () {
return this.value;
});
$_M(c$, "getXVal2", 
function () {
return this.pt2.getXVal ();
});
$_M(c$, "getYVal2", 
function () {
return this.pt2.getYVal ();
});
$_V(c$, "addSpecShift", 
function (dx) {
this.setXVal (this.getXVal () + dx);
this.pt2.setXVal (this.pt2.getXVal () + dx);
}, "~N");
$_M(c$, "setYVal2", 
function (y2) {
this.pt2.setYVal (y2);
}, "~N");
$_M(c$, "overlaps", 
function (x1, x2) {
return (Math.min (this.getXVal (), this.getXVal2 ()) < Math.max (x1, x2) && Math.max (this.getXVal (), this.getXVal2 ()) > Math.min (x1, x2));
}, "~N,~N");
$_V(c$, "toString", 
function () {
return "[" + this.getXVal () + "," + this.pt2.getXVal () + "]";
});
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.Measurement"], "JSV.common.Integral", null, function () {
c$ = Clazz_declareType (JSV.common, "Integral", JSV.common.Measurement);
$_M(c$, "setInt", 
function (x1, y1, spec, value, x2, y2) {
this.setA (x1, y1, spec, "", false, false, 0, 6);
this.setPt2 (x2, y2);
this.setValue (value);
return this;
}, "~N,~N,JSV.common.JDXSpectrum,~N,~N,~N");
});
Clazz_declarePackage ("JSV.exception");
Clazz_load (["java.lang.Exception"], "JSV.exception.JSpecViewException", null, function () {
c$ = Clazz_declareType (JSV.exception, "JSpecViewException", Exception);
});
Clazz_declarePackage ("JSV.dialog");
Clazz_load (["JSV.api.AnnotationData", "JSV.common.Annotation"], "JSV.dialog.JSVDialog", ["java.lang.Double", "JU.DF", "$.PT", "JSV.common.IntegralData", "$.PeakData"], function () {
c$ = Clazz_decorateAsClass (function () {
this.optionKey = null;
this.options = null;
this.type = null;
this.title = null;
this.viewer = null;
this.$spec = null;
this.manager = null;
this.dialog = null;
this.jsvp = null;
this.txt1 = null;
this.txt2 = null;
this.txt3 = null;
this.combo1 = null;
this.xyData = null;
this.myParams = null;
this.precision = 1;
this.loc = null;
this.showHideButton = null;
this.addClearBtn = false;
this.addCombo1 = false;
this.addApplyBtn = false;
this.isNumeric = false;
this.defaultVisible = false;
this.subType = null;
this.graphSetKey = null;
this.tableData = null;
this.addUnits = false;
this.unitOptions = null;
this.formatOptions = null;
this.unitPtr = null;
this.isON = true;
this.lastNorm = 1;
this.iRowColSelected = -1;
this.iSelected = -1;
this.skipCreate = false;
this.iRowSelected = -1;
this.iColSelected = -1;
Clazz_instantialize (this, arguments);
}, JSV.dialog, "JSVDialog", JSV.common.Annotation, JSV.api.AnnotationData);
$_M(c$, "setParams", 
function (title, viewer, spec) {
this.title = title;
this.viewer = viewer;
this.$spec = spec;
this.manager = viewer.getDialogManager ();
this.jsvp = viewer.selectedPanel;
this.myParams = (viewer.getPlatformInterface ("Parameters")).setName ("dialogParams");
this.subType = (spec == null ? "!" : spec.getTypeLabel ());
this.optionKey = this.type + "_" + this.subType;
this.options = this.manager.getDialogOptions ();
if (spec != null) {
var specOptions = spec.getDefaultAnnotationInfo (this.type);
this.options.put (this.optionKey, specOptions);
this.unitOptions = specOptions[0];
this.formatOptions = specOptions[1];
this.unitPtr = this.options.get (this.optionKey + "_unitPtr");
if (this.unitPtr == null) this.unitPtr = specOptions[2];
}switch (this.type) {
case JSV.common.Annotation.AType.Integration:
this.isNumeric = true;
this.addClearBtn = true;
this.defaultVisible = true;
this.addApplyBtn = true;
break;
case JSV.common.Annotation.AType.Measurements:
this.isNumeric = true;
this.addClearBtn = true;
this.addCombo1 = true;
this.defaultVisible = true;
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.PeakList:
this.isNumeric = true;
this.addApplyBtn = true;
this.defaultVisible = true;
break;
case JSV.common.Annotation.AType.Views:
this.defaultVisible = true;
break;
case JSV.common.Annotation.AType.NONE:
break;
}
this.initDialog ();
return this;
}, "~S,JSV.common.JSViewer,JSV.common.JDXSpectrum");
$_M(c$, "initDialog", 
function () {
this.dialog = this.manager.getDialog (this);
this.restoreDialogPosition (this.jsvp, this.getPosXY ());
this.dialog.setTitle (this.title);
this.layoutDialog ();
});
$_M(c$, "layoutDialog", 
function () {
this.dialog.startLayout ();
this.addUniqueControls ();
if (this.isNumeric) {
this.getUnitOptions ();
if (this.addCombo1) this.combo1 = this.dialog.addSelectOption ("cmbUnits", "Units", this.unitOptions, this.unitPtr.intValue (), this.addUnits);
if (this.addApplyBtn) this.dialog.addButton ("btnApply", "Apply");
this.showHideButton = this.dialog.addButton ("btnShow", "Show");
if (this.addClearBtn) this.dialog.addButton ("btnClear", "Clear");
}this.dialog.endLayout ();
this.checkEnables ();
this.dialog.setVisible (this.defaultVisible);
});
$_M(c$, "callbackAD", 
function (id, msg) {
if (id.equals ("tableSelect")) {
this.tableSelect (msg);
} else if (id.equals ("btnClear")) {
this.clear ();
} else if (id.equals ("btnApply")) {
this.eventApply ();
} else if (id.equals ("btnShow")) {
var label = this.dialog.getText (this.showHideButton);
this.eventShowHide (label.equals ("Show"));
} else if (id.equals ("cmbUnits")) {
this.setPrecision (this.dialog.getSelectedIndex (this.combo1));
} else if (id.startsWith ("txt")) {
this.eventApply ();
} else if (id.equals ("windowClosing")) {
this.done ();
return true;
}if (this.jsvp != null) this.jsvp.doRepaint (true);
return true;
}, "~S,~S");
$_M(c$, "addUniqueControls", 
function (dialogHelper) {
}, "JSV.dialog.DialogManager");
$_V(c$, "getAType", 
function () {
return this.type;
});
$_V(c$, "getGraphSetKey", 
function () {
return this.graphSetKey;
});
$_V(c$, "setGraphSetKey", 
function (key) {
this.graphSetKey = key;
}, "~S");
$_V(c$, "getSpectrum", 
function () {
return this.$spec;
});
$_V(c$, "getState", 
function () {
return this.isON;
});
$_V(c$, "setState", 
function (b) {
this.isON = b;
}, "~B");
$_M(c$, "checkEnables", 
function () {
var isShow = this.checkVisible ();
this.dialog.setText (this.showHideButton, isShow ? "Hide" : "Show");
});
$_M(c$, "createTable", 
function (data, header, widths) {
this.tableData = data;
this.dialog.createTable (data, header, widths);
}, "~A,~A,~A");
$_M(c$, "setTableSelectionEnabled", 
function (enabled) {
this.dialog.setCellSelectionEnabled (enabled);
}, "~B");
$_M(c$, "getParameters", 
function () {
return this.myParams;
});
$_M(c$, "showMessage", 
function (msg, title, msgType) {
this.manager.showMessageDialog (this.dialog, msg, title, msgType);
}, "~S,~S,~N");
$_M(c$, "setThreshold", 
function (y) {
this.dialog.setText (this.txt1, this.getThreasholdText (y));
}, "~N");
$_M(c$, "setComboSelected", 
function (i) {
this.dialog.setSelectedIndex (this.combo1, i);
}, "~N");
$_M(c$, "applyFromFields", 
function () {
this.apply (null);
});
$_M(c$, "reEnable", 
function () {
this.paramsReEnable ();
return this;
});
$_M(c$, "dispose", 
function () {
this.dialog.dispose ();
});
$_M(c$, "setVisible", 
function (visible) {
this.dialog.setVisible (visible);
}, "~B");
$_V(c$, "isVisible", 
function () {
return this.dialog.isVisible ();
});
$_M(c$, "selectTableRow", 
function (i) {
this.dialog.selectTableRow (i);
}, "~N");
$_M(c$, "repaint", 
function () {
this.dialog.repaint ();
});
$_M(c$, "setFields", 
function () {
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
break;
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
this.myParams = this.xyData.getParameters ();
this.setThreshold (this.myParams.peakListThreshold);
this.setComboSelected (this.myParams.peakListInterpolation.equals ("none") ? 1 : 0);
this.createData ();
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
});
$_M(c$, "update", 
function (clicked, xRange, yOffset) {
this.selectTableRow (-1);
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
this.loadData ();
this.checkEnables ();
break;
case JSV.common.Annotation.AType.Measurements:
this.loadData ();
this.checkEnables ();
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
if (yOffset > 20) this.applyFromFields ();
if (this.xyData == null || clicked == null || yOffset > 20) return;
var ipt = 0;
var dx0 = 1e100;
var xval = clicked.getXVal ();
var md = this.xyData;
var min = Math.abs (xRange / 20);
for (var i = md.size (); --i >= 0; ) {
var dx = Math.abs (xval - md.get (i).getXVal ());
if (dx < dx0) {
dx0 = dx;
ipt = i;
}}
if (dx0 < min) {
this.selectTableRow (md.size () - 2 - ipt);
this.repaint ();
}break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
}, "JSV.common.Coordinate,~N,~N");
$_M(c$, "getPeakData", 
function () {
var md =  new JSV.common.PeakData (JSV.common.Annotation.AType.PeakList, this.$spec);
md.setPeakList (this.myParams, this.precision, this.jsvp.getPanelData ().getView ());
this.xyData = md;
return null;
});
$_V(c$, "getData", 
function () {
if (this.xyData == null) this.createData ();
return this.xyData;
});
$_M(c$, "setData", 
function (data) {
this.myParams = data.getParameters ();
this.xyData = data;
}, "JSV.api.AnnotationData");
$_V(c$, "setSpecShift", 
function (dx) {
if (this.xyData != null) this.xyData.setSpecShift (dx);
}, "~N");
$_M(c$, "setType", 
function (type) {
this.type = type;
switch (type) {
case JSV.common.Annotation.AType.Measurements:
this.addUnits = true;
break;
case JSV.common.Annotation.AType.Integration:
break;
case JSV.common.Annotation.AType.PeakList:
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
case JSV.common.Annotation.AType.NONE:
break;
}
}, "JSV.common.Annotation.AType");
$_M(c$, "apply", 
function (objects) {
try {
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
var offset = Double.parseDouble (objects[0]);
var scale = Double.parseDouble (objects[1]);
this.myParams.integralOffset = offset;
this.myParams.integralRange = scale;
this.myParams.integralDrawAll = false;
(this.getData ()).update (this.myParams);
break;
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
return;
case JSV.common.Annotation.AType.PeakList:
if (!this.skipCreate) {
this.setThreshold (NaN);
this.createData ();
}this.skipCreate = false;
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
this.loadData ();
this.checkEnables ();
this.jsvp.doRepaint (true);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}, "~A");
$_M(c$, "done", 
function () {
if (this.jsvp != null && this.$spec != null) this.jsvp.getPanelData ().removeDialog (this);
if (this.xyData != null) this.xyData.setState (this.isON);
this.saveDialogPosition (this.getPosXY ());
this.dispose ();
this.jsvp.doRepaint (true);
});
$_M(c$, "restoreDialogPosition", 
function (panel, posXY) {
if (panel != null) {
if (posXY[0] == -2147483648) {
posXY[0] = 0;
posXY[1] = -20;
}var pt = this.manager.getLocationOnScreen (panel);
var height = panel.getHeight ();
this.loc = [Math.max (0, pt[0] + posXY[0]), Math.max (0, pt[1] + height + posXY[1])];
this.dialog.setIntLocation (this.loc);
}}, "JSV.api.JSVPanel,~A");
$_M(c$, "saveDialogPosition", 
function (posXY) {
try {
var pt = this.manager.getLocationOnScreen (this.dialog);
posXY[0] += pt[0] - this.loc[0];
posXY[1] += pt[1] - this.loc[1];
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}, "~A");
$_M(c$, "getThreasholdText", 
function (y) {
if (Double.isNaN (y)) {
var pd = this.jsvp.getPanelData ();
var f = (pd.getSpectrum ().isInverted () ? 0.1 : 0.9);
var c = pd.getClickedCoordinate ();
y = (c == null ? (pd.getView ().minYOnScale * f + pd.getView ().maxYOnScale) * (1 - f) : c.getYVal ());
}var sy = JU.DF.formatDecimalDbl (y, y < 1000 ? 2 : -2);
return " " + sy;
}, "~N");
$_M(c$, "checkVisible", 
function () {
return this.viewer.selectedPanel.getPanelData ().getShowAnnotation (this.type);
});
$_M(c$, "getUnitOptions", 
function () {
var key = this.optionKey + "_format";
var format = this.options.get (key);
if (format == null) this.options.put (key, format = Integer.$valueOf (this.formatOptions[this.unitPtr == null ? 0 : this.unitPtr.intValue ()]));
});
$_M(c$, "eventApply", 
function () {
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
break;
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
this.createData ();
this.skipCreate = true;
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
this.applyFromFields ();
});
$_M(c$, "eventShowHide", 
function (isShow) {
this.isON = isShow;
if (isShow) this.eventApply ();
this.jsvp.doRepaint (true);
this.checkEnables ();
}, "~B");
$_M(c$, "clear", 
function () {
this.setState (true);
if (this.xyData != null) {
this.xyData.clear ();
this.applyFromFields ();
}});
$_M(c$, "paramsReEnable", 
function () {
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
break;
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
this.skipCreate = true;
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
this.setVisible (true);
this.isON = true;
this.applyFromFields ();
});
$_M(c$, "tableCellSelect", 
function (iRow, iCol) {
var value = this.tableData[iRow][1];
var icolrow = this.iRowSelected * 1000 + this.iColSelected;
if (icolrow == this.iRowColSelected) return;
this.iRowColSelected = icolrow;
this.selectTableRow (this.iRowSelected);
try {
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
for (var i = 0; i < this.xyData.size (); i++) if (JU.DF.formatDecimalDbl (this.xyData.get (i).getXVal (), 2).equals (value)) {
this.iSelected = i;
this.jsvp.getPanelData ().setXPointers (this.$spec, this.xyData.get (i).getXVal (), this.$spec, this.xyData.get (i).getXVal2 ());
this.jsvp.doRepaint (true);
break;
}
this.checkEnables ();
break;
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
try {
switch (iCol) {
case 6:
case 5:
case 4:
var x1 = Double.parseDouble (value);
var x2 = Double.parseDouble (this.tableData[iRow + 3 - iCol][1]);
this.jsvp.getPanelData ().setXPointers (this.$spec, x1, this.$spec, x2);
break;
default:
this.jsvp.getPanelData ().findX (this.$spec, Double.parseDouble (value));
}
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
this.jsvp.getPanelData ().findX (this.$spec, 1E100);
} else {
throw e;
}
}
this.jsvp.doRepaint (false);
break;
case JSV.common.Annotation.AType.OverlayLegend:
this.jsvp.getPanelData ().setSpectrum (iRow, false);
break;
case JSV.common.Annotation.AType.Views:
break;
}
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}, "~N,~N");
$_M(c$, "loadData", 
function () {
var data;
var header;
var widths;
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
if (this.xyData == null) this.createData ();
this.iSelected = -1;
data = (this.xyData).getMeasurementListArray (null);
header = this.xyData.getDataHeader ();
widths = [40, 65, 65, 50];
this.createTable (data, header, widths);
break;
case JSV.common.Annotation.AType.Measurements:
if (this.xyData == null) return;
data = this.xyData.getMeasurementListArray (this.dialog.getSelectedItem (this.combo1).toString ());
header = this.xyData.getDataHeader ();
widths = [40, 65, 65, 50];
this.createTable (data, header, widths);
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
if (this.xyData == null) this.createData ();
data = (this.xyData).getMeasurementListArray (null);
header = (this.xyData).getDataHeader ();
widths = [40, 65, 50, 50, 50, 50, 50];
this.createTable (data, header, widths);
this.setTableSelectionEnabled (true);
break;
case JSV.common.Annotation.AType.OverlayLegend:
header = ["No.", "Plot Color", "Title"];
data = this.viewer.selectedPanel.getPanelData ().getOverlayLegendData ();
widths = [30, 60, 250];
this.createTable (data, header, widths);
this.setTableSelectionEnabled (true);
break;
case JSV.common.Annotation.AType.Views:
break;
}
});
$_M(c$, "createData", 
function () {
switch (this.type) {
case JSV.common.Annotation.AType.Integration:
this.xyData =  new JSV.common.IntegralData (this.$spec, this.myParams);
this.iSelected = -1;
break;
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
break;
case JSV.common.Annotation.AType.PeakList:
try {
var thresh = Double.parseDouble (this.dialog.getText (this.txt1));
this.myParams.peakListThreshold = thresh;
this.myParams.peakListInterpolation = this.dialog.getSelectedItem (this.combo1).toString ();
this.myParams.precision = this.precision;
var md =  new JSV.common.PeakData (JSV.common.Annotation.AType.PeakList, this.$spec);
md.setPeakList (this.myParams, this.precision, this.jsvp.getPanelData ().getView ());
this.xyData = md;
this.loadData ();
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
break;
case JSV.common.Annotation.AType.OverlayLegend:
break;
case JSV.common.Annotation.AType.Views:
break;
}
});
$_M(c$, "setPrecision", 
function (i) {
this.precision = this.formatOptions[i];
}, "~N");
$_M(c$, "tableSelect", 
function (url) {
var isAdjusting = "true".equals (this.getField (url, "adjusting"));
if (isAdjusting) {
this.iColSelected = this.iRowSelected = -1;
return;
}var index = JU.PT.parseInt (this.getField (url, "index"));
switch ("ROW COL ROWCOL".indexOf (this.getField (url, "selector"))) {
case 8:
this.iColSelected = JU.PT.parseInt (this.getField (url, "index2"));
case 0:
this.iRowSelected = index;
break;
case 4:
this.iColSelected = index;
break;
}
if (this.iColSelected >= 0 && this.iRowSelected >= 0) {
this.tableCellSelect (this.iRowSelected, this.iColSelected);
}}, "~S");
$_M(c$, "getField", 
function (url, name) {
url += "&";
var key = "&" + name + "=";
var pt = url.indexOf (key);
return (pt < 0 ? null : url.substring (pt + key.length, url.indexOf ("&", pt + 1)));
}, "~S,~S");
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "XYScaleConverter");
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum", "JSV.api.XYScaleConverter", "java.util.Hashtable", "JU.BS", "$.List", "JSV.common.Coordinate", "JSV.dialog.JSVDialog"], "JSV.common.GraphSet", ["java.lang.Boolean", "$.Double", "$.Float", "JU.DF", "$.PT", "JSV.common.Annotation", "$.ColorParameters", "$.ColoredAnnotation", "$.ImageView", "$.Integral", "$.IntegralData", "$.JDXSpectrum", "$.Measurement", "$.MeasurementData", "$.PanelData", "$.Parameters", "$.PeakData", "$.PeakPickEvent", "$.PlotWidget", "$.ScaleData", "$.ScriptToken", "$.ViewData", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.gs2dLinkedX = null;
this.gs2dLinkedY = null;
this.cur1D2Locked = false;
this.highlights = null;
this.spectra = null;
this.isSplittable = true;
this.allowStacking = true;
this.splitPointers = null;
this.annotations = null;
this.selectedSpectrumMeasurements = null;
this.selectedSpectrumIntegrals = null;
this.lastAnnotation = null;
this.pendingMeasurement = null;
this.pendingIntegral = null;
this.graphsTemp = null;
this.widgets = null;
this.isLinked = false;
this.haveSingleYScale = false;
this.iSpectrumMovedTo = 0;
this.iSpectrumClicked = 0;
this.iSpectrumSelected = -1;
this.stackSelected = false;
this.bsSelected = null;
this.viewData = null;
this.reversePlot = false;
this.nSplit = 1;
this.yStackOffsetPercent = 0;
this.showAllStacked = true;
this.viewList = null;
this.imageView = null;
this.pd = null;
this.sticky2Dcursor = false;
this.nSpectra = 0;
this.fracX = 1;
this.fracY = 1;
this.fX0 = 0;
this.fY0 = 0;
this.zoomBox1D = null;
this.zoomBox2D = null;
this.pin1Dx0 = null;
this.pin1Dx1 = null;
this.pin1Dy0 = null;
this.pin1Dy1 = null;
this.pin1Dx01 = null;
this.pin1Dy01 = null;
this.pin2Dx0 = null;
this.pin2Dx1 = null;
this.pin2Dy0 = null;
this.pin2Dy1 = null;
this.pin2Dx01 = null;
this.pin2Dy01 = null;
this.cur2Dx0 = null;
this.cur2Dx1 = null;
this.cur1D2x1 = null;
this.cur1D2x2 = null;
this.cur2Dy = null;
this.xPixel0 = 0;
this.yPixel0 = 0;
this.xPixel1 = 0;
this.yPixel1 = 0;
this.xVArrows = 0;
this.xHArrows = 0;
this.yHArrows = 0;
this.xPixel00 = 0;
this.yPixel00 = 0;
this.xPixel11 = 0;
this.yPixel11 = 0;
this.yPixel000 = 0;
this.xPixels = 0;
this.yPixels = 0;
this.xPixel10 = 0;
this.xPixels0 = 0;
this.allowStackedYScale = true;
this.drawXAxisLeftToRight = false;
this.xAxisLeftToRight = true;
this.iPreviousSpectrumClicked = -1;
this.$haveSelectedSpectrum = false;
this.zoomEnabled = false;
this.currentZoomIndex = 0;
this.lastClickX = NaN;
this.lastPixelX = 2147483647;
this.height = 0;
this.width = 0;
this.right = 0;
this.top = 0;
this.left = 0;
this.bottom = 0;
this.piMouseOver = null;
this.coordTemp = null;
this.FONT_PLAIN = 0;
this.FONT_BOLD = 1;
this.FONT_ITALIC = 2;
this.is2DSpectrum = false;
this.selectedMeasurement = null;
this.selectedIntegral = null;
this.lastXMax = NaN;
this.lastSpecClicked = -1;
this.inPlotMove = false;
this.xPixelMovedTo = -1;
this.xPixelMovedTo2 = -1;
this.yValueMovedTo = 0;
this.xValueMovedTo = 0;
this.haveLeftRightArrows = false;
this.xPixelPlot1 = 0;
this.xPixelPlot0 = 0;
this.yPixelPlot0 = 0;
this.yPixelPlot1 = 0;
this.nextClickForSetPeak = false;
this.mapX = null;
if (!Clazz_isClassDefined ("JSV.common.GraphSet.Highlight")) {
JSV.common.GraphSet.$GraphSet$Highlight$ ();
}
this.triggered = true;
this.dialogs = null;
this.aIntegrationRatios = null;
this.jsvp = null;
this.image2D = null;
this.plotColors = null;
this.g2d = null;
this.gMain = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "GraphSet", null, JSV.api.XYScaleConverter);
Clazz_prepareFields (c$, function () {
this.highlights =  new JU.List ();
this.spectra =  new JU.List ();
this.splitPointers = [0];
this.graphsTemp =  new JU.List ();
this.bsSelected =  new JU.BS ();
this.coordTemp =  new JSV.common.Coordinate ();
this.mapX =  new java.util.Hashtable ();
});
Clazz_makeConstructor (c$, 
function (pd) {
this.pd = pd;
this.jsvp = pd.jsvp;
this.g2d = pd.g2d;
}, "JSV.common.PanelData");
$_M(c$, "setSpectrumMovedTo", 
function (i) {
return this.iSpectrumMovedTo = i;
}, "~N");
$_M(c$, "setSpectrumClicked", 
function (i) {
this.stackSelected = this.showAllStacked;
if (i < 0 || this.iSpectrumClicked != i) {
this.lastClickX = NaN;
this.lastPixelX = 2147483647;
}this.iSpectrumClicked = this.setSpectrumSelected (this.setSpectrumMovedTo (i));
}, "~N");
$_M(c$, "setSpectrumSelected", 
function (i) {
var isNew = (i != this.iSpectrumSelected);
this.iSpectrumSelected = i;
if (isNew) {
this.getCurrentView ();
}return this.iSpectrumSelected;
}, "~N");
$_M(c$, "closeDialogsExcept", 
function (type) {
if (this.dialogs != null) for (var e, $e = this.dialogs.entrySet ().iterator (); $e.hasNext () && ((e = $e.next ()) || true);) {
var ad = e.getValue ();
if (Clazz_instanceOf (ad, JSV.dialog.JSVDialog) && (type === JSV.common.Annotation.AType.NONE || ad.getAType () !== type)) (ad).setVisible (false);
}
}, "JSV.common.Annotation.AType");
$_M(c$, "dispose", 
function () {
this.spectra = null;
this.viewData = null;
this.viewList = null;
this.annotations = null;
this.lastAnnotation = null;
this.pendingMeasurement = null;
this.imageView = null;
this.graphsTemp = null;
this.widgets = null;
this.disposeImage ();
if (this.dialogs != null) for (var e, $e = this.dialogs.entrySet ().iterator (); $e.hasNext () && ((e = $e.next ()) || true);) {
var ad = e.getValue ();
if (Clazz_instanceOf (ad, JSV.dialog.JSVDialog)) (ad).dispose ();
}
this.dialogs = null;
});
$_M(c$, "isDrawNoSpectra", 
function () {
return (this.iSpectrumSelected == -2147483648);
});
$_M(c$, "getFixedSelectedSpectrumIndex", 
function () {
return Math.max (this.iSpectrumSelected, 0);
});
$_M(c$, "getSpectrum", 
function () {
return this.getSpectrumAt (this.getFixedSelectedSpectrumIndex ()).getCurrentSubSpectrum ();
});
$_M(c$, "getSpectrumAt", 
function (index) {
return this.spectra.get (index);
}, "~N");
$_M(c$, "getSpectrumIndex", 
function (spec) {
for (var i = this.spectra.size (); --i >= 0; ) if (this.spectra.get (i) === spec) return i;

return -1;
}, "JSV.common.JDXSpectrum");
$_M(c$, "addSpec", 
function (spec) {
this.spectra.addLast (spec);
this.nSpectra++;
}, "JSV.common.JDXSpectrum");
$_M(c$, "splitStack", 
function (graphSets, doSplit) {
if (doSplit && this.isSplittable) {
this.nSplit = this.nSpectra;
this.showAllStacked = false;
this.setSpectrumClicked (0);
} else {
this.nSplit = 1;
this.splitPointers[0] = 0;
this.showAllStacked = this.allowStacking && !doSplit;
this.setSpectrumClicked (-1);
}this.stackSelected = false;
JSV.common.GraphSet.setFractionalPositions (this.pd, graphSets, JSV.common.PanelData.LinkMode.NONE);
}, "JU.List,~B");
$_M(c$, "setPositionForFrame", 
function (iSplit) {
var marginalHeight = this.height - 50;
this.xPixel00 = Clazz_doubleToInt (this.width * this.fX0);
this.xPixel11 = Clazz_doubleToInt (this.xPixel00 + this.width * this.fracX - 1);
this.xHArrows = this.xPixel00 + 25;
this.xVArrows = this.xPixel11 - Clazz_doubleToInt (this.right / 2);
this.xPixel0 = this.xPixel00 + Clazz_doubleToInt (this.left * (1 - this.fX0));
this.xPixel10 = this.xPixel1 = this.xPixel11 - this.right;
this.xPixels0 = this.xPixels = this.xPixel1 - this.xPixel0 + 1;
this.yPixel000 = (this.fY0 == 0 ? 25 : 0) + Clazz_doubleToInt (this.height * this.fY0);
this.yPixel00 = this.yPixel000 + Clazz_doubleToInt (marginalHeight * this.fracY * iSplit);
this.yPixel11 = this.yPixel00 + Clazz_doubleToInt (marginalHeight * this.fracY) - 1;
this.yHArrows = this.yPixel11 - 12;
this.yPixel0 = this.yPixel00 + Clazz_doubleToInt (this.top / 2);
this.yPixel1 = this.yPixel11 - Clazz_doubleToInt (this.bottom / 2);
this.yPixels = this.yPixel1 - this.yPixel0 + 1;
if (this.imageView != null && this.is2DSpectrum) {
this.setImageWindow ();
if (this.pd.display1D) {
var widthRatio = (this.pd.display1D ? 1.0 * (this.xPixels0 - this.imageView.xPixels) / this.xPixels0 : 1);
this.xPixels = Clazz_doubleToInt (Math.floor (widthRatio * this.xPixels0 * 0.8));
this.xPixel1 = this.xPixel0 + this.xPixels - 1;
} else {
this.xPixels = 0;
this.xPixel1 = this.imageView.xPixel0 - 30;
}}}, "~N");
$_M(c$, "hasPoint", 
function (xPixel, yPixel) {
return (xPixel >= this.xPixel00 && xPixel <= this.xPixel11 && yPixel >= this.yPixel000 && yPixel <= this.yPixel11 * this.nSplit);
}, "~N,~N");
$_M(c$, "isInPlotRegion", 
function (xPixel, yPixel) {
return (xPixel >= this.xPixel0 && xPixel <= this.xPixel1 && yPixel >= this.yPixel0 && yPixel <= this.yPixel1);
}, "~N,~N");
$_M(c$, "getSplitPoint", 
function (yPixel) {
return Math.min ((Clazz_doubleToInt ((yPixel - this.yPixel000) / (this.yPixel11 - this.yPixel00))), this.nSplit - 1);
}, "~N");
$_M(c$, "isSplitWidget", 
function (xPixel, yPixel) {
return (this.isSplittable && xPixel >= this.xPixel11 - 20 && yPixel >= this.yPixel00 + 1 && xPixel <= this.xPixel11 - 10 && yPixel <= this.yPixel00 + 11);
}, "~N,~N");
$_M(c$, "initGraphSet", 
function (startIndex, endIndex) {
if (JSV.common.GraphSet.veryLightGrey == null) JSV.common.GraphSet.veryLightGrey = this.g2d.getColor3 (200, 200, 200);
this.setPlotColors (JSV.common.ColorParameters.defaultPlotColors);
this.xAxisLeftToRight = this.getSpectrumAt (0).shouldDisplayXAxisIncreasing ();
this.setDrawXAxis ();
var startIndices =  Clazz_newIntArray (this.nSpectra, 0);
var endIndices =  Clazz_newIntArray (this.nSpectra, 0);
this.bsSelected.setBits (0, this.nSpectra);
this.allowStackedYScale = true;
if (endIndex <= 0) endIndex = 2147483647;
this.isSplittable = (this.nSpectra > 1);
this.allowStacking = (this.spectra.get (0).isStackable ());
this.showAllStacked = this.allowStacking && (this.nSpectra > 1);
for (var i = 0; i < this.nSpectra; i++) {
var iLast = this.spectra.get (i).getXYCoords ().length - 1;
startIndices[i] = JSV.common.Coordinate.intoRange (startIndex, 0, iLast);
endIndices[i] = JSV.common.Coordinate.intoRange (endIndex, 0, iLast);
this.allowStackedYScale = new Boolean (this.allowStackedYScale & (this.spectra.get (i).getYUnits ().equals (this.spectra.get (0).getYUnits ()) && this.spectra.get (i).getUserYFactor () == this.spectra.get (0).getUserYFactor ())).valueOf ();
}
this.getView (0, 0, 0, 0, startIndices, endIndices, null, null);
this.viewList =  new JU.List ();
this.viewList.addLast (this.viewData);
}, "~N,~N");
$_M(c$, "getView", 
function (x1, x2, y1, y2, startIndices, endIndices, viewScales, yScales) {
var graphs = (this.graphsTemp.size () == 0 ? this.spectra : this.graphsTemp);
var subspecs = this.getSpectrumAt (0).getSubSpectra ();
var dontUseSubspecs = (subspecs == null || subspecs.size () == 2 && subspecs.get (1).isImaginary ());
var is2D = !this.getSpectrumAt (0).is1D ();
var useFirstSubSpecOnly = false;
if (is2D && useFirstSubSpecOnly || dontUseSubspecs && y1 == y2) {
graphs = this.spectra;
} else if (y1 == y2) {
this.viewData =  new JSV.common.ViewData (subspecs, y1, y2, this.getSpectrum ().isContinuous ());
graphs = null;
}if (graphs != null) {
this.viewData =  new JSV.common.ViewData (graphs, y1, y2, startIndices, endIndices, this.getSpectrumAt (0).isContinuous (), is2D);
if (x1 != x2) this.getScale ().setXRange (x1, x2);
}if (viewScales != null) {
JSV.common.ScaleData.copyScaleFactors (viewScales, this.viewData.getScaleData ());
if (yScales != null) JSV.common.ScaleData.copyYScales (yScales, this.viewData.getScaleData ());
this.getCurrentView ();
}}, "~N,~N,~N,~N,~A,~A,~A,~A");
$_M(c$, "isNearby", 
function (a1, a2, c, range) {
var x = a1.getXVal ();
var xp1 = c.toPixelX (x);
var yp1 = this.toPixelY (a1.getYVal ());
x = a2.getXVal ();
var xp2 = c.toPixelX (x);
var yp2 = this.toPixelY (a2.getYVal ());
return (Math.abs (xp1 - xp2) + Math.abs (yp1 - yp2) < range);
}, "JSV.common.Coordinate,JSV.common.Coordinate,JSV.api.XYScaleConverter,~N");
$_M(c$, "setReversePlot", 
function (val) {
this.reversePlot = val;
if (this.reversePlot) this.closeDialogsExcept (JSV.common.Annotation.AType.NONE);
this.setDrawXAxis ();
}, "~B");
$_M(c$, "setDrawXAxis", 
function () {
this.drawXAxisLeftToRight =  new Boolean (this.xAxisLeftToRight ^ this.reversePlot).valueOf ();
for (var i = 0; i < this.spectra.size (); i++) (this.spectra.get (i)).setExportXAxisDirection (this.drawXAxisLeftToRight);

});
$_M(c$, "fixX", 
function (xPixel) {
return JSV.common.Coordinate.intoRange (xPixel, this.xPixel0, this.xPixel1);
}, "~N");
$_M(c$, "isInTopBar", 
function (xPixel, yPixel) {
return (xPixel == this.fixX (xPixel) && yPixel > this.pin1Dx0.yPixel0 - 2 && yPixel < this.pin1Dx0.yPixel1);
}, "~N,~N");
$_M(c$, "isInTopBar2D", 
function (xPixel, yPixel) {
return (this.imageView != null && xPixel == this.imageView.fixX (xPixel) && yPixel > this.pin2Dx0.yPixel0 - 2 && yPixel < this.pin2Dx0.yPixel1);
}, "~N,~N");
$_M(c$, "isInRightBar", 
function (xPixel, yPixel) {
return (yPixel == this.fixY (yPixel) && xPixel > this.pin1Dy0.xPixel1 && xPixel < this.pin1Dy0.xPixel0 + 2);
}, "~N,~N");
$_M(c$, "isInRightBar2D", 
function (xPixel, yPixel) {
return (this.imageView != null && yPixel == this.fixY (yPixel) && xPixel > this.pin2Dy0.xPixel1 && xPixel < this.pin2Dy0.xPixel0 + 2);
}, "~N,~N");
$_M(c$, "getScale", 
function () {
return this.viewData.getScale ();
});
$_M(c$, "getXPixels", 
function () {
return this.xPixels;
});
$_M(c$, "getXPixel0", 
function () {
return this.xPixel0;
});
$_V(c$, "getYPixels", 
function () {
return this.yPixels;
});
$_V(c$, "toX", 
function (xPixel) {
if (this.imageView != null && this.imageView.isXWithinRange (xPixel)) return this.imageView.toX (xPixel);
return this.getScale ().toX (this.fixX (xPixel), this.xPixel1, this.drawXAxisLeftToRight);
}, "~N");
$_M(c$, "toX0", 
function (xPixel) {
return this.viewList.get (0).getScale ().toX0 (this.fixX (xPixel), this.xPixel0, this.xPixel1, this.drawXAxisLeftToRight);
}, "~N");
$_M(c$, "toPixelX", 
function (dx) {
return this.getScale ().toPixelX (dx, this.xPixel0, this.xPixel1, this.drawXAxisLeftToRight);
}, "~N");
$_M(c$, "toPixelX0", 
function (x) {
return this.viewList.get (0).getScale ().toPixelX0 (x, this.xPixel0, this.xPixel1, this.drawXAxisLeftToRight);
}, "~N");
$_V(c$, "toY", 
function (yPixel) {
return this.getScale ().toY (yPixel, this.yPixel0);
}, "~N");
$_M(c$, "toPixelY", 
function (yVal) {
return this.getScale ().toPixelY (yVal, this.yPixel1);
}, "~N");
$_M(c$, "toY0", 
function (yPixel) {
return this.viewList.get (0).getScale ().toY0 (this.fixY (yPixel), this.yPixel0, this.yPixel1);
}, "~N");
$_M(c$, "toPixelY0", 
function (y) {
return this.fixY (this.viewList.get (0).getScale ().toPixelY0 (y, this.yPixel0, this.yPixel1));
}, "~N");
$_M(c$, "fixY", 
function (yPixel) {
return JSV.common.Coordinate.intoRange (yPixel, this.yPixel0, this.yPixel1);
}, "~N");
$_M(c$, "toPixelYint", 
function (yVal) {
return this.yPixel1 - Clazz_doubleToInt (Double.isNaN (yVal) ? -2147483648 : this.yPixels * yVal);
}, "~N");
$_M(c$, "findAnnotation2D", 
function (xy) {
for (var i = this.annotations.size (); --i >= 0; ) {
var a = this.annotations.get (i);
if (this.isNearby (a, xy, this.imageView, 10)) return a;
}
return null;
}, "JSV.common.Coordinate");
$_M(c$, "addAnnotation", 
function (annotation, isToggle) {
if (this.annotations == null) this.annotations =  new JU.List ();
var removed = false;
for (var i = this.annotations.size (); --i >= 0; ) if (annotation.is2D ? this.isNearby (this.annotations.get (i), annotation, this.imageView, 10) : annotation.equals (this.annotations.get (i))) {
removed = true;
this.annotations.remove (i);
}
if (annotation.text.length > 0 && (!removed || !isToggle)) this.annotations.addLast (annotation);
}, "JSV.common.Annotation,~B");
$_M(c$, "setImageWindow", 
function () {
this.imageView.setPixelWidthHeight (Clazz_doubleToInt ((this.pd.display1D ? 0.6 : 1) * this.xPixels0), this.yPixels);
this.imageView.setXY0 (this.getSpectrumAt (0), Clazz_doubleToInt (Math.floor (this.xPixel10 - this.imageView.xPixels)), this.yPixel0);
});
$_M(c$, "findNearestMaxMin", 
function () {
if (this.nSpectra > 1 && this.iSpectrumClicked < 0) return false;
this.xValueMovedTo = this.getSpectrum ().findXForPeakNearest (this.xValueMovedTo);
this.setXPixelMovedTo (this.xValueMovedTo, 1.7976931348623157E308, 0, 0);
return true;
});
$_M(c$, "setXPixelMovedTo", 
function (x1, x2, xPixel1, xPixel2) {
if (x1 == 1.7976931348623157E308 && x2 == 1.7976931348623157E308) {
this.xPixelMovedTo = xPixel1;
this.xPixelMovedTo2 = xPixel2;
if (this.isLinked && this.sticky2Dcursor) {
this.pd.setlinkedXMove (this, this.toX (this.xPixelMovedTo), false);
}return;
}if (x1 != 1.7976931348623157E308) {
this.xPixelMovedTo = this.toPixelX (x1);
if (this.fixX (this.xPixelMovedTo) != this.xPixelMovedTo) this.xPixelMovedTo = -1;
this.xPixelMovedTo2 = -1;
if (x1 != 1e10) this.setSpectrumClicked (this.getFixedSelectedSpectrumIndex ());
}if (x2 != 1.7976931348623157E308) {
this.xPixelMovedTo2 = this.toPixelX (x2);
}}, "~N,~N,~N,~N");
$_M(c$, "processPendingMeasurement", 
function (xPixel, yPixel, clickCount) {
if (!this.isInPlotRegion (xPixel, yPixel)) {
this.pendingMeasurement = null;
return;
}var x = this.toX (xPixel);
var y = this.toY (yPixel);
var x0 = x;
var m;
switch (clickCount) {
case 0:
this.pendingMeasurement.setPt2 (this.toX (xPixel), this.toY (yPixel));
break;
case 3:
case 2:
if (this.iSpectrumClicked < 0) return;
var spec = this.spectra.get (this.iSpectrumClicked);
this.setScale (this.iSpectrumClicked);
if (clickCount == 3) {
} else {
m = this.findMeasurement (this.selectedSpectrumMeasurements, xPixel, yPixel, 1);
if (m != null) {
x = m.getXVal ();
y = m.getYVal ();
} else if ((m = this.findMeasurement (this.selectedSpectrumMeasurements, xPixel, yPixel, 2)) != null) {
x = m.getXVal2 ();
y = m.getYVal2 ();
} else {
x = this.getNearestPeak (spec, x, y);
}}this.pendingMeasurement =  new JSV.common.Measurement ().setM1 (x, y, spec);
this.pendingMeasurement.setPt2 (x0, y);
this.pd.repaint ();
break;
case 1:
case -2:
case -3:
if (this.pendingMeasurement == null) return;
this.setScale (this.getSpectrumIndex (this.pendingMeasurement.spec));
if (clickCount != 3 && this.findNearestMaxMin ()) {
xPixel = this.xPixelMovedTo;
}x = this.toX (xPixel);
y = this.toY (yPixel);
this.pendingMeasurement.setPt2 (x, y);
if (this.pendingMeasurement.text.length == 0) {
this.pendingMeasurement = null;
} else {
this.setMeasurement (this.pendingMeasurement);
if (clickCount == 1) {
this.setSpectrumClicked (this.getSpectrumIndex (this.pendingMeasurement.spec));
this.pendingMeasurement =  new JSV.common.Measurement ().setM1 (x, y, this.pendingMeasurement.spec);
} else {
this.pendingMeasurement = null;
}}this.pd.repaint ();
break;
case 5:
if (this.findNearestMaxMin ()) {
var iSpec = this.getFixedSelectedSpectrumIndex ();
if (Double.isNaN (this.lastXMax) || this.lastSpecClicked != iSpec || this.pendingMeasurement == null) {
this.lastXMax = this.xValueMovedTo;
this.lastSpecClicked = iSpec;
this.pendingMeasurement =  new JSV.common.Measurement ().setM1 (this.xValueMovedTo, this.yValueMovedTo, this.spectra.get (iSpec));
} else {
this.pendingMeasurement.setPt2 (this.xValueMovedTo, this.yValueMovedTo);
if (this.pendingMeasurement.text.length > 0) this.setMeasurement (this.pendingMeasurement);
this.pendingMeasurement = null;
this.lastXMax = NaN;
}} else {
this.lastXMax = NaN;
}break;
}
}, "~N,~N,~N");
$_M(c$, "checkIntegralNormalizationClick", 
function (xPixel, yPixel) {
if (this.selectedSpectrumIntegrals == null) return false;
var integral = this.findMeasurement (this.selectedSpectrumIntegrals, xPixel, yPixel, -5);
if (integral == null) return false;
this.selectedIntegral = integral;
this.pd.normalizeIntegral ();
this.updateDialog (JSV.common.Annotation.AType.Integration, -1);
this.setSpectrumClicked (this.getSpectrumIndex (integral.spec));
return true;
}, "~N,~N");
$_M(c$, "getNearestPeak", 
function (spec, x, y) {
var x0 = JSV.common.Coordinate.getNearestXWithYAbove (spec.getXYCoords (), x, y, spec.isInverted (), false);
var x1 = JSV.common.Coordinate.getNearestXWithYAbove (spec.getXYCoords (), x, y, spec.isInverted (), true);
return (Double.isNaN (x0) ? x1 : Double.isNaN (x1) ? x0 : Math.abs (x0 - x) < Math.abs (x1 - x) ? x0 : x1);
}, "JSV.common.JDXSpectrum,~N,~N");
$_M(c$, "findMeasurement", 
function (measurements, xPixel, yPixel, iPt) {
if (measurements == null || measurements.size () == 0) return null;
if (iPt == 0) {
var m = this.findMeasurement (measurements, xPixel, yPixel, -1);
if (m != null || Clazz_instanceOf (measurements.get (0), JSV.common.Integral)) return m;
return this.findMeasurement (measurements, xPixel, yPixel, -2);
}for (var i = measurements.size (); --i >= 0; ) {
var m = measurements.get (i);
var x1;
var x2;
var y1;
var y2;
if (Clazz_instanceOf (m, JSV.common.Integral)) {
x1 = x2 = this.toPixelX (m.getXVal2 ());
y1 = this.toPixelYint (m.getYVal ());
y2 = this.toPixelYint (m.getYVal2 ());
} else {
x1 = this.toPixelX (m.getXVal ());
x2 = this.toPixelX (m.getXVal2 ());
y1 = y2 = (iPt == -2 ? this.yPixel1 - 2 : this.toPixelY (m.getYVal ()));
}switch (iPt) {
case 1:
if (Math.abs (xPixel - x1) + Math.abs (yPixel - y1) < 4) return m;
break;
case 2:
if (Math.abs (xPixel - x2) + Math.abs (yPixel - y2) < 4) return m;
break;
case -5:
y1 = y2 = Clazz_doubleToInt ((y1 + y2) / 2);
x2 = x1 + 20;
default:
if (JSV.common.GraphSet.isOnLine (xPixel, yPixel, x1, y1, x2, y2)) return m;
break;
}
}
return null;
}, "JSV.common.MeasurementData,~N,~N,~N");
$_M(c$, "setMeasurement", 
function (m) {
var iSpec = this.getSpectrumIndex (m.spec);
var ad = this.getDialog (JSV.common.Annotation.AType.Measurements, iSpec);
if (ad == null) this.addDialog (iSpec, JSV.common.Annotation.AType.Measurements, ad =  new JSV.common.MeasurementData (JSV.common.Annotation.AType.Measurements, m.spec));
ad.getData ().addLast (m.copyM ());
this.updateDialog (JSV.common.Annotation.AType.Measurements, -1);
}, "JSV.common.Measurement");
$_M(c$, "checkArrowUpDownClick", 
function (xPixel, yPixel) {
var ok = false;
var f = (this.isArrowClick (xPixel, yPixel, JSV.common.GraphSet.ArrowType.UP) ? JSV.common.GraphSet.RT2 : this.isArrowClick (xPixel, yPixel, JSV.common.GraphSet.ArrowType.DOWN) ? 1 / JSV.common.GraphSet.RT2 : 0);
if (f != 0) {
if (this.nSplit > 1) this.setSpectrumSelected (this.iSpectrumMovedTo);
if ((this.nSpectra == 1 || this.iSpectrumSelected >= 0) && this.spectra.get (this.getFixedSelectedSpectrumIndex ()).isTransmittance ()) f = 1 / f;
this.viewData.scaleSpectrum (this.imageView == null ? this.iSpectrumSelected : -2, f);
ok = true;
} else if (this.isArrowClick (xPixel, yPixel, JSV.common.GraphSet.ArrowType.RESET)) {
this.clearViews ();
if (this.showAllStacked && !this.stackSelected) this.closeDialogsExcept (JSV.common.Annotation.AType.NONE);
this.viewData.resetScaleFactors ();
this.updateDialogs ();
ok = true;
}if (ok) {
if (this.imageView != null) {
this.update2dImage (false);
this.resetPinsFromView ();
}this.pd.taintedAll = true;
}return ok;
}, "~N,~N");
$_M(c$, "checkArrowLeftRightClick", 
function (xPixel, yPixel) {
if (this.haveLeftRightArrows) {
var dx = (this.isArrowClick (xPixel, yPixel, JSV.common.GraphSet.ArrowType.LEFT) ? -1 : this.isArrowClick (xPixel, yPixel, JSV.common.GraphSet.ArrowType.RIGHT) ? 1 : 0);
if (dx != 0) {
this.setSpectrumClicked ((this.iSpectrumSelected + dx) % this.nSpectra);
return true;
}if (this.isArrowClick (xPixel, yPixel, JSV.common.GraphSet.ArrowType.HOME)) {
if (this.showAllStacked) {
this.showAllStacked = false;
this.setSpectrumClicked (this.getFixedSelectedSpectrumIndex ());
} else {
this.showAllStacked = this.allowStacking;
this.setSpectrumSelected (-1);
this.stackSelected = false;
}return true;
}}return false;
}, "~N,~N");
$_M(c$, "isArrowClick", 
function (xPixel, yPixel, type) {
var pt;
switch (type) {
case JSV.common.GraphSet.ArrowType.UP:
case JSV.common.GraphSet.ArrowType.DOWN:
case JSV.common.GraphSet.ArrowType.RESET:
pt = Clazz_doubleToInt ((this.yPixel00 + this.yPixel11) / 2) + (type === JSV.common.GraphSet.ArrowType.UP ? -1 : type === JSV.common.GraphSet.ArrowType.DOWN ? 1 : 0) * 15;
return (Math.abs (this.xVArrows - xPixel) < 10 && Math.abs (pt - yPixel) < 10);
case JSV.common.GraphSet.ArrowType.LEFT:
case JSV.common.GraphSet.ArrowType.RIGHT:
case JSV.common.GraphSet.ArrowType.HOME:
pt = this.xHArrows + (type === JSV.common.GraphSet.ArrowType.LEFT ? -1 : type === JSV.common.GraphSet.ArrowType.RIGHT ? 1 : 0) * 15;
return (Math.abs (pt - xPixel) < 10 && Math.abs (this.yHArrows - yPixel) < 10);
}
return false;
}, "~N,~N,JSV.common.GraphSet.ArrowType");
$_M(c$, "setWidgetValueByUser", 
function (pw) {
var sval;
if (pw === this.cur2Dy) sval = "" + this.imageView.toSubspectrumIndex (pw.yPixel0);
 else if (pw === this.pin1Dx01) sval = "" + Math.min (this.pin1Dx0.getXVal (), this.pin1Dx1.getXVal ()) + " - " + Math.max (this.pin1Dx0.getXVal (), this.pin1Dx1.getXVal ());
 else if (pw === this.pin1Dy01) sval = "" + Math.min (this.pin1Dy0.getYVal (), this.pin1Dy1.getYVal ()) + " - " + Math.max (this.pin1Dy0.getYVal (), this.pin1Dy1.getYVal ());
 else if (pw === this.pin2Dx01) sval = "" + Math.min (this.pin2Dx0.getXVal (), this.pin2Dx1.getXVal ()) + " - " + Math.max (this.pin2Dx0.getXVal (), this.pin2Dx1.getXVal ());
 else if (pw === this.pin2Dy01) sval = "" + Clazz_doubleToInt (Math.min (this.pin2Dy0.getYVal (), this.pin2Dy1.getYVal ())) + " - " + Clazz_doubleToInt (Math.max (this.pin2Dy0.getYVal (), this.pin2Dy1.getYVal ()));
 else sval = "" + pw.getValue ();
sval = this.pd.getInput ("New value?", "Set Slider", sval);
if (sval == null) return;
sval = sval.trim ();
try {
if (pw === this.pin1Dx01 || pw === this.pin1Dy01 || pw === this.pin2Dx01 || pw === this.pin2Dy01) {
var pt = sval.indexOf ("-", 1);
if (pt < 0) return;
var val1 = Double.$valueOf (sval.substring (0, pt)).doubleValue ();
var val2 = Double.$valueOf (sval.substring (pt + 1)).doubleValue ();
if (pw === this.pin1Dx01) {
this.doZoom (val1, this.pin1Dy0.getYVal (), val2, this.pin1Dy1.getYVal (), true, false, false, true, true);
} else if (pw === this.pin1Dy01) {
this.doZoom (this.pin1Dx0.getXVal (), val1, this.pin1Dx1.getXVal (), val2, this.imageView == null, this.imageView == null, false, false, true);
} else if (pw === this.pin2Dx01) {
this.imageView.setView0 (this.imageView.toPixelX0 (val1), this.pin2Dy0.yPixel0, this.imageView.toPixelX0 (val2), this.pin2Dy1.yPixel0);
this.doZoom (val1, this.pin1Dy0.getYVal (), val2, this.pin1Dy1.getYVal (), false, false, false, true, true);
} else if (pw === this.pin2Dy01) {
this.imageView.setView0 (this.pin2Dx0.xPixel0, this.imageView.toPixelY0 (val1), this.pin2Dx1.xPixel0, this.imageView.toPixelY0 (val2));
this.doZoom (this.imageView.toX (this.imageView.xPixel0), this.getScale ().minY, this.imageView.toX (this.imageView.xPixel0 + this.imageView.xPixels - 1), this.getScale ().maxY, false, false, false, false, true);
}} else {
var val = Double.$valueOf (sval).doubleValue ();
if (pw.isXtype) {
var val2 = (pw === this.pin1Dx0 || pw === this.cur2Dx0 || pw === this.pin2Dx0 ? this.pin1Dx1.getXVal () : this.pin1Dx0.getXVal ());
this.doZoom (val, 0, val2, 0, !pw.is2D, false, false, true, true);
} else if (pw === this.cur2Dy) {
this.setCurrentSubSpectrum (Clazz_doubleToInt (val));
} else if (pw === this.pin2Dy0 || pw === this.pin2Dy1) {
var val2 = (pw === this.pin2Dy0 ? this.pin2Dy1.yPixel0 : this.pin2Dy0.yPixel0);
this.imageView.setView0 (this.pin2Dx0.xPixel0, this.imageView.subIndexToPixelY (Clazz_doubleToInt (val)), this.pin2Dx1.xPixel0, val2);
} else {
var val2 = (pw === this.pin1Dy0 ? this.pin1Dy1.getYVal () : this.pin1Dy0.getYVal ());
this.doZoom (this.pin1Dx0.getXVal (), val, this.pin1Dx1.getXVal (), val2, this.imageView == null, this.imageView == null, false, false, true);
}}} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}, "JSV.common.PlotWidget");
$_M(c$, "removeAllHighlights", 
function (spec) {
if (spec == null) this.highlights.clear ();
 else for (var i = this.highlights.size (); --i >= 0; ) if (this.highlights.get (i).spectrum === spec) this.highlights.remove (i);

}, "JSV.common.JDXSpectrum");
$_M(c$, "setCoordClicked", 
function (xPixel, x, y) {
if (y == 0) this.nextClickForSetPeak = false;
if (Double.isNaN (x)) {
this.pd.coordClicked = null;
this.pd.coordsClicked = null;
return null;
}this.pd.coordClicked =  new JSV.common.Coordinate ().set (this.lastClickX = x, y);
this.pd.coordsClicked = this.getSpectrum ().getXYCoords ();
this.pd.xPixelClicked = (this.lastPixelX = xPixel);
return this.pd.coordClicked;
}, "~N,~N,~N");
$_M(c$, "setWidgets", 
function (needNewPins, subIndex, doDraw1DObjects) {
if (needNewPins || this.pin1Dx0 == null) {
if (this.zoomBox1D == null) this.newPins ();
 else this.resetPinPositions ();
}this.setDerivedPins (subIndex);
this.setPinSliderPositions (doDraw1DObjects);
}, "~B,~N,~B");
$_M(c$, "newPins", 
function () {
this.zoomBox1D =  new JSV.common.PlotWidget ("zoomBox1D");
this.pin1Dx0 =  new JSV.common.PlotWidget ("pin1Dx0");
this.pin1Dx1 =  new JSV.common.PlotWidget ("pin1Dx1");
this.pin1Dy0 =  new JSV.common.PlotWidget ("pin1Dy0");
this.pin1Dy1 =  new JSV.common.PlotWidget ("pin1Dy1");
this.pin1Dx01 =  new JSV.common.PlotWidget ("pin1Dx01");
this.pin1Dy01 =  new JSV.common.PlotWidget ("pin1Dy01");
this.cur1D2x1 =  new JSV.common.PlotWidget ("cur1D2x1");
this.cur1D2x1.color = JSV.common.ScriptToken.PEAKTABCOLOR;
this.cur1D2x2 =  new JSV.common.PlotWidget ("cur1D2x2");
this.cur1D2x2.color = JSV.common.ScriptToken.PEAKTABCOLOR;
if (this.imageView != null) {
this.zoomBox2D =  new JSV.common.PlotWidget ("zoomBox2D");
this.pin2Dx0 =  new JSV.common.PlotWidget ("pin2Dx0");
this.pin2Dx1 =  new JSV.common.PlotWidget ("pin2Dx1");
this.pin2Dy0 =  new JSV.common.PlotWidget ("pin2Dy0");
this.pin2Dy1 =  new JSV.common.PlotWidget ("pin2Dy1");
this.pin2Dx01 =  new JSV.common.PlotWidget ("pin2Dx01");
this.pin2Dy01 =  new JSV.common.PlotWidget ("pin2Dy01");
this.cur2Dx0 =  new JSV.common.PlotWidget ("cur2Dx0");
this.cur2Dx1 =  new JSV.common.PlotWidget ("cur2Dx1");
this.cur2Dy =  new JSV.common.PlotWidget ("cur2Dy");
this.pin2Dy0.setY (0, this.imageView.toPixelY0 (0));
var n = this.getSpectrumAt (0).getSubSpectra ().size ();
this.pin2Dy1.setY (n, this.imageView.toPixelY0 (n));
}this.setWidgetX (this.pin1Dx0, this.getScale ().minX);
this.setWidgetX (this.pin1Dx1, this.getScale ().maxX);
this.setWidgetY (this.pin1Dy0, this.getScale ().minY);
this.setWidgetY (this.pin1Dy1, this.getScale ().maxY);
this.widgets = [this.zoomBox1D, this.zoomBox2D, this.pin1Dx0, this.pin1Dx01, this.pin1Dx1, this.pin1Dy0, this.pin1Dy01, this.pin1Dy1, this.pin2Dx0, this.pin2Dx01, this.pin2Dx1, this.pin2Dy0, this.pin2Dy01, this.pin2Dy1, this.cur2Dx0, this.cur2Dx1, this.cur2Dy, this.cur1D2x1, this.cur1D2x2];
});
$_M(c$, "setWidgetX", 
function (pw, x) {
pw.setX (x, this.toPixelX0 (x));
}, "JSV.common.PlotWidget,~N");
$_M(c$, "setWidgetY", 
function (pw, y) {
pw.setY (y, this.toPixelY0 (y));
}, "JSV.common.PlotWidget,~N");
$_M(c$, "resetPinsFromView", 
function () {
if (this.pin1Dx0 == null) return;
this.setWidgetX (this.pin1Dx0, this.getScale ().minXOnScale);
this.setWidgetX (this.pin1Dx1, this.getScale ().maxXOnScale);
this.setWidgetY (this.pin1Dy0, this.getScale ().minYOnScale);
this.setWidgetY (this.pin1Dy1, this.getScale ().maxYOnScale);
});
$_M(c$, "resetPinPositions", 
function () {
this.resetX (this.pin1Dx0);
this.resetY (this.pin1Dy0);
this.resetY (this.pin1Dy1);
if (this.imageView == null) {
if (this.gs2dLinkedX != null) this.resetX (this.cur1D2x1);
if (this.gs2dLinkedY != null) this.resetX (this.cur1D2x2);
} else {
this.pin2Dy0.setY (this.pin2Dy0.getYVal (), this.imageView.toPixelY0 (this.pin2Dy0.getYVal ()));
this.pin2Dy1.setY (this.pin2Dy1.getYVal (), this.imageView.toPixelY0 (this.pin2Dy1.getYVal ()));
}});
$_M(c$, "resetX", 
function (p) {
this.setWidgetX (p, p.getXVal ());
}, "JSV.common.PlotWidget");
$_M(c$, "resetY", 
function (p) {
this.setWidgetY (p, p.getYVal ());
}, "JSV.common.PlotWidget");
$_M(c$, "setPinSliderPositions", 
function (doDraw1DObjects) {
this.pin1Dx0.yPixel0 = this.pin1Dx1.yPixel0 = this.pin1Dx01.yPixel0 = this.yPixel0 - 5;
this.pin1Dx0.yPixel1 = this.pin1Dx1.yPixel1 = this.pin1Dx01.yPixel1 = this.yPixel0;
this.cur1D2x1.yPixel1 = this.cur1D2x2.yPixel1 = this.yPixel0 - 5;
this.cur1D2x1.yPixel0 = this.cur1D2x2.yPixel0 = this.yPixel1 + 6;
if (this.imageView == null) {
this.pin1Dy0.xPixel0 = this.pin1Dy1.xPixel0 = this.pin1Dy01.xPixel0 = this.xPixel1 + 5;
this.pin1Dy0.xPixel1 = this.pin1Dy1.xPixel1 = this.pin1Dy01.xPixel1 = this.xPixel1;
} else {
this.pin1Dy0.xPixel0 = this.pin1Dy1.xPixel0 = this.pin1Dy01.xPixel0 = this.imageView.xPixel1 + 15;
this.pin1Dy0.xPixel1 = this.pin1Dy1.xPixel1 = this.pin1Dy01.xPixel1 = this.imageView.xPixel1 + 10;
this.pin2Dx0.yPixel0 = this.pin2Dx1.yPixel0 = this.pin2Dx01.yPixel0 = this.yPixel0 - 5;
this.pin2Dx0.yPixel1 = this.pin2Dx1.yPixel1 = this.pin2Dx01.yPixel1 = this.yPixel0;
this.pin2Dy0.xPixel0 = this.pin2Dy1.xPixel0 = this.pin2Dy01.xPixel0 = this.imageView.xPixel1 + 5;
this.pin2Dy0.xPixel1 = this.pin2Dy1.xPixel1 = this.pin2Dy01.xPixel1 = this.imageView.xPixel1;
this.cur2Dx0.yPixel0 = this.cur2Dx1.yPixel0 = this.yPixel1 + 6;
this.cur2Dx0.yPixel1 = this.cur2Dx1.yPixel1 = this.yPixel0 - 5;
this.cur2Dx0.yPixel0 = this.cur2Dx1.yPixel0 = this.yPixel1 + 6;
this.cur2Dx1.yPixel1 = this.cur2Dx1.yPixel1 = this.yPixel0 - 5;
this.cur2Dy.xPixel0 = (doDraw1DObjects ? Clazz_doubleToInt ((this.xPixel1 + this.imageView.xPixel0) / 2) : this.imageView.xPixel0 - 6);
this.cur2Dy.xPixel1 = this.imageView.xPixel1 + 5;
}}, "~B");
$_M(c$, "setDerivedPins", 
function (subIndex) {
this.triggered = true;
if (this.gs2dLinkedX != null) this.cur1D2x1.setX (this.cur1D2x1.getXVal (), this.toPixelX (this.cur1D2x1.getXVal ()));
if (this.gs2dLinkedY != null) this.cur1D2x2.setX (this.cur1D2x2.getXVal (), this.toPixelX (this.cur1D2x2.getXVal ()));
this.pin1Dx01.setX (0, Clazz_doubleToInt ((this.pin1Dx0.xPixel0 + this.pin1Dx1.xPixel0) / 2));
this.pin1Dy01.setY (0, Clazz_doubleToInt ((this.pin1Dy0.yPixel0 + this.pin1Dy1.yPixel0) / 2));
this.pin1Dx01.setEnabled (Math.min (this.pin1Dx0.xPixel0, this.pin1Dx1.xPixel0) > this.xPixel0 || Math.max (this.pin1Dx0.xPixel0, this.pin1Dx1.xPixel0) < this.xPixel1);
this.pin1Dy01.setEnabled (Math.min (this.pin1Dy0.yPixel0, this.pin1Dy1.yPixel0) > Math.min (this.toPixelY (this.getScale ().minY), this.toPixelY (this.getScale ().maxY)) || Math.max (this.pin1Dy0.yPixel0, this.pin1Dy1.yPixel0) < Math.max (this.toPixelY (this.getScale ().minY), this.toPixelY (this.getScale ().maxY)));
if (this.imageView == null) return;
var x = this.pin1Dx0.getXVal ();
this.cur2Dx0.setX (x, this.imageView.toPixelX (x));
x = this.pin1Dx1.getXVal ();
this.cur2Dx1.setX (x, this.imageView.toPixelX (x));
x = this.imageView.toX (this.imageView.xPixel0);
this.pin2Dx0.setX (x, this.imageView.toPixelX0 (x));
x = this.imageView.toX (this.imageView.xPixel1);
this.pin2Dx1.setX (x, this.imageView.toPixelX0 (x));
this.pin2Dx01.setX (0, Clazz_doubleToInt ((this.pin2Dx0.xPixel0 + this.pin2Dx1.xPixel0) / 2));
var y = this.imageView.imageHeight - 1 - this.imageView.yView1;
this.pin2Dy0.setY (y, this.imageView.toPixelY0 (y));
y = this.imageView.imageHeight - 1 - this.imageView.yView2;
this.pin2Dy1.setY (y, this.imageView.toPixelY0 (y));
this.pin2Dy01.setY (0, Clazz_doubleToInt ((this.pin2Dy0.yPixel0 + this.pin2Dy1.yPixel0) / 2));
this.cur2Dy.yPixel0 = this.cur2Dy.yPixel1 = this.imageView.subIndexToPixelY (subIndex);
this.pin2Dx01.setEnabled (Math.min (this.pin2Dx0.xPixel0, this.pin2Dx1.xPixel0) != this.imageView.xPixel0 || Math.max (this.pin2Dx0.xPixel0, this.pin2Dx1.xPixel1) != this.imageView.xPixel1);
this.pin2Dy01.setEnabled (Math.min (this.pin2Dy0.yPixel0, this.pin2Dy1.yPixel0) != this.yPixel0 || Math.max (this.pin2Dy0.yPixel0, this.pin2Dy1.yPixel1) != this.yPixel1);
}, "~N");
$_M(c$, "doZoom", 
function (initX, initY, finalX, finalY, is1D, is1DY, checkRange, checkLinked, addZoom) {
if (initX == finalX) {
initX = this.getScale ().minXOnScale;
finalX = this.getScale ().maxXOnScale;
} else if (this.isLinked && checkLinked) this.pd.doZoomLinked (this, initX, finalX, addZoom, checkRange, is1D);
if (initX > finalX) {
var tempX = initX;
initX = finalX;
finalX = tempX;
}if (initY > finalY) {
var tempY = initY;
initY = finalY;
finalY = tempY;
}var is2DGrayScaleChange = (!is1D && this.imageView != null && (this.imageView.minZ != initY || this.imageView.maxZ != finalY));
if (!this.zoomEnabled && !is2DGrayScaleChange) return;
if (checkRange) {
if (!this.getScale ().isInRangeX (initX) && !this.getScale ().isInRangeX (finalX)) return;
if (!this.getScale ().isInRangeX (initX)) {
initX = this.getScale ().minX;
} else if (!this.getScale ().isInRangeX (finalX)) {
finalX = this.getScale ().maxX;
}} else {
}this.pd.taintedAll = true;
var viewScales = this.viewData.getScaleData ();
var startIndices =  Clazz_newIntArray (this.nSpectra, 0);
var endIndices =  Clazz_newIntArray (this.nSpectra, 0);
this.graphsTemp.clear ();
var subspecs = this.getSpectrumAt (0).getSubSpectra ();
var dontUseSubspecs = (subspecs == null || subspecs.size () == 2);
var is2D = !this.getSpectrumAt (0).is1D ();
if (!is2D && !dontUseSubspecs) {
this.graphsTemp.addLast (this.getSpectrum ());
if (!JSV.common.ScaleData.setDataPointIndices (this.graphsTemp, initX, finalX, 3, startIndices, endIndices)) return;
} else {
if (!JSV.common.ScaleData.setDataPointIndices (this.spectra, initX, finalX, 3, startIndices, endIndices)) return;
}var y1 = initY;
var y2 = finalY;
var isXOnly = (y1 == y2);
if (isXOnly) {
var f = (!is2DGrayScaleChange && is1D ? f = this.getScale ().spectrumScaleFactor : 1);
if (Math.abs (f - 1) < 0.0001) {
y1 = this.getScale ().minYOnScale;
y2 = this.getScale ().maxYOnScale;
}}var yScales = null;
if (isXOnly || is1DY) {
this.getCurrentView ();
yScales = this.viewData.getNewScales (this.iSpectrumSelected, isXOnly, y1, y2);
}this.getView (initX, finalX, y1, y2, startIndices, endIndices, viewScales, yScales);
this.setXPixelMovedTo (1E10, 1.7976931348623157E308, 0, 0);
this.setWidgetX (this.pin1Dx0, initX);
this.setWidgetX (this.pin1Dx1, finalX);
this.setWidgetY (this.pin1Dy0, y1);
this.setWidgetY (this.pin1Dy1, y2);
if (this.imageView == null) {
this.updateDialogs ();
} else {
var isub = this.getSpectrumAt (0).getSubIndex ();
var ifix = this.imageView.fixSubIndex (isub);
if (ifix != isub) this.setCurrentSubSpectrum (ifix);
if (is2DGrayScaleChange) this.update2dImage (false);
}if (addZoom) this.addCurrentZoom ();
}, "~N,~N,~N,~N,~B,~B,~B,~B,~B");
$_M(c$, "updateDialogs", 
function () {
this.updateDialog (JSV.common.Annotation.AType.PeakList, -1);
this.updateDialog (JSV.common.Annotation.AType.Measurements, -1);
});
$_M(c$, "setCurrentSubSpectrum", 
function (i) {
var spec0 = this.getSpectrumAt (0);
i = spec0.setCurrentSubSpectrum (i);
if (spec0.isForcedSubset ()) this.viewData.setXRangeForSubSpectrum (this.getSpectrum ().getXYCoords ());
this.pd.notifySubSpectrumChange (i, this.getSpectrum ());
}, "~N");
$_M(c$, "addCurrentZoom", 
function () {
if (this.viewList.size () > this.currentZoomIndex + 1) for (var i = this.viewList.size () - 1; i > this.currentZoomIndex; i--) this.viewList.remove (i);

this.viewList.addLast (this.viewData);
this.currentZoomIndex++;
});
$_M(c$, "setZoomTo", 
function (i) {
this.currentZoomIndex = i;
this.viewData = this.viewList.get (i);
this.resetPinsFromView ();
}, "~N");
$_M(c$, "clearViews", 
function () {
if (this.isLinked) {
this.pd.clearLinkViews (this);
}this.setZoom (0, 0, 0, 0);
for (var i = this.viewList.size (); --i >= 1; ) this.viewList.remove (i);

});
$_M(c$, "drawAll", 
function (gMain, gTop, iSplit, needNewPins, doAll) {
this.g2d = this.pd.g2d;
this.gMain = gMain;
var subIndex = this.getSpectrumAt (0).getSubIndex ();
this.is2DSpectrum = (!this.getSpectrumAt (0).is1D () && (this.isLinked || this.pd.getBoolean (JSV.common.ScriptToken.DISPLAY2D)) && (this.imageView != null || this.get2DImage ()));
if (this.imageView != null && doAll) {
if (this.pd.isPrinting && this.g2d !== this.pd.g2d0) this.g2d.newGrayScaleImage (gMain, this.image2D, this.imageView.imageWidth, this.imageView.imageHeight, this.imageView.getBuffer ());
if (this.is2DSpectrum) this.setPositionForFrame (iSplit);
this.draw2DImage ();
}var iSelected = (this.stackSelected || !this.showAllStacked ? this.iSpectrumSelected : -1);
var doYScale = (!this.showAllStacked || this.nSpectra == 1 || iSelected >= 0);
var doDraw1DObjects = (this.imageView == null || this.pd.display1D);
var n = (iSelected >= 0 ? 1 : 0);
var iSpectrumForScale = this.getFixedSelectedSpectrumIndex ();
if (doDraw1DObjects && doAll) {
this.fillBox (gMain, this.xPixel0, this.yPixel0, this.xPixel1, this.yPixel1, JSV.common.ScriptToken.PLOTAREACOLOR);
if (iSelected < 0) {
doYScale = true;
for (var i = 0; i < this.nSpectra; i++) if (this.doPlot (i, iSplit)) {
if (n++ == 0) continue;
doYScale = new Boolean (doYScale & this.viewData.areYScalesSame (i - 1, i)).valueOf ();
}
}}var iSpecForFrame = (this.nSpectra == 1 ? 0 : !this.showAllStacked ? this.iSpectrumMovedTo : this.iSpectrumSelected);
if (doAll) {
var addCurrentBox = (!this.isLinked && (this.isSplittable) && (!this.isSplittable || this.nSplit == 1 || this.pd.currentSplitPoint == iSplit));
var drawUpDownArrows = (this.pd.isCurrentGraphSet (this) && this.zoomEnabled && this.spectra.get (0).isScalable () && (addCurrentBox || this.nSpectra == 1) && (this.nSplit == 1 || this.pd.currentSplitPoint == this.iSpectrumMovedTo) && !this.isDrawNoSpectra ());
var addSplitBox = this.isSplittable;
this.drawFrame (gMain, iSpecForFrame, addCurrentBox, addSplitBox, drawUpDownArrows);
}if (this.pd.isCurrentGraphSet (this) && iSplit == this.pd.currentSplitPoint && (n < 2 || this.iSpectrumSelected >= 0)) this.$haveSelectedSpectrum = true;
this.haveSingleYScale = (this.showAllStacked && this.nSpectra > 1 ? this.allowStackedYScale && doYScale : true);
if (doDraw1DObjects) {
var yOffsetPixels = Clazz_floatToInt (this.yPixels * (this.yStackOffsetPercent / 100));
this.haveLeftRightArrows = false;
for (var i = 0, offset = 0; i < this.nSpectra; i++) {
if (!this.doPlot (i, iSplit)) continue;
var isGrey = (this.stackSelected && this.iSpectrumSelected >= 0 && this.iSpectrumSelected != i);
var ig = (!this.reversePlot && this.getShowAnnotation (JSV.common.Annotation.AType.Integration, i) && (!this.showAllStacked || this.iSpectrumSelected == i) ? this.getDialog (JSV.common.Annotation.AType.Integration, i).getData () : null);
this.setScale (i);
var spec = this.spectra.get (i);
if (this.nSplit > 1) {
iSpectrumForScale = i;
}var doDrawWidgets = !isGrey && (this.nSplit == 1 || this.showAllStacked || this.iSpectrumSelected == iSplit);
var doDraw1DY = (doDrawWidgets && this.$haveSelectedSpectrum && i == iSpectrumForScale);
if (doDrawWidgets) {
this.resetPinsFromView ();
this.drawWidgets (gTop, subIndex, needNewPins, doDraw1DObjects, doDraw1DY, false);
}if (this.haveSingleYScale && i == iSpectrumForScale && doAll) {
this.drawGrid (gMain);
if (this.pd.isPrinting && this.nSplit > 1) this.drawSpectrumSource (gMain, i);
}if (doDrawWidgets) this.drawWidgets (gTop, subIndex, false, doDraw1DObjects, doDraw1DY, true);
if (this.haveSingleYScale && !this.isDrawNoSpectra () && i == iSpectrumForScale && (this.nSpectra == 1 || this.iSpectrumSelected >= 0)) this.drawHighlightsAndPeakTabs (gTop, i);
if (doAll) {
if (n == 1 && this.iSpectrumSelected < 0 || this.iSpectrumSelected == i && this.pd.isCurrentGraphSet (this)) {
if (this.pd.titleOn && !this.pd.titleDrawn) {
this.pd.drawTitle (gMain, this.height, this.width, this.pd.getDrawTitle (this.pd.isPrinting));
this.pd.titleDrawn = true;
}}if (this.haveSingleYScale && i == iSpectrumForScale) {
if (this.pd.getBoolean (JSV.common.ScriptToken.YSCALEON)) this.drawYScale (gMain, this);
if (this.pd.getBoolean (JSV.common.ScriptToken.YUNITSON)) this.drawYUnits (gMain);
}this.drawSpectrum (gMain, i, offset, isGrey, ig);
}this.drawMeasurements (gTop, i);
if (this.pendingMeasurement != null && this.pendingMeasurement.spec === spec) this.drawMeasurement (gTop, this.pendingMeasurement);
if ((this.nSplit > 1 ? i == this.iSpectrumMovedTo : this.isLinked || i == iSpectrumForScale) && !this.pd.isPrinting && this.xPixelMovedTo >= 0 && spec.isContinuous ()) {
this.drawSpectrumPointer (gTop, spec, ig);
}if (this.nSpectra > 1 && this.nSplit == 1 && this.pd.isCurrentGraphSet (this) && doAll) {
this.haveLeftRightArrows = true;
if (!this.pd.isPrinting) {
this.setScale (0);
iSpecForFrame = (this.iSpectrumSelected);
if (this.nSpectra != 2) {
this.setPlotColor (gMain, (iSpecForFrame + this.nSpectra - 1) % this.nSpectra);
this.fillArrow (gMain, JSV.common.GraphSet.ArrowType.LEFT, this.yHArrows, this.xHArrows - 9, true);
this.setCurrentBoxColor (gMain);
this.fillArrow (gMain, JSV.common.GraphSet.ArrowType.LEFT, this.yHArrows, this.xHArrows - 9, false);
}if (iSpecForFrame >= 0) {
this.setPlotColor (gMain, iSpecForFrame);
this.fillCircle (gMain, this.xHArrows, this.yHArrows, true);
}this.setCurrentBoxColor (gMain);
this.fillCircle (gMain, this.xHArrows, this.yHArrows, false);
this.setPlotColor (gMain, (iSpecForFrame + 1) % this.nSpectra);
this.fillArrow (gMain, JSV.common.GraphSet.ArrowType.RIGHT, this.yHArrows, this.xHArrows + 9, true);
this.setCurrentBoxColor (gMain);
this.fillArrow (gMain, JSV.common.GraphSet.ArrowType.RIGHT, this.yHArrows, this.xHArrows + 9, false);
}}offset -= yOffsetPixels;
}
if (doAll) {
if (this.pd.getBoolean (JSV.common.ScriptToken.XSCALEON)) this.drawXScale (gMain, this);
if (this.pd.getBoolean (JSV.common.ScriptToken.XUNITSON)) this.drawXUnits (gMain);
}} else {
if (doAll) {
if (this.pd.getBoolean (JSV.common.ScriptToken.XSCALEON)) this.drawXScale (gMain, this.imageView);
if (this.pd.getBoolean (JSV.common.ScriptToken.YSCALEON)) this.drawYScale (gMain, this.imageView);
if (subIndex >= 0) this.draw2DUnits (gMain);
}this.drawWidgets (gTop, subIndex, needNewPins, doDraw1DObjects, true, false);
this.drawWidgets (gTop, subIndex, needNewPins, doDraw1DObjects, true, true);
}if (this.annotations != null) this.drawAnnotations (gTop, this.annotations, null);
}, "~O,~O,~N,~B,~B");
$_M(c$, "drawSpectrumSource", 
function (g, i) {
this.pd.printFilePath (g, this.pd.thisWidth - this.pd.right, this.yPixel0, this.spectra.get (i).getFilePath ());
}, "~O,~N");
$_M(c$, "doPlot", 
function (i, iSplit) {
var isGrey = (this.stackSelected && this.iSpectrumSelected >= 0 && this.iSpectrumSelected != i);
var ok = (this.showAllStacked || this.iSpectrumSelected == -1 || this.iSpectrumSelected == i);
return (this.nSplit > 1 ? i == iSplit : ok && (!this.pd.isPrinting || !isGrey));
}, "~N,~N");
$_M(c$, "drawSpectrumPointer", 
function (g, spec, ig) {
this.setColorFromToken (g, JSV.common.ScriptToken.PEAKTABCOLOR);
var iHandle = this.pd.integralShiftMode;
if (ig != null) {
if ((!this.pd.ctrlPressed || this.pd.isIntegralDrag) && !this.isOnSpectrum (this.pd.mouseX, this.pd.mouseY, -1)) ig = null;
 else if (iHandle == 0) iHandle = this.getShiftMode (this.pd.mouseX, this.pd.mouseY);
}var y0 = this.yValueMovedTo;
this.yValueMovedTo = (ig == null ? spec.getYValueAt (this.xValueMovedTo) : ig.getPercentYValueAt (this.xValueMovedTo));
this.setCoordStr (this.xValueMovedTo, this.yValueMovedTo);
if (iHandle != 0) {
this.setPlotColor (g, 0);
var x = (iHandle < 0 ? this.xPixelPlot1 : this.xPixelPlot0);
var y = (iHandle < 0 ? this.yPixelPlot0 : this.yPixelPlot1);
this.drawHandle (g, x, y, false);
return;
}if (ig != null) this.g2d.setStrokeBold (g, true);
if (Double.isNaN (y0) || this.pendingMeasurement != null) {
this.g2d.drawLine (g, this.xPixelMovedTo, this.yPixel0, this.xPixelMovedTo, this.yPixel1);
if (this.xPixelMovedTo2 >= 0) this.g2d.drawLine (g, this.xPixelMovedTo2, this.yPixel0, this.xPixelMovedTo2, this.yPixel1);
this.yValueMovedTo = NaN;
} else {
var y = (ig == null ? this.toPixelY (this.yValueMovedTo) : this.toPixelYint (this.yValueMovedTo / 100));
if (y == this.fixY (y)) this.g2d.drawLine (g, this.xPixelMovedTo, y - 10, this.xPixelMovedTo, y + 10);
}if (ig != null) this.g2d.setStrokeBold (g, false);
}, "~O,JSV.common.JDXSpectrum,JSV.common.IntegralData");
$_M(c$, "setScale", 
function (i) {
this.viewData.setScale (i, this.xPixels, this.yPixels, this.spectra.get (i).isInverted ());
}, "~N");
$_M(c$, "draw2DUnits", 
function (g) {
var nucleusX = this.getSpectrumAt (0).nucleusX;
var nucleusY = this.getSpectrumAt (0).nucleusY;
this.setColorFromToken (g, JSV.common.ScriptToken.PLOTCOLOR);
this.drawUnits (g, nucleusX, this.imageView.xPixel1 + 5 * this.pd.scalingFactor, this.yPixel1, 1, 1.0);
this.drawUnits (g, nucleusY, this.imageView.xPixel0 - 5 * this.pd.scalingFactor, this.yPixel0, 1, 0);
}, "~O");
$_M(c$, "drawPeakTabs", 
function (g, spec) {
var list = (this.nSpectra == 1 || this.iSpectrumSelected >= 0 ? spec.getPeakList () : null);
if (list != null && list.size () > 0) {
if (this.piMouseOver != null && this.piMouseOver.spectrum === spec && this.pd.isMouseUp ()) {
this.g2d.setGraphicsColor (g, this.g2d.getColor4 (240, 240, 240, 140));
this.drawPeak (g, this.piMouseOver, true);
spec.setHighlightedPeak (this.piMouseOver);
} else {
spec.setHighlightedPeak (null);
}this.setColorFromToken (g, JSV.common.ScriptToken.PEAKTABCOLOR);
for (var i = list.size (); --i >= 0; ) {
this.drawPeak (g, list.get (i), false);
}
}}, "~O,JSV.common.JDXSpectrum");
$_M(c$, "drawPeak", 
function (g, pi, isFull) {
if (this.pd.isPrinting) return;
var xMin = pi.getXMin ();
var xMax = pi.getXMax ();
if (xMin != xMax) {
this.drawBar (g, pi, xMin, xMax, null, isFull);
}}, "~O,JSV.common.PeakInfo,~B");
$_M(c$, "drawWidgets", 
function (g, subIndex, needNewPins, doDraw1DObjects, doDraw1DY, postGrid) {
this.setWidgets (needNewPins, subIndex, doDraw1DObjects);
if (this.pd.isPrinting && (this.imageView == null ? !this.cur1D2Locked : this.sticky2Dcursor)) return;
if (!this.pd.isPrinting && !postGrid) {
if (doDraw1DObjects) {
this.fillBox (g, this.xPixel0, this.pin1Dx0.yPixel1, this.xPixel1, this.pin1Dx1.yPixel1 + 2, JSV.common.ScriptToken.GRIDCOLOR);
this.fillBox (g, this.pin1Dx0.xPixel0, this.pin1Dx0.yPixel1, this.pin1Dx1.xPixel0, this.pin1Dx1.yPixel1 + 2, JSV.common.ScriptToken.PLOTCOLOR);
} else {
this.fillBox (g, this.imageView.xPixel0, this.pin2Dx0.yPixel1, this.imageView.xPixel1, this.pin2Dx0.yPixel1 + 2, JSV.common.ScriptToken.GRIDCOLOR);
this.fillBox (g, this.pin2Dx0.xPixel0, this.pin2Dx0.yPixel1, this.pin2Dx1.xPixel0, this.pin2Dx1.yPixel1 + 2, JSV.common.ScriptToken.PLOTCOLOR);
this.fillBox (g, this.pin2Dy0.xPixel1, this.yPixel1, this.pin2Dy1.xPixel1 + 2, this.yPixel0, JSV.common.ScriptToken.GRIDCOLOR);
this.fillBox (g, this.pin2Dy0.xPixel1, this.pin2Dy0.yPixel1, this.pin2Dy1.xPixel1 + 2, this.pin2Dy1.yPixel0, JSV.common.ScriptToken.PLOTCOLOR);
}this.fillBox (g, this.pin1Dy0.xPixel1, this.yPixel1, this.pin1Dy1.xPixel1 + 2, this.yPixel0, JSV.common.ScriptToken.GRIDCOLOR);
if (doDraw1DY) this.fillBox (g, this.pin1Dy0.xPixel1, this.pin1Dy0.yPixel1, this.pin1Dy1.xPixel1 + 2, this.pin1Dy1.yPixel0, JSV.common.ScriptToken.PLOTCOLOR);
}for (var i = 0; i < this.widgets.length; i++) {
var pw = this.widgets[i];
if (pw == null || !pw.isPinOrCursor && !this.zoomEnabled) continue;
var isLockedCursor = (pw === this.cur1D2x1 || pw === this.cur1D2x2 || pw === this.cur2Dx0 || pw === this.cur2Dx1 || pw === this.cur2Dy);
if ((pw.isPin || !pw.isPinOrCursor) == postGrid) continue;
if (pw.is2D) {
if (pw === this.cur2Dx0 && !doDraw1DObjects) continue;
} else {
var isPin1Dy = (pw === this.pin1Dy0 || pw === this.pin1Dy1 || pw === this.pin1Dy01);
if ((this.imageView != null && doDraw1DObjects == isPin1Dy) || isPin1Dy && !doDraw1DY || pw === this.cur1D2x1 && this.gs2dLinkedX == null || pw === this.cur1D2x2 && this.gs2dLinkedY == null || pw === this.zoomBox1D && (this.pd.isIntegralDrag || this.pd.integralShiftMode != 0)) {
if (!this.isLinked || this.imageView != null) continue;
}}if (this.pd.isPrinting && !isLockedCursor) continue;
if (pw.isPinOrCursor) {
this.setColorFromToken (g, pw.color);
this.g2d.drawLine (g, pw.xPixel0, pw.yPixel0, pw.xPixel1, pw.yPixel1);
pw.isVisible = true;
if (pw.isPin) this.drawHandle (g, pw.xPixel0, pw.yPixel0, !pw.isEnabled);
} else if (pw.xPixel1 != pw.xPixel0) {
this.fillBox (g, pw.xPixel0, pw.yPixel0, pw.xPixel1, pw.yPixel1, pw === this.zoomBox1D && this.pd.shiftPressed ? JSV.common.ScriptToken.ZOOMBOXCOLOR2 : JSV.common.ScriptToken.ZOOMBOXCOLOR);
}}
}, "~O,~N,~B,~B,~B,~B");
$_M(c$, "drawBar", 
function (g, pi, startX, endX, whatColor, isFullHeight) {
var x1 = this.toPixelX (startX);
var x2 = this.toPixelX (endX);
if (x1 > x2) {
var tmp = x1;
x1 = x2;
x2 = tmp;
}x1 = this.fixX (x1);
x2 = this.fixX (x2);
if (x2 - x1 < 3) {
x1 -= 2;
x2 += 2;
}if (pi != null) pi.setPixelRange (x1, x2);
this.fillBox (g, x1, this.yPixel0, x2, this.yPixel0 + (isFullHeight ? this.yPixels : 5), whatColor);
if (pi != null && !isFullHeight) {
x1 = Clazz_doubleToInt ((x1 + x2) / 2);
this.fillBox (g, x1 - 1, this.yPixel0, x1 + 1, this.yPixel0 + 7, whatColor);
}}, "~O,JSV.common.PeakInfo,~N,~N,JSV.common.ScriptToken,~B");
$_M(c$, "drawSpectrum", 
function (g, index, yOffset, isGrey, ig) {
var spec = this.spectra.get (index);
this.drawPlot (g, index, spec.getXYCoords (), spec.isContinuous (), yOffset, isGrey, null);
if (ig != null) {
if (this.haveIntegralDisplayed (index)) this.drawPlot (g, index, this.getIntegrationGraph (index).getXYCoords (), true, yOffset, false, ig);
this.drawIntegralValues (g, index, yOffset);
}if (this.getIntegrationRatios (index) != null) this.drawAnnotations (g, this.getIntegrationRatios (index), JSV.common.ScriptToken.INTEGRALPLOTCOLOR);
}, "~O,~N,~N,~B,JSV.common.IntegralData");
$_M(c$, "getMeasurements", 
function (type, iSpec) {
var ad = this.getDialog (type, iSpec);
return (ad == null || ad.getData ().size () == 0 || !ad.getState () ? null : ad.getData ());
}, "JSV.common.Annotation.AType,~N");
$_M(c$, "drawPlot", 
function (g, index, xyCoords, isContinuous, yOffset, isGrey, ig) {
var isIntegral = (ig != null);
var bsDraw = (ig == null ? null : ig.getBitSet ());
var fillPeaks = (!isIntegral && !isGrey && this.pendingIntegral != null && this.pendingIntegral.spec === this.spectra.get (index));
var iColor = (isGrey ? -2 : isIntegral ? -1 : !this.allowStacking ? 0 : index);
this.setPlotColor (g, iColor);
var plotOn = true;
var y0 = this.toPixelY (0);
fillPeaks = new Boolean (fillPeaks & (y0 == this.fixY (y0))).valueOf ();
var iFirst = this.viewData.getStartingPointIndex (index);
var iLast = this.viewData.getEndingPointIndex (index);
if (isContinuous) {
iLast--;
var doLineTo = !fillPeaks && this.g2d.canDoLineTo ();
if (doLineTo) this.g2d.doStroke (g, true);
var isDown = false;
for (var i = iFirst; i <= iLast; i++) {
var point1 = xyCoords[i];
var point2 = xyCoords[i + 1];
var y1 = (isIntegral ? this.toPixelYint (point1.getYVal ()) : this.toPixelY (point1.getYVal ()));
if (y1 == -2147483648) continue;
var y2 = (isIntegral ? this.toPixelYint (point2.getYVal ()) : this.toPixelY (point2.getYVal ()));
if (y2 == -2147483648) continue;
var x1 = this.toPixelX (point1.getXVal ());
var x2 = this.toPixelX (point2.getXVal ());
y1 = this.fixY (yOffset + y1);
y2 = this.fixY (yOffset + y2);
if (isIntegral) {
if (i == iFirst) {
this.xPixelPlot1 = x1;
this.yPixelPlot0 = y1;
}this.xPixelPlot0 = x2;
this.yPixelPlot1 = y2;
}if (x2 == x1 && y1 == y2) continue;
if (fillPeaks && this.pendingIntegral.overlaps (point1.getXVal (), point2.getXVal ())) {
this.setColorFromToken (g, JSV.common.ScriptToken.INTEGRALPLOTCOLOR);
this.g2d.drawLine (g, x1, y0, x1, y1);
this.setPlotColor (g, iColor);
continue;
}if (y1 == y2 && (y1 == this.yPixel0 || y1 == this.yPixel1)) continue;
if (bsDraw != null && bsDraw.get (i) != plotOn) {
plotOn = bsDraw.get (i);
if (!this.pd.isPrinting && this.pd.integralShiftMode != 0) this.setPlotColor (g, 0);
 else if (plotOn) this.setColorFromToken (g, JSV.common.ScriptToken.INTEGRALPLOTCOLOR);
 else this.setPlotColor (g, -3);
}if (this.pd.isPrinting && !plotOn) continue;
if (isDown) {
this.g2d.lineTo (g, x2, y2);
} else {
this.g2d.drawLine (g, x1, y1, x2, y2);
isDown = doLineTo;
}}
if (doLineTo) this.g2d.doStroke (g, false);
} else {
for (var i = iFirst; i <= iLast; i++) {
var point = xyCoords[i];
var y2 = this.toPixelY (point.getYVal ());
if (y2 == -2147483648) continue;
var x1 = this.toPixelX (point.getXVal ());
var y1 = this.toPixelY (Math.max (this.getScale ().minYOnScale, 0));
y1 = this.fixY (yOffset + y1);
y2 = this.fixY (yOffset + y2);
if (y1 == y2 && (y1 == this.yPixel0 || y1 == this.yPixel1)) continue;
this.g2d.drawLine (g, x1, y1, x1, y2);
}
if (this.getScale ().isYZeroOnScale ()) {
var y = yOffset + this.toPixelY (this.getScale ().spectrumYRef);
if (y == this.fixY (y)) this.g2d.drawLine (g, this.xPixel1, y, this.xPixel0, y);
}}}, "~O,~N,~A,~B,~N,~B,JSV.common.IntegralData");
$_M(c$, "drawFrame", 
function (g, iSpec, addCurrentBox, addSplitBox, drawUpDownArrows) {
if (!this.pd.gridOn || this.pd.isPrinting) {
this.setColorFromToken (g, JSV.common.ScriptToken.GRIDCOLOR);
this.g2d.drawRect (g, this.xPixel0, this.yPixel0, this.xPixels, this.yPixels);
if (this.pd.isPrinting) return;
}this.setCurrentBoxColor (g);
if (drawUpDownArrows) {
if (iSpec >= 0) {
this.setPlotColor (g, iSpec);
this.fillArrow (g, JSV.common.GraphSet.ArrowType.UP, this.xVArrows, Clazz_doubleToInt ((this.yPixel00 + this.yPixel11) / 2) - 9, true);
this.fillArrow (g, JSV.common.GraphSet.ArrowType.DOWN, this.xVArrows, Clazz_doubleToInt ((this.yPixel00 + this.yPixel11) / 2) + 9, true);
this.setCurrentBoxColor (g);
}this.fillArrow (g, JSV.common.GraphSet.ArrowType.UP, this.xVArrows, Clazz_doubleToInt ((this.yPixel00 + this.yPixel11) / 2) - 9, false);
this.fillCircle (g, this.xVArrows, Clazz_doubleToInt ((this.yPixel00 + this.yPixel11) / 2), false);
this.fillArrow (g, JSV.common.GraphSet.ArrowType.DOWN, this.xVArrows, Clazz_doubleToInt ((this.yPixel00 + this.yPixel11) / 2) + 9, false);
}if (this.imageView != null) return;
if (addCurrentBox) {
var x1 = this.xPixel00 + 10;
var x2 = this.xPixel11 - 10;
var y1 = this.yPixel00 + 1;
var y2 = this.yPixel11 - 2;
this.g2d.drawLine (g, x1, y1, x2, y1);
this.g2d.drawLine (g, x2, y1, x2, y2);
this.g2d.drawLine (g, x1, y2, x2, y2);
if (addSplitBox) {
this.fillBox (g, this.xPixel11 - 20, this.yPixel00 + 1, this.xPixel11 - 10, this.yPixel00 + 11, null);
}}}, "~O,~N,~B,~B,~B");
$_M(c$, "drawGrid", 
function (g) {
if (!this.pd.gridOn || this.imageView != null) return;
this.setColorFromToken (g, JSV.common.ScriptToken.GRIDCOLOR);
var lastX;
if (Double.isNaN (this.getScale ().firstX)) {
lastX = this.getScale ().maxXOnScale + this.getScale ().steps[0] / 2;
for (var val = this.getScale ().minXOnScale; val < lastX; val += this.getScale ().steps[0]) {
var x = this.toPixelX (val);
this.g2d.drawLine (g, x, this.yPixel0, x, this.yPixel1);
}
} else {
lastX = this.getScale ().maxXOnScale * 1.0001;
for (var val = this.getScale ().firstX; val <= lastX; val += this.getScale ().steps[0]) {
var x = this.toPixelX (val);
this.g2d.drawLine (g, x, this.yPixel0, x, this.yPixel1);
}
}for (var val = this.getScale ().firstY; val < this.getScale ().maxYOnScale + this.getScale ().steps[1] / 2; val += this.getScale ().steps[1]) {
var y = this.toPixelY (val);
if (y == this.fixY (y)) this.g2d.drawLine (g, this.xPixel0, y, this.xPixel1, y);
}
}, "~O");
$_M(c$, "drawXScale", 
function (g, c) {
this.setColorFromToken (g, JSV.common.ScriptToken.SCALECOLOR);
if (this.pd.isPrinting) this.g2d.drawLine (g, c.getXPixel0 (), this.yPixel1, c.getXPixel0 () + c.getXPixels () - 1, this.yPixel1);
var precision = this.getScale ().precision[0];
var font = this.pd.setFont (g, c.getXPixels (), 0, this.pd.isPrinting ? 10 : 12, false);
var y1 = this.yPixel1;
var y2 = this.yPixel1 + 4 * this.pd.scalingFactor;
var y3 = this.yPixel1 + 2 * this.pd.scalingFactor;
var h = font.getHeight ();
var dx = c.toPixelX (this.getScale ().steps[0]) - c.toPixelX (0);
var maxWidth = Math.abs (dx * 0.95);
var firstX = this.getScale ().firstX - this.getScale ().steps[0];
var lastX = (this.getScale ().maxXOnScale + this.getScale ().steps[0]) * 1.0001;
for (var pass = 0; pass < 2; pass++) {
if (pass == 1) JSV.common.ScaleData.fixScale (this.mapX);
var prevX = 1e10;
for (var val = firstX; val <= lastX; val += this.getScale ().steps[0]) {
var x = c.toPixelX (val);
var d = Double.$valueOf (val);
var s;
switch (pass) {
case 0:
s = JU.DF.formatDecimalDbl (val, precision);
this.mapX.put (d, s);
this.drawTick (g, x, y1, y2, c);
dx = Math.abs (prevX - val);
var ntick = this.getScale ().minorTickCounts[0];
if (ntick != 0) {
var step = dx / ntick;
for (var i = 1; i < ntick; i++) {
var x1 = val - i * step;
this.drawTick (g, c.toPixelX (x1), y1, y3, c);
}
}prevX = val;
continue;
case 1:
s = this.mapX.get (d);
if (s == null || x != c.fixX (x)) continue;
var w = this.pd.getStringWidth (s);
var n = (x + Clazz_doubleToInt (w / 2) == c.fixX (x + Clazz_doubleToInt (w / 2)) ? 2 : 0);
if (n > 0) this.g2d.drawString (g, s, x - Clazz_doubleToInt (w / n), y2 + h);
val += Math.floor (w / maxWidth) * this.getScale ().steps[0];
break;
}
}
}
this.mapX.clear ();
}, "~O,JSV.api.XYScaleConverter");
$_M(c$, "drawTick", 
function (g, x, y1, y2, c) {
if (x == c.fixX (x)) this.g2d.drawLine (g, x, y1, x, y2);
}, "~O,~N,~N,~N,JSV.api.XYScaleConverter");
$_M(c$, "drawYScale", 
function (g, c) {
var sd = c.getScale ();
var precision = sd.precision[1];
var font = this.pd.setFont (g, c.getXPixels (), 0, this.pd.isPrinting ? 10 : 12, false);
var h = font.getHeight ();
var max = sd.maxYOnScale + sd.steps[1] / 2;
var yLast = -2147483648;
this.setColorFromToken (g, JSV.common.ScriptToken.SCALECOLOR);
for (var pass = 0; pass < 2; pass++) {
if (pass == 1) JSV.common.ScaleData.fixScale (this.mapX);
for (var val = sd.firstY; val < max; val += sd.steps[1]) {
var d = Double.$valueOf (val);
var x1 = c.getXPixel0 ();
var y = c.toPixelY (val);
if (y != c.fixY (y)) continue;
var s;
if (pass == 0) this.g2d.drawLine (g, x1, y, x1 - 3 * this.pd.scalingFactor, y);
if (Math.abs (y - yLast) <= h) continue;
yLast = y;
switch (pass) {
case 0:
s = JU.DF.formatDecimalDbl (val, precision);
this.mapX.put (d, s);
break;
case 1:
s = this.mapX.get (d);
if (s == null) continue;
if (s.startsWith ("0") && s.contains ("E")) s = "0";
this.g2d.drawString (g, s, (x1 - 4 * this.pd.scalingFactor - this.pd.getStringWidth (s)), y + Clazz_doubleToInt (h / 3));
break;
}
}
}
this.mapX.clear ();
}, "~O,JSV.api.XYScaleConverter");
$_M(c$, "drawXUnits", 
function (g) {
var units = this.spectra.get (0).getAxisLabel (true);
if (units != null) this.drawUnits (g, units, this.xPixel1 + 25 * this.pd.scalingFactor, this.yPixel1 + 5 * this.pd.scalingFactor, 1, 1);
}, "~O");
$_M(c$, "drawUnits", 
function (g, s, x, y, hOff, vOff) {
this.setColorFromToken (g, JSV.common.ScriptToken.UNITSCOLOR);
this.pd.setFont (g, (this.imageView == null ? this : this.imageView).getXPixels (), 2, 10, false);
this.g2d.drawString (g, s, Clazz_doubleToInt (x - this.pd.getStringWidth (s) * hOff), Clazz_doubleToInt (y + this.pd.getFontHeight () * vOff));
}, "~O,~S,~N,~N,~N,~N");
$_M(c$, "drawYUnits", 
function (g) {
var units = this.spectra.get (0).getAxisLabel (false);
if (units != null) this.drawUnits (g, units, (this.pd.isPrinting ? 30 : 5) * this.pd.scalingFactor, this.yPixel0 + (this.pd.isPrinting ? 0 : 5) * this.pd.scalingFactor, 0, -1);
}, "~O");
$_M(c$, "drawHighlightsAndPeakTabs", 
function (g, iSpec) {
var md = this.getMeasurements (JSV.common.Annotation.AType.PeakList, iSpec);
var spec = this.spectra.get (iSpec);
if (this.pd.isPrinting) {
if (md != null) {
this.setColorFromToken (g, JSV.common.ScriptToken.PEAKTABCOLOR);
this.printPeakList (g, spec, md);
}return;
}if (md == null) {
for (var i = 0; i < this.highlights.size (); i++) {
var hl = this.highlights.get (i);
if (hl.spectrum === spec) {
this.pd.setHighlightColor (hl.color);
this.drawBar (g, null, hl.x1, hl.x2, JSV.common.ScriptToken.HIGHLIGHTCOLOR, true);
}}
this.drawPeakTabs (g, spec);
}var y;
if (md != null) {
y = (spec.isInverted () ? this.yPixel1 - 10 * this.pd.scalingFactor : this.yPixel0);
this.setColorFromToken (g, JSV.common.ScriptToken.PEAKTABCOLOR);
for (var i = md.size (); --i >= 0; ) {
var m = md.get (i);
var x = this.toPixelX (m.getXVal ());
this.g2d.drawLine (g, x, y, x, y + 10 * this.pd.scalingFactor);
}
if (this.isVisible (this.getDialog (JSV.common.Annotation.AType.PeakList, iSpec))) {
y = this.toPixelY ((md).getThresh ());
if (y == this.fixY (y) && !this.pd.isPrinting) this.g2d.drawLine (g, this.xPixel0, y, this.xPixel1, y);
}}}, "~O,~N");
$_M(c$, "printPeakList", 
function (g, spec, data) {
var sdata = data.getMeasurementListArray (null);
if (sdata.length == 0) return;
this.pd.setFont (g, this.xPixels, 0, 8, false);
var h = this.pd.getFontHeight ();
var xs =  Clazz_newIntArray (data.size (), 0);
var xs0 =  Clazz_newIntArray (data.size (), 0);
var dx = 0;
var s5 = 5 * this.pd.scalingFactor;
var s10 = 10 * this.pd.scalingFactor;
var s15 = 15 * this.pd.scalingFactor;
var s25 = 25 * this.pd.scalingFactor;
for (var i = 0; i < sdata.length; i++) {
xs0[i] = this.toPixelX (Double.parseDouble (sdata[i][1]));
if (i == 0) {
xs[i] = xs0[i];
continue;
}xs[i] = Math.max (xs[i - 1] + h, xs0[i] + h);
dx += (xs[i] - xs0[i]);
}
dx /= 2 * sdata.length;
if (xs[0] - dx < this.xPixel0 + s25) dx = xs[0] - (this.xPixel0 + s25);
for (var i = 0; i < sdata.length; i++) xs[i] -= dx;

var inverted = spec.isInverted ();
var y4 = this.pd.getStringWidth ("99.9999");
var y2 = (sdata[0].length >= 6 ? this.pd.getStringWidth ("99.99") : 0);
var f = (inverted ? -1 : 1);
var y = (inverted ? this.yPixel1 : this.yPixel0) + f * (y2 + y4 + s15);
for (var i = 0; i < sdata.length; i++) {
this.g2d.drawLine (g, xs[i], y, xs[i], y + s5 * f);
this.g2d.drawLine (g, xs[i], y + s5 * f, xs0[i], y + s10 * f);
this.g2d.drawLine (g, xs0[i], y + s10 * f, xs0[i], y + s15 * f);
if (y2 > 0 && sdata[i][4].length > 0) this.g2d.drawLine (g, Clazz_doubleToInt ((xs[i] + xs[i - 1]) / 2), y - y4 + s5, Clazz_doubleToInt ((xs[i] + xs[i - 1]) / 2), y - y4 - s5);
}
y -= f * 2 * this.pd.scalingFactor;
if (y2 > 0) {
this.drawStringRotated (g, -90, xs[0] - s15, y, "  ppm");
this.drawStringRotated (g, -90, xs[0] - s15, y - y4 - s5, " Hz");
}for (var i = data.size (); --i >= 0; ) {
this.drawStringRotated (g, -90 * f, xs[i] + Clazz_doubleToInt (f * h / 3), y, sdata[i][1]);
if (y2 > 0 && sdata[i][4].length > 0) {
var x = Clazz_doubleToInt ((xs[i] + xs[i - 1]) / 2) + Clazz_doubleToInt (h / 3);
this.drawStringRotated (g, -90, x, y - y4 - s5, sdata[i][4]);
}}
}, "~O,JSV.common.JDXSpectrum,JSV.common.PeakData");
$_M(c$, "drawStringRotated", 
function (g, angle, x, y, s) {
this.g2d.drawStringRotated (g, s, x, y, angle);
}, "~O,~N,~N,~N,~S");
$_M(c$, "drawAnnotations", 
function (g, annotations, whatColor) {
this.pd.setFont (g, this.xPixels, 1, 12, false);
for (var i = annotations.size (); --i >= 0; ) {
var note = annotations.get (i);
this.setAnnotationColor (g, note, whatColor);
var c = (note.is2D ? this.imageView : this);
var x = c.toPixelX (note.getXVal ());
var y = (note.isPixels () ? Clazz_doubleToInt (this.yPixel0 + 10 * this.pd.scalingFactor - note.getYVal ()) : note.is2D ? this.imageView.subIndexToPixelY (Clazz_doubleToInt (note.getYVal ())) : this.toPixelY (note.getYVal ()));
this.g2d.drawString (g, note.text, x + note.offsetX * this.pd.scalingFactor, y - note.offsetY * this.pd.scalingFactor);
}
}, "~O,JU.List,JSV.common.ScriptToken");
$_M(c$, "drawIntegralValues", 
function (g, iSpec, yOffset) {
var integrals = this.getMeasurements (JSV.common.Annotation.AType.Integration, iSpec);
if (integrals != null) {
if (this.pd.isPrinting) this.pd.setFont (g, this.xPixels, 0, 8, false);
 else this.pd.setFont (g, this.xPixels, 1, 12, false);
this.setColorFromToken (g, JSV.common.ScriptToken.INTEGRALPLOTCOLOR);
var h = this.pd.getFontHeight ();
this.g2d.setStrokeBold (g, true);
for (var i = integrals.size (); --i >= 0; ) {
var $in = integrals.get (i);
if ($in.getValue () == 0) continue;
var x = this.toPixelX ($in.getXVal2 ());
var y1 = yOffset * this.pd.scalingFactor + this.toPixelYint ($in.getYVal ());
var y2 = yOffset * this.pd.scalingFactor + this.toPixelYint ($in.getYVal2 ());
if (x != this.fixX (x) || y1 != this.fixY (y1) || y2 != this.fixY (y2)) continue;
if (!this.pd.isPrinting) this.g2d.drawLine (g, x, y1, x, y2);
var s = "  " + $in.text;
this.g2d.drawString (g, s, x, Clazz_doubleToInt ((y1 + y2) / 2) + Clazz_doubleToInt (h / 3));
}
this.g2d.setStrokeBold (g, false);
}if (iSpec == this.getFixedSelectedSpectrumIndex ()) this.selectedSpectrumIntegrals = integrals;
}, "~O,~N,~N");
$_M(c$, "drawMeasurements", 
function (g, iSpec) {
var md = this.getMeasurements (JSV.common.Annotation.AType.Measurements, iSpec);
if (md != null) for (var i = md.size (); --i >= 0; ) this.drawMeasurement (g, md.get (i));

if (iSpec == this.getFixedSelectedSpectrumIndex ()) this.selectedSpectrumMeasurements = md;
}, "~O,~N");
$_M(c$, "drawMeasurement", 
function (g, m) {
if (m.text.length == 0 && m !== this.pendingMeasurement) return;
this.pd.setFont (g, this.xPixels, 1, 12, false);
this.g2d.setGraphicsColor (g, (m === this.pendingMeasurement ? this.pd.getColor (JSV.common.ScriptToken.HIGHLIGHTCOLOR) : this.pd.BLACK));
var x1 = this.toPixelX (m.getXVal ());
var y1 = this.toPixelY (m.getYVal ());
var x2 = this.toPixelX (m.getXVal2 ());
if (Double.isNaN (m.getXVal ()) || x1 != this.fixX (x1) || x2 != this.fixX (x2)) return;
var drawString = (Math.abs (x1 - x2) >= 2);
var drawBaseLine = this.getScale ().isYZeroOnScale () && m.spec.isHNMR ();
var x = Clazz_doubleToInt ((x1 + x2) / 2);
this.g2d.setStrokeBold (g, true);
if (drawString) this.g2d.drawLine (g, x1, y1, x2, y1);
if (drawBaseLine) this.g2d.drawLine (g, x1 + 1, this.yPixel1 - 1, x2, this.yPixel1 - 1);
this.g2d.setStrokeBold (g, false);
if (drawString) this.g2d.drawString (g, m.text, x + m.offsetX, y1 - m.offsetY);
if (drawBaseLine) {
this.g2d.drawLine (g, x1, this.yPixel1, x1, this.yPixel1 - 6 * this.pd.scalingFactor);
this.g2d.drawLine (g, x2, this.yPixel1, x2, this.yPixel1 - 6 * this.pd.scalingFactor);
}}, "~O,JSV.common.Measurement");
$_M(c$, "getPinSelected", 
function (xPixel, yPixel) {
if (this.widgets != null) for (var i = 0; i < this.widgets.length; i++) {
if (this.widgets[i] != null && this.widgets[i].isPinOrCursor && this.widgets[i].selected (xPixel, yPixel)) {
return this.widgets[i];
}}
return null;
}, "~N,~N");
$_M(c$, "set2DCrossHairs", 
function (xPixel, yPixel) {
var x;
if (xPixel == this.imageView.fixX (xPixel) && yPixel == this.fixY (yPixel)) {
this.pin1Dx1.setX (x = this.imageView.toX (xPixel), this.toPixelX (x));
this.cur2Dx1.setX (x, xPixel);
this.setCurrentSubSpectrum (this.imageView.toSubspectrumIndex (yPixel));
if (this.isLinked) {
var y = this.imageView.toY (yPixel);
this.pd.set2DCrossHairsLinked (this, x, y, !this.sticky2Dcursor);
}}}, "~N,~N");
$_M(c$, "reset2D", 
function (isX) {
if (isX) {
this.imageView.setView0 (this.imageView.xPixel0, this.pin2Dy0.yPixel0, this.imageView.xPixel1, this.pin2Dy1.yPixel0);
this.doZoom (0, this.getScale ().minY, 0, this.getScale ().maxY, true, false, false, false, true);
} else {
this.imageView.setView0 (this.pin2Dx0.xPixel0, this.imageView.yPixel0, this.pin2Dx1.xPixel0, this.imageView.yPixel1);
}}, "~B");
$_M(c$, "setAnnotationText", 
function (a) {
var sval = this.pd.getInput ("New text?", "Set Label", a.text);
if (sval == null) return false;
if (sval.length == 0) this.annotations.removeObj (a);
 else a.text = sval;
return true;
}, "JSV.common.Annotation");
$_M(c$, "checkIntegral", 
function (x1, x2, isFinal) {
var ad = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
if (ad == null) return;
var integral = (ad.getData ()).addIntegralRegion (x1, x2);
if (isFinal && Clazz_instanceOf (ad, JSV.dialog.JSVDialog)) (ad).update (null, 0, 0);
this.selectedSpectrumIntegrals = null;
this.pendingIntegral = (isFinal ? null : integral);
}, "~N,~N,~B");
$_M(c$, "setToolTipForPixels", 
function (xPixel, yPixel) {
var pw = this.getPinSelected (xPixel, yPixel);
var precisionX = this.getScale ().precision[0];
var precisionY = this.getScale ().precision[1];
if (pw != null) {
if (this.setStartupPinTip ()) return;
var s;
if (pw === this.pin1Dx01 || pw === this.pin2Dx01) {
s = JU.DF.formatDecimalDbl (Math.min (this.pin1Dx0.getXVal (), this.pin1Dx1.getXVal ()), precisionX) + " - " + JU.DF.formatDecimalDbl (Math.max (this.pin1Dx0.getXVal (), this.pin1Dx1.getXVal ()), precisionX);
} else if (pw === this.pin1Dy01) {
s = JU.DF.formatDecimalDbl (Math.min (this.pin1Dy0.getYVal (), this.pin1Dy1.getYVal ()), precisionY) + " - " + JU.DF.formatDecimalDbl (Math.max (this.pin1Dy0.getYVal (), this.pin1Dy1.getYVal ()), precisionY);
} else if (pw === this.cur2Dy) {
var isub = this.imageView.toSubspectrumIndex (pw.yPixel0);
s = this.get2DYLabel (isub, precisionX);
} else if (pw === this.pin2Dy01) {
s = "" + Clazz_doubleToInt (Math.min (this.pin2Dy0.getYVal (), this.pin2Dy1.getYVal ())) + " - " + Clazz_doubleToInt (Math.max (this.pin2Dy0.getYVal (), this.pin2Dy1.getYVal ()));
} else if (pw.isXtype) {
s = JU.DF.formatDecimalDbl (pw.getXVal (), precisionX);
} else if (pw.is2D) {
s = "" + Clazz_doubleToInt (pw.getYVal ());
} else {
s = JU.DF.formatDecimalDbl (pw.getYVal (), precisionY);
}this.pd.setToolTipText (s);
return;
}var yPt;
if (this.imageView != null) {
if (this.imageView.fixX (xPixel) == xPixel && this.fixY (yPixel) == yPixel) {
var isub = this.imageView.toSubspectrumIndex (yPixel);
var s = JU.DF.formatDecimalDbl (this.imageView.toX (xPixel), precisionX) + " " + this.getSpectrum ().getAxisLabel (true) + ",  " + this.get2DYLabel (isub, precisionX);
this.pd.setToolTipText (this.pd.display1D ? s : "");
this.pd.coordStr = s;
return;
}if (!this.pd.display1D) {
this.pd.setToolTipText ("");
this.pd.coordStr = "";
return;
}}var xPt = this.toX (this.fixX (xPixel));
yPt = (this.imageView != null && this.imageView.isXWithinRange (xPixel) ? this.imageView.toSubspectrumIndex (this.fixY (yPixel)) : this.toY (this.fixY (yPixel)));
var xx = this.setCoordStr (xPt, yPt);
var iSpec = this.getFixedSelectedSpectrumIndex ();
if (!this.isInPlotRegion (xPixel, yPixel)) {
yPt = NaN;
} else if (this.nSpectra == 1) {
} else if (this.haveIntegralDisplayed (iSpec)) {
yPt = this.getIntegrationGraph (iSpec).getPercentYValueAt (xPt);
xx += ", " + JU.DF.formatDecimalDbl (yPt, 1);
}this.pd.setToolTipText ((this.pendingMeasurement != null || this.selectedMeasurement != null || this.selectedIntegral != null ? (this.pd.hasFocus () ? "Press ESC to delete " + (this.selectedIntegral != null ? "integral, DEL to delete all visible, or N to normalize" : this.pendingMeasurement == null ? "\"" + this.selectedMeasurement.text + "\" or DEL to delete all visible" : "measurement") : "") : Double.isNaN (yPt) ? null : xx));
}, "~N,~N");
$_M(c$, "setCoordStr", 
function (xPt, yPt) {
var xx = JU.DF.formatDecimalDbl (xPt, this.getScale ().precision[0]);
this.pd.coordStr = "(" + xx + (this.haveSingleYScale || this.iSpectrumSelected >= 0 ? ", " + JU.DF.formatDecimalDbl (yPt, this.getScale ().precision[1]) : "") + ")";
return xx;
}, "~N,~N");
$_M(c$, "setStartupPinTip", 
function () {
if (this.pd.startupPinTip == null) return false;
this.pd.setToolTipText (this.pd.startupPinTip);
this.pd.startupPinTip = null;
return true;
});
$_M(c$, "get2DYLabel", 
function (isub, precision) {
var spec = this.getSpectrumAt (0).getSubSpectra ().get (isub);
return JU.DF.formatDecimalDbl (spec.getY2D (), precision) + (spec.y2DUnits.equals ("HZ") ? " HZ (" + JU.DF.formatDecimalDbl (spec.getY2DPPM (), precision) + " PPM)" : "");
}, "~N,~N");
$_M(c$, "isOnSpectrum", 
function (xPixel, yPixel, index) {
var xyCoords = null;
var isContinuous = true;
var isIntegral = (index < 0);
if (isIntegral) {
var ad = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
if (ad == null) return false;
xyCoords = (ad.getData ()).getXYCoords ();
index = this.getFixedSelectedSpectrumIndex ();
} else {
this.setScale (index);
var spec = this.spectra.get (index);
xyCoords = spec.xyCoords;
isContinuous = spec.isContinuous ();
}var yOffset = index * Clazz_floatToInt (this.yPixels * (this.yStackOffsetPercent / 100));
var ix0 = this.viewData.getStartingPointIndex (index);
var ix1 = this.viewData.getEndingPointIndex (index);
if (isContinuous) {
for (var i = ix0; i < ix1; i++) {
var point1 = xyCoords[i];
var point2 = xyCoords[i + 1];
var x1 = this.toPixelX (point1.getXVal ());
var x2 = this.toPixelX (point2.getXVal ());
var y1 = (isIntegral ? this.toPixelYint (point1.getYVal ()) : this.toPixelY (point1.getYVal ()));
var y2 = (isIntegral ? this.toPixelYint (point2.getYVal ()) : this.toPixelY (point2.getYVal ()));
if (y1 == -2147483648 || y2 == -2147483648) continue;
y1 = yOffset + this.fixY (y1);
y2 = yOffset + this.fixY (y2);
if (JSV.common.GraphSet.isOnLine (xPixel, yPixel, x1, y1, x2, y2)) return true;
}
} else {
for (var i = ix0; i <= ix1; i++) {
var point = xyCoords[i];
var y2 = this.toPixelY (point.getYVal ());
if (y2 == -2147483648) continue;
var x1 = this.toPixelX (point.getXVal ());
var y1 = this.toPixelY (Math.max (this.getScale ().minYOnScale, 0));
y1 = this.fixY (y1);
y2 = this.fixY (y2);
if (y1 == y2 && (y1 == this.yPixel0 || y1 == this.yPixel1)) continue;
if (JSV.common.GraphSet.isOnLine (xPixel, yPixel, x1, y1, x1, y2)) return true;
}
}return false;
}, "~N,~N,~N");
c$.distance = $_M(c$, "distance", 
function (dx, dy) {
return Math.sqrt (dx * dx + dy * dy);
}, "~N,~N");
c$.findCompatibleGraphSet = $_M(c$, "findCompatibleGraphSet", 
function (graphSets, spec) {
for (var i = 0; i < graphSets.size (); i++) if (JSV.common.JDXSpectrum.areXScalesCompatible (spec, graphSets.get (i).getSpectrum (), false, false)) return graphSets.get (i);

return null;
}, "JU.List,JSV.common.JDXSpectrum");
c$.isGoodEvent = $_M(c$, "isGoodEvent", 
function (zOrP, p, asX) {
return (p == null ? (Math.abs (zOrP.xPixel1 - zOrP.xPixel0) > 5 && Math.abs (zOrP.yPixel1 - zOrP.yPixel0) > 5) : asX ? Math.abs (zOrP.xPixel0 - p.xPixel0) > 5 : Math.abs (zOrP.yPixel0 - p.yPixel0) > 5);
}, "JSV.common.PlotWidget,JSV.common.PlotWidget,~B");
c$.isOnLine = $_M(c$, "isOnLine", 
function (xPixel, yPixel, x1, y1, x2, y2) {
var dx1 = Math.abs (x1 - xPixel);
if (dx1 < 2 && Math.abs (y1 - yPixel) < 2) return true;
var dx2 = x2 - xPixel;
if (Math.abs (dx2) < 2 && Math.abs (y2 - yPixel) < 2) return true;
var dy12 = y1 - y2;
if (Math.abs (dy12) > 2 && (y1 < yPixel) == (y2 < yPixel)) return false;
var dx12 = x1 - x2;
if (Math.abs (dx12) > 2 && (x1 < xPixel) == (x2 < xPixel)) return false;
return (JSV.common.GraphSet.distance (dx1, y1 - yPixel) + JSV.common.GraphSet.distance (dx2, yPixel - y2) < JSV.common.GraphSet.distance (dx12, dy12) + 2);
}, "~N,~N,~N,~N,~N,~N");
c$.setFractionalPositions = $_M(c$, "setFractionalPositions", 
function (pd, graphSets, linkMode) {
var n = graphSets.size ();
var f = 0;
var n2d = 1;
var gs;
var y = 0;
pd.isLinked = (linkMode !== JSV.common.PanelData.LinkMode.NONE);
if (linkMode === JSV.common.PanelData.LinkMode.NONE) {
for (var i = 0; i < n; i++) {
gs = graphSets.get (i);
f += (gs.getSpectrumAt (0).is1D () ? 1 : n2d) * gs.nSplit;
}
f = 1 / f;
for (var i = 0; i < n; i++) {
gs = graphSets.get (i);
gs.isLinked = false;
var g = (gs.getSpectrumAt (0).is1D () ? f : n2d * f);
gs.fX0 = 0;
gs.fY0 = y;
gs.fracX = 1;
gs.fracY = g;
y += g * gs.nSplit;
}
} else {
var gs2d = null;
var i2d = -1;
if (n == 2 || n == 3) for (var i = 0; i < n; i++) {
gs = graphSets.get (i);
if (!gs.getSpectrum ().is1D ()) {
gs2d = gs;
if (i2d >= 0) i = -2;
i2d = i;
break;
}}
if (i2d == -2 || i2d == -1 && n != 2) {
JSV.common.GraphSet.setFractionalPositions (pd, graphSets, JSV.common.PanelData.LinkMode.NONE);
return;
}for (var i = 0; i < n; i++) {
gs = graphSets.get (i);
gs.isLinked = true;
var s1 = gs.getSpectrumAt (0);
var is1D = s1.is1D ();
if (is1D) {
if (gs2d != null) {
var s2 = gs2d.getSpectrumAt (0);
if (JSV.common.JDXSpectrum.areLinkableX (s1, s2)) gs.gs2dLinkedX = gs2d;
if (JSV.common.JDXSpectrum.areLinkableY (s1, s2)) gs.gs2dLinkedY = gs2d;
}gs.fX0 = 0;
gs.fY0 = y;
gs.fracX = (gs2d == null ? 1 : 0.5);
gs.fracY = (n == 3 || gs2d == null ? 0.5 : 1);
y += 0.5;
} else {
gs.fX0 = 0.5;
gs.fY0 = 0;
gs.fracX = 0.5;
gs.fracY = 1;
}}
}}, "JSV.common.PanelData,JU.List,JSV.common.PanelData.LinkMode");
$_M(c$, "addAnnotation", 
function (args, title) {
if (args.size () == 0 || args.size () == 1 && args.get (0).equalsIgnoreCase ("none")) {
this.annotations = null;
this.lastAnnotation = null;
return null;
}if (args.size () < 4 && this.lastAnnotation == null) this.lastAnnotation = this.getAnnotation ((this.getScale ().maxXOnScale + this.getScale ().minXOnScale) / 2, (this.getScale ().maxYOnScale + this.getScale ().minYOnScale) / 2, title, false, false, 0, 0);
var annotation = this.getAnnotation (args, this.lastAnnotation);
if (annotation == null) return null;
if (this.annotations == null && args.size () == 1 && args.get (0).charAt (0) == '\"') {
var s = annotation.text;
this.getSpectrum ().setTitle (s);
return s;
}this.lastAnnotation = annotation;
this.addAnnotation (annotation, false);
return null;
}, "JU.List,~S");
$_M(c$, "addHighlight", 
function (x1, x2, spec, color) {
if (spec == null) spec = this.getSpectrumAt (0);
var hl = Clazz_innerTypeInstance (JSV.common.GraphSet.Highlight, this, null, x1, x2, spec, (color == null ? this.pd.getColor (JSV.common.ScriptToken.HIGHLIGHTCOLOR) : color));
if (!this.highlights.contains (hl)) this.highlights.addLast (hl);
}, "~N,~N,JSV.common.JDXSpectrum,javajs.api.GenericColor");
$_M(c$, "addPeakHighlight", 
function (peakInfo) {
for (var i = this.spectra.size (); --i >= 0; ) {
var spec = this.spectra.get (i);
this.removeAllHighlights (spec);
if (peakInfo == null || peakInfo.isClearAll () || spec !== peakInfo.spectrum) continue;
var peak = peakInfo.toString ();
if (peak == null) {
continue;
}var xMin = JU.PT.getQuotedAttribute (peak, "xMin");
var xMax = JU.PT.getQuotedAttribute (peak, "xMax");
if (xMin == null || xMax == null) return;
var x1 = JU.PT.parseFloat (xMin);
var x2 = JU.PT.parseFloat (xMax);
if (Float.isNaN (x1) || Float.isNaN (x2)) return;
this.pd.addHighlight (this, x1, x2, spec, 200, 200, 200, 200);
spec.setSelectedPeak (peakInfo);
if (this.getScale ().isInRangeX (x1) || this.getScale ().isInRangeX (x2) || x1 < this.getScale ().minX && this.getScale ().maxX < x2) {
} else {
this.setZoomTo (0);
}}
}, "JSV.common.PeakInfo");
$_M(c$, "advanceSubSpectrum", 
function (dir) {
var spec0 = this.getSpectrumAt (0);
var i = spec0.advanceSubSpectrum (dir);
if (spec0.isForcedSubset ()) this.viewData.setXRangeForSubSpectrum (this.getSpectrum ().getXYCoords ());
this.pd.notifySubSpectrumChange (i, this.getSpectrum ());
}, "~N");
$_M(c$, "checkSpectrumClickedEvent", 
function (xPixel, yPixel, clickCount) {
if (this.nextClickForSetPeak) return false;
if (clickCount > 0 && this.checkArrowLeftRightClick (xPixel, yPixel)) return true;
if (clickCount > 1 || this.pendingMeasurement != null || !this.isInPlotRegion (xPixel, yPixel)) return false;
if (clickCount == 0) {
var isOnIntegral = this.isOnSpectrum (xPixel, yPixel, -1);
this.pd.integralShiftMode = (isOnIntegral ? this.getShiftMode (xPixel, yPixel) : 0);
this.pd.isIntegralDrag = (this.pd.integralShiftMode == 0 && (isOnIntegral || this.haveIntegralDisplayed (-1) && this.findMeasurement (this.getIntegrationGraph (-1), xPixel, yPixel, 0) != null));
System.out.println ("pd.isIntegralDrag set in checkSPelick " + this.pd.isIntegralDrag);
if (this.pd.integralShiftMode != 0) return false;
}if (!this.showAllStacked) return false;
this.stackSelected = false;
for (var i = 0; i < this.nSpectra; i++) {
if (!this.isOnSpectrum (xPixel, yPixel, i)) continue;
var isNew = (i != this.iSpectrumSelected);
this.setSpectrumClicked (this.iPreviousSpectrumClicked = i);
return isNew;
}
if (this.isDialogOpen ()) return false;
this.setSpectrumClicked (-1);
return this.stackSelected = false;
}, "~N,~N,~N");
$_M(c$, "getShiftMode", 
function (xPixel, yPixel) {
return (this.isStartEndIntegral (xPixel, false) ? yPixel : this.isStartEndIntegral (xPixel, true) ? -yPixel : 0);
}, "~N,~N");
$_M(c$, "isDialogOpen", 
function () {
return (this.isVisible (this.getDialog (JSV.common.Annotation.AType.Integration, -1)) || this.isVisible (this.getDialog (JSV.common.Annotation.AType.Measurements, -1)) || this.isVisible (this.getDialog (JSV.common.Annotation.AType.PeakList, -1)));
});
$_M(c$, "isStartEndIntegral", 
function (xPixel, isEnd) {
return (isEnd ? this.xPixelPlot1 - xPixel < 20 : xPixel - this.xPixelPlot0 < 20);
}, "~N,~B");
$_M(c$, "checkWidgetEvent", 
function (xPixel, yPixel, isPress) {
if (!this.zoomEnabled || !this.triggered) return;
this.triggered = false;
var widget;
if (isPress) {
if (this.pd.clickCount == 2) {
if (this.pendingMeasurement == null) {
if (this.iSpectrumClicked == -1 && this.iPreviousSpectrumClicked >= 0) {
this.setSpectrumClicked (this.iPreviousSpectrumClicked);
}this.processPendingMeasurement (xPixel, yPixel, 2);
return;
}}if (this.pendingMeasurement != null) return;
widget = this.getPinSelected (xPixel, yPixel);
if (widget == null) {
yPixel = this.fixY (yPixel);
if (xPixel < this.xPixel1) {
if (this.pd.shiftPressed) this.setSpectrumClicked (this.iPreviousSpectrumClicked);
xPixel = this.fixX (xPixel);
this.zoomBox1D.setX (this.toX (xPixel), xPixel);
this.zoomBox1D.yPixel0 = yPixel;
widget = this.zoomBox1D;
} else if (this.imageView != null && xPixel < this.imageView.xPixel1) {
this.zoomBox2D.setX (this.imageView.toX (xPixel), this.imageView.fixX (xPixel));
this.zoomBox2D.yPixel0 = yPixel;
widget = this.zoomBox2D;
}}this.pd.thisWidget = widget;
return;
}this.nextClickForSetPeak = false;
widget = this.pd.thisWidget;
if (widget == null) return;
if (widget === this.zoomBox1D) {
this.zoomBox1D.xPixel1 = this.fixX (xPixel);
this.zoomBox1D.yPixel1 = this.fixY (yPixel);
if (this.pd.isIntegralDrag && this.zoomBox1D.xPixel0 != this.zoomBox1D.xPixel1) this.checkIntegral (this.zoomBox1D.getXVal (), this.toX (this.zoomBox1D.xPixel1), false);
return;
}if (widget === this.zoomBox2D) {
this.zoomBox2D.xPixel1 = this.imageView.fixX (xPixel);
this.zoomBox2D.yPixel1 = this.fixY (yPixel);
return;
}if (widget === this.cur2Dy) {
yPixel = this.fixY (yPixel);
this.cur2Dy.yPixel0 = this.cur2Dy.yPixel1 = yPixel;
this.setCurrentSubSpectrum (this.imageView.toSubspectrumIndex (yPixel));
return;
}if (widget === this.cur2Dx0 || widget === this.cur2Dx1) {
return;
}if (widget === this.pin1Dx0 || widget === this.pin1Dx1 || widget === this.pin1Dx01) {
xPixel = this.fixX (xPixel);
widget.setX (this.toX0 (xPixel), xPixel);
if (widget === this.pin1Dx01) {
var dp = xPixel - (Clazz_doubleToInt ((this.pin1Dx0.xPixel0 + this.pin1Dx1.xPixel0) / 2));
var dp1 = (dp < 0 ? dp : dp);
var dp2 = (dp < 0 ? dp : dp);
xPixel = this.pin1Dx0.xPixel0 + dp2;
var xPixel1 = this.pin1Dx1.xPixel0 + dp1;
if (dp == 0 || this.fixX (xPixel) != xPixel || this.fixX (xPixel1) != xPixel1) return;
this.pin1Dx0.setX (this.toX0 (xPixel), xPixel);
this.pin1Dx1.setX (this.toX0 (xPixel1), xPixel1);
}this.doZoom (this.pin1Dx0.getXVal (), 0, this.pin1Dx1.getXVal (), 0, true, false, false, true, false);
return;
}if (widget === this.pin1Dy0 || widget === this.pin1Dy1 || widget === this.pin1Dy01) {
yPixel = this.fixY (yPixel);
widget.setY (this.toY0 (yPixel), yPixel);
if (widget === this.pin1Dy01) {
var dp = yPixel - Clazz_doubleToInt ((this.pin1Dy0.yPixel0 + this.pin1Dy1.yPixel0) / 2) + 1;
yPixel = this.pin1Dy0.yPixel0 + dp;
var yPixel1 = this.pin1Dy1.yPixel0 + dp;
var y0 = this.toY0 (yPixel);
var y1 = this.toY0 (yPixel1);
if (Math.min (y0, y1) == this.getScale ().minY || Math.max (y0, y1) == this.getScale ().maxY) return;
this.pin1Dy0.setY (y0, yPixel);
this.pin1Dy1.setY (y1, yPixel1);
}this.doZoom (0, this.pin1Dy0.getYVal (), 0, this.pin1Dy1.getYVal (), this.imageView == null, this.imageView == null, false, false, false);
return;
}if (widget === this.pin2Dx0 || widget === this.pin2Dx1 || widget === this.pin2Dx01) {
xPixel = this.imageView.fixX (xPixel);
widget.setX (this.imageView.toX0 (xPixel), xPixel);
if (widget === this.pin2Dx01) {
var dp = xPixel - Clazz_doubleToInt ((this.pin2Dx0.xPixel0 + this.pin2Dx1.xPixel0) / 2) + 1;
xPixel = this.pin2Dx0.xPixel0 + dp;
var xPixel1 = this.pin2Dx1.xPixel0 + dp;
if (this.imageView.fixX (xPixel) != xPixel || this.imageView.fixX (xPixel1) != xPixel1) return;
this.pin2Dx0.setX (this.imageView.toX0 (xPixel), xPixel);
this.pin2Dx1.setX (this.imageView.toX0 (xPixel1), xPixel1);
}if (!JSV.common.GraphSet.isGoodEvent (this.pin2Dx0, this.pin2Dx1, true)) {
this.reset2D (true);
return;
}this.imageView.setView0 (this.pin2Dx0.xPixel0, this.pin2Dy0.yPixel0, this.pin2Dx1.xPixel0, this.pin2Dy1.yPixel0);
this.doZoom (this.pin2Dx0.getXVal (), this.getScale ().minY, this.pin2Dx1.getXVal (), this.getScale ().maxY, false, false, false, true, false);
return;
}if (widget === this.pin2Dy0 || widget === this.pin2Dy1 || widget === this.pin2Dy01) {
yPixel = this.fixY (yPixel);
widget.setY (this.imageView.toSubspectrumIndex (yPixel), yPixel);
if (widget === this.pin2Dy01) {
var dp = yPixel - Clazz_doubleToInt ((this.pin2Dy0.yPixel0 + this.pin2Dy1.yPixel0) / 2) + 1;
yPixel = this.pin2Dy0.yPixel0 + dp;
var yPixel1 = this.pin2Dy1.yPixel0 + dp;
if (yPixel != this.fixY (yPixel) || yPixel1 != this.fixY (yPixel1)) return;
this.pin2Dy0.setY (this.imageView.toSubspectrumIndex (yPixel), yPixel);
this.pin2Dy1.setY (this.imageView.toSubspectrumIndex (yPixel1), yPixel1);
}if (!JSV.common.GraphSet.isGoodEvent (this.pin2Dy0, this.pin2Dy1, false)) {
this.reset2D (false);
return;
}this.imageView.setView0 (this.pin2Dx0.xPixel0, this.pin2Dy0.yPixel0, this.pin2Dx1.xPixel1, this.pin2Dy1.yPixel1);
return;
}return;
}, "~N,~N,~B");
$_M(c$, "clearIntegrals", 
function () {
this.checkIntegral (NaN, 0, false);
});
$_M(c$, "clearMeasurements", 
function () {
this.removeDialog (this.getFixedSelectedSpectrumIndex (), JSV.common.Annotation.AType.Measurements);
});
c$.createGraphSetsAndSetLinkMode = $_M(c$, "createGraphSetsAndSetLinkMode", 
function (pd, jsvp, spectra, startIndex, endIndex, linkMode) {
var graphSets =  new JU.List ();
for (var i = 0; i < spectra.size (); i++) {
var spec = spectra.get (i);
var graphSet = (linkMode === JSV.common.PanelData.LinkMode.NONE ? JSV.common.GraphSet.findCompatibleGraphSet (graphSets, spec) : null);
if (graphSet == null) graphSets.addLast (graphSet =  new JSV.common.GraphSet (jsvp.getPanelData ()));
graphSet.addSpec (spec);
}
JSV.common.GraphSet.setFractionalPositions (pd, graphSets, linkMode);
for (var i = graphSets.size (); --i >= 0; ) {
graphSets.get (i).initGraphSet (startIndex, endIndex);
J.util.Logger.info ("JSVGraphSet " + (i + 1) + " nSpectra = " + graphSets.get (i).nSpectra);
}
return graphSets;
}, "JSV.common.PanelData,JSV.api.JSVPanel,JU.List,~N,~N,JSV.common.PanelData.LinkMode");
$_M(c$, "drawGraphSet", 
function (gMain, gTop, width, height, left, right, top, bottom, isResized, taintedAll) {
this.zoomEnabled = this.pd.getBoolean (JSV.common.ScriptToken.ENABLEZOOM);
this.height = height * this.pd.scalingFactor;
this.width = width * this.pd.scalingFactor;
this.left = left * this.pd.scalingFactor;
this.right = right * this.pd.scalingFactor;
this.top = top * this.pd.scalingFactor;
this.bottom = bottom * this.pd.scalingFactor;
this.$haveSelectedSpectrum = false;
this.selectedSpectrumIntegrals = null;
this.selectedSpectrumMeasurements = null;
if (!this.pd.isPrinting && this.widgets != null) for (var j = 0; j < this.widgets.length; j++) if (this.widgets[j] != null) this.widgets[j].isVisible = false;

for (var iSplit = 0; iSplit < this.nSplit; iSplit++) {
this.setPositionForFrame (iSplit);
this.drawAll (gMain, gTop, iSplit, isResized || this.nSplit > 1, taintedAll);
}
this.setPositionForFrame (this.nSplit > 1 ? this.pd.currentSplitPoint : 0);
if (this.pd.isPrinting) return;
}, "~O,~O,~N,~N,~N,~N,~N,~N,~B,~B");
$_M(c$, "escapeKeyPressed", 
function (isDEL) {
if (this.zoomBox1D != null) this.zoomBox1D.xPixel0 = this.zoomBox1D.xPixel1 = 0;
if (this.zoomBox2D != null) this.zoomBox2D.xPixel0 = this.zoomBox2D.xPixel1 = 0;
if (!this.inPlotMove) return;
if (this.pendingMeasurement != null) {
this.pendingMeasurement = null;
return;
}this.pd.thisWidget = null;
this.pendingMeasurement = null;
if (this.selectedSpectrumMeasurements != null && this.selectedMeasurement != null) {
if (isDEL) this.selectedSpectrumMeasurements.clear (this.getScale ().minXOnScale, this.getScale ().maxXOnScale);
 else this.selectedSpectrumMeasurements.removeObj (this.selectedMeasurement);
this.selectedMeasurement = null;
this.updateDialog (JSV.common.Annotation.AType.Measurements, -1);
}if (this.selectedSpectrumIntegrals != null && this.selectedIntegral != null) {
if (isDEL) this.selectedSpectrumIntegrals.clear (this.getScale ().minXOnScale, this.getScale ().maxXOnScale);
 else this.selectedSpectrumIntegrals.removeObj (this.selectedIntegral);
this.selectedIntegral = null;
this.updateDialog (JSV.common.Annotation.AType.Integration, -1);
}}, "~B");
c$.findGraphSet = $_M(c$, "findGraphSet", 
function (graphSets, xPixel, yPixel) {
for (var i = graphSets.size (); --i >= 0; ) if (graphSets.get (i).hasPoint (xPixel, yPixel)) return graphSets.get (i);

return null;
}, "JU.List,~N,~N");
$_M(c$, "findMatchingPeakInfo", 
function (pi) {
var pi2 = null;
for (var i = 0; i < this.spectra.size (); i++) if ((pi2 = (this.spectra.get (i)).findMatchingPeakInfo (pi)) != null) break;

return pi2;
}, "JSV.common.PeakInfo");
$_M(c$, "getCurrentSpectrumIndex", 
function () {
return (this.nSpectra == 1 ? 0 : this.iSpectrumSelected);
});
$_M(c$, "getSelectedIntegral", 
function () {
return this.selectedIntegral;
});
$_M(c$, "getShowAnnotation", 
function (type, i) {
var id = this.getDialog (type, i);
return (id != null && id.getState ());
}, "JSV.common.Annotation.AType,~N");
$_M(c$, "hasFileLoaded", 
function (filePath) {
for (var i = this.spectra.size (); --i >= 0; ) if (this.spectra.get (i).getFilePathForwardSlash ().equals (filePath)) return true;

return false;
}, "~S");
$_M(c$, "haveSelectedSpectrum", 
function () {
return this.$haveSelectedSpectrum;
});
$_M(c$, "mouseClickedEvent", 
function (xPixel, yPixel, clickCount, isControlDown) {
this.selectedMeasurement = null;
this.selectedIntegral = null;
var isNextClick = this.nextClickForSetPeak;
this.nextClickForSetPeak = false;
if (this.checkArrowUpDownClick (xPixel, yPixel)) return;
this.lastClickX = NaN;
this.lastPixelX = 2147483647;
if (this.isSplitWidget (xPixel, yPixel)) {
this.splitStack (this.pd.graphSets, this.nSplit == 1);
return;
}var pw = this.getPinSelected (xPixel, yPixel);
if (pw != null) {
this.setWidgetValueByUser (pw);
return;
}var is2D = (this.imageView != null && xPixel == this.imageView.fixX (xPixel) && yPixel == this.fixY (yPixel));
if (clickCount == 2 && this.iSpectrumClicked == -1 && this.iPreviousSpectrumClicked >= 0) {
this.setSpectrumClicked (this.iPreviousSpectrumClicked);
}if (!is2D && isControlDown) {
this.setSpectrumClicked (this.iPreviousSpectrumClicked);
if (this.pendingMeasurement != null) {
this.processPendingMeasurement (xPixel, yPixel, -3);
} else if (this.iSpectrumClicked >= 0) {
this.processPendingMeasurement (xPixel, yPixel, 3);
}return;
}this.lastXMax = NaN;
if (clickCount == 2) {
if (is2D) {
if (this.sticky2Dcursor) {
this.addAnnotation (this.getAnnotation (this.imageView.toX (xPixel), this.imageView.toSubspectrumIndex (yPixel), this.pd.coordStr, false, true, 5, 5), true);
}this.sticky2Dcursor = !this.sticky2Dcursor;
this.set2DCrossHairs (xPixel, yPixel);
return;
}if (this.isInTopBar (xPixel, yPixel)) {
this.doZoom (this.toX0 (this.xPixel0), 0, this.toX0 (this.xPixel1), 0, true, false, false, true, true);
} else if (this.isInRightBar (xPixel, yPixel)) {
this.doZoom (this.getScale ().minXOnScale, this.viewList.get (0).getScale ().minYOnScale, this.getScale ().maxXOnScale, this.viewList.get (0).getScale ().maxYOnScale, true, true, false, false, false);
} else if (this.isInTopBar2D (xPixel, yPixel)) {
this.reset2D (true);
} else if (this.isInRightBar2D (xPixel, yPixel)) {
this.reset2D (false);
} else if (this.pendingMeasurement != null) {
this.processPendingMeasurement (xPixel, yPixel, -2);
} else if (this.iSpectrumClicked >= 0) {
this.processPendingMeasurement (xPixel, yPixel, 2);
}return;
}if (is2D) {
if (this.annotations != null) {
var xy =  new JSV.common.Coordinate ().set (this.imageView.toX (xPixel), this.imageView.toSubspectrumIndex (yPixel));
var a = this.findAnnotation2D (xy);
if (a != null && this.setAnnotationText (a)) {
return;
}}this.sticky2Dcursor = false;
this.set2DCrossHairs (xPixel, yPixel);
return;
}if (this.isInPlotRegion (xPixel, yPixel)) {
if (this.selectedSpectrumIntegrals != null && this.checkIntegralNormalizationClick (xPixel, yPixel)) return;
if (this.pendingMeasurement != null) {
this.processPendingMeasurement (xPixel, yPixel, 1);
return;
}this.setCoordClicked (xPixel, this.toX (xPixel), this.toY (yPixel));
this.updateDialog (JSV.common.Annotation.AType.PeakList, -1);
if (isNextClick) {
this.shiftSpectrum (NaN, NaN);
return;
}} else {
this.setCoordClicked (0, NaN, 0);
}this.pd.notifyPeakPickedListeners (null);
}, "~N,~N,~N,~B");
$_M(c$, "updateDialog", 
function (type, iSpec) {
var ad = this.getDialog (type, iSpec);
if (ad == null || !this.isVisible (ad)) return;
var xRange = this.toX (this.xPixel1) - this.toX (this.xPixel0);
var yOffset = (this.getSpectrum ().isInverted () ? this.yPixel1 - this.pd.mouseY : this.pd.mouseY - this.yPixel0);
(ad).update (this.pd.coordClicked, xRange, yOffset);
}, "JSV.common.Annotation.AType,~N");
$_M(c$, "isVisible", 
function (ad) {
return (Clazz_instanceOf (ad, JSV.dialog.JSVDialog) && ad.isVisible ());
}, "JSV.api.AnnotationData");
$_M(c$, "mouseReleasedEvent", 
function (xPixel, yPixel) {
if (this.pendingMeasurement != null) {
if (Math.abs (this.toPixelX (this.pendingMeasurement.getXVal ()) - xPixel) < 2) this.pendingMeasurement = null;
this.processPendingMeasurement (xPixel, yPixel, -2);
this.setToolTipForPixels (xPixel, yPixel);
return;
}if (this.pd.integralShiftMode != 0) {
this.pd.integralShiftMode = 0;
this.zoomBox1D.xPixel1 = this.zoomBox1D.xPixel0;
return;
}if (this.iSpectrumMovedTo >= 0) this.setScale (this.iSpectrumMovedTo);
var thisWidget = this.pd.thisWidget;
if (this.pd.isIntegralDrag) {
if (JSV.common.GraphSet.isGoodEvent (this.zoomBox1D, null, true)) {
this.checkIntegral (this.toX (this.zoomBox1D.xPixel0), this.toX (this.zoomBox1D.xPixel1), true);
}this.zoomBox1D.xPixel1 = this.zoomBox1D.xPixel0 = 0;
this.pendingIntegral = null;
this.pd.isIntegralDrag = false;
} else if (thisWidget === this.zoomBox2D) {
if (!JSV.common.GraphSet.isGoodEvent (this.zoomBox2D, null, true)) return;
this.imageView.setZoom (this.zoomBox2D.xPixel0, this.zoomBox2D.yPixel0, this.zoomBox2D.xPixel1, this.zoomBox2D.yPixel1);
this.zoomBox2D.xPixel1 = this.zoomBox2D.xPixel0;
this.doZoom (this.imageView.toX (this.imageView.xPixel0), this.getScale ().minY, this.imageView.toX (this.imageView.xPixel0 + this.imageView.xPixels - 1), this.getScale ().maxY, false, false, false, true, true);
} else if (thisWidget === this.zoomBox1D) {
if (!JSV.common.GraphSet.isGoodEvent (this.zoomBox1D, null, true)) return;
var x1 = this.zoomBox1D.xPixel1;
var doY = (this.pd.shiftPressed);
this.doZoom (this.toX (this.zoomBox1D.xPixel0), (doY ? this.toY (this.zoomBox1D.yPixel0) : 0), this.toX (x1), (doY ? this.toY (this.zoomBox1D.yPixel1) : 0), true, doY, true, true, true);
this.zoomBox1D.xPixel1 = this.zoomBox1D.xPixel0;
} else if (thisWidget === this.pin1Dx0 || thisWidget === this.pin1Dx1 || thisWidget === this.cur2Dx0 || thisWidget === this.cur2Dx1) {
this.addCurrentZoom ();
}}, "~N,~N");
$_M(c$, "mouseMovedEvent", 
function (xPixel, yPixel) {
if (this.nSpectra > 1) {
var iFrame = this.getSplitPoint (yPixel);
this.setPositionForFrame (iFrame);
this.setSpectrumMovedTo (this.nSplit > 1 ? iFrame : this.iSpectrumSelected);
if (this.iSpectrumMovedTo >= 0) this.setScale (this.iSpectrumMovedTo);
}this.inPlotMove = this.isInPlotRegion (xPixel, yPixel);
this.setXPixelMovedTo (1.7976931348623157E308, 1.7976931348623157E308, (this.inPlotMove ? xPixel : -1), -1);
if (this.inPlotMove) {
this.xValueMovedTo = this.toX (this.xPixelMovedTo);
this.yValueMovedTo = this.getSpectrum ().getYValueAt (this.xValueMovedTo);
}if (this.pd.integralShiftMode != 0) {
var ad = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
var xy = (ad.getData ()).getXYCoords ();
var y = xy[this.pd.integralShiftMode > 0 ? xy.length - 1 : 0].getYVal ();
(ad.getData ()).shiftY (this.pd.integralShiftMode, this.toPixelYint (y) + yPixel - (this.pd.integralShiftMode > 0 ? this.yPixelPlot1 : this.yPixelPlot0), this.yPixel0, this.yPixels);
} else if (this.pd.isIntegralDrag) {
} else if (this.pendingMeasurement != null) {
this.processPendingMeasurement (xPixel, yPixel, 0);
this.setToolTipForPixels (xPixel, yPixel);
} else {
this.selectedMeasurement = (this.inPlotMove && this.selectedSpectrumMeasurements != null ? this.findMeasurement (this.selectedSpectrumMeasurements, xPixel, yPixel, 0) : null);
this.selectedIntegral = null;
if (this.inPlotMove && this.selectedSpectrumIntegrals != null && this.selectedMeasurement == null) {
this.selectedIntegral = this.findMeasurement (this.selectedSpectrumIntegrals, xPixel, yPixel, 0);
if (this.selectedIntegral == null) this.selectedIntegral = this.findMeasurement (this.selectedSpectrumIntegrals, xPixel, yPixel, -5);
}this.setToolTipForPixels (xPixel, yPixel);
if (this.imageView == null) {
this.piMouseOver = null;
var iSpec = (this.nSplit > 1 ? this.iSpectrumMovedTo : this.iSpectrumClicked);
if (!this.isDrawNoSpectra () && iSpec >= 0) {
var spec = this.spectra.get (iSpec);
if (spec.getPeakList () != null) {
this.coordTemp.setXVal (this.toX (xPixel));
this.coordTemp.setYVal (this.toY (yPixel));
this.piMouseOver = spec.findPeakByCoord (xPixel, this.coordTemp);
}}} else {
if (!this.pd.display1D && this.sticky2Dcursor) this.set2DCrossHairs (xPixel, yPixel);
}}}, "~N,~N");
$_M(c$, "nextView", 
function () {
if (this.currentZoomIndex + 1 < this.viewList.size ()) this.setZoomTo (this.currentZoomIndex + 1);
});
$_M(c$, "previousView", 
function () {
if (this.currentZoomIndex > 0) this.setZoomTo (this.currentZoomIndex - 1);
});
$_M(c$, "resetView", 
function () {
this.setZoomTo (0);
});
$_M(c$, "removeAllHighlights", 
function () {
this.removeAllHighlights (null);
});
$_M(c$, "removeHighlight", 
function (index) {
this.highlights.remove (index);
}, "~N");
$_M(c$, "removeHighlight", 
function (x1, x2) {
for (var i = this.highlights.size (); --i >= 0; ) {
var h = this.highlights.get (i);
if (h.x1 == x1 && h.x2 == x2) this.highlights.remove (i);
}
}, "~N,~N");
$_M(c$, "scaleYBy", 
function (factor) {
if (this.imageView == null && !this.zoomEnabled) return;
this.viewData.scaleSpectrum (this.imageView == null ? this.iSpectrumSelected : -2, factor);
if (this.imageView != null) {
this.update2dImage (false);
this.resetPinsFromView ();
}this.pd.refresh ();
}, "~N");
$_M(c$, "selectSpectrum", 
function (filePath, type, model) {
var haveFound = false;
for (var i = this.spectra.size (); --i >= 0; ) if ((filePath == null || this.getSpectrumAt (i).getFilePathForwardSlash ().equals (filePath)) && (this.getSpectrumAt (i).matchesPeakTypeModel (type, model))) {
this.setSpectrumSelected (i);
if (this.nSplit > 1) this.splitStack (this.pd.graphSets, true);
haveFound = true;
}
if (this.nSpectra > 1 && !haveFound && this.iSpectrumSelected >= 0 && !this.pd.isCurrentGraphSet (this)) this.setSpectrumSelected (-2147483648);
return haveFound;
}, "~S,~S,~S");
$_M(c$, "selectPeakByFileIndex", 
function (filePath, index) {
var pi;
for (var i = this.spectra.size (); --i >= 0; ) if ((pi = this.getSpectrumAt (i).selectPeakByFileIndex (filePath, index)) != null) return pi;

return null;
}, "~S,~S");
$_M(c$, "setSelected", 
function (i) {
if (i < 0) {
this.bsSelected.clearAll ();
this.setSpectrumClicked (-1);
return;
}this.bsSelected.set (i);
this.setSpectrumClicked ((this.bsSelected.cardinality () == 1 ? i : -1));
if (this.nSplit > 1 && i >= 0) this.pd.currentSplitPoint = i;
}, "~N");
$_M(c$, "setSelectedIntegral", 
function (val) {
var spec = this.selectedIntegral.getSpectrum ();
this.getIntegrationGraph (this.getSpectrumIndex (spec)).setSelectedIntegral (this.selectedIntegral, val);
}, "~N");
$_M(c$, "setShowAnnotation", 
function (type, tfToggle) {
var id = this.getDialog (type, -1);
if (id == null) {
if (tfToggle != null && tfToggle !== Boolean.TRUE) return;
if (type === JSV.common.Annotation.AType.PeakList || type === JSV.common.Annotation.AType.Integration || type === JSV.common.Annotation.AType.Measurements) this.pd.showDialog (type);
return;
}if (tfToggle == null) {
if (Clazz_instanceOf (id, JSV.dialog.JSVDialog)) (id).setVisible (false);
 else id.setState (!id.getState ());
return;
}var isON = tfToggle.booleanValue ();
id.setState (isON);
if (isON || Clazz_instanceOf (id, JSV.dialog.JSVDialog)) this.pd.showDialog (type);
}, "JSV.common.Annotation.AType,Boolean");
$_M(c$, "checkIntegral", 
function (parameters, value) {
var spec = this.getSpectrum ();
if (!spec.canIntegrate () || this.reversePlot) return false;
var iSpec = this.getFixedSelectedSpectrumIndex ();
var ad = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
if (value == null) return true;
switch (JSV.common.IntegralData.IntMode.getMode (value.toUpperCase ())) {
case JSV.common.IntegralData.IntMode.ON:
this.integrate (iSpec, parameters);
break;
case JSV.common.IntegralData.IntMode.OFF:
this.integrate (iSpec, null);
break;
case JSV.common.IntegralData.IntMode.TOGGLE:
this.integrate (iSpec, ad == null ? parameters : null);
break;
case JSV.common.IntegralData.IntMode.AUTO:
if (ad == null) {
this.checkIntegral (parameters, "ON");
ad = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
}if (ad != null) (ad.getData ()).autoIntegrate ();
break;
case JSV.common.IntegralData.IntMode.LIST:
this.pd.showDialog (JSV.common.Annotation.AType.Integration);
break;
case JSV.common.IntegralData.IntMode.MARK:
if (ad == null) {
this.checkIntegral (parameters, "ON");
ad = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
}if (ad != null) (ad.getData ()).addMarks (value.substring (4).trim ());
break;
case JSV.common.IntegralData.IntMode.MIN:
if (ad != null) {
try {
var val = Double.parseDouble (JSV.common.ScriptToken.getTokens (value).get (1));
(ad.getData ()).setMinimumIntegral (val);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}break;
case JSV.common.IntegralData.IntMode.UPDATE:
if (ad != null) (ad.getData ()).update (parameters);
}
this.updateDialog (JSV.common.Annotation.AType.Integration, -1);
return true;
}, "JSV.common.Parameters,~S");
$_M(c$, "setSpectrum", 
function (iSpec, fromSplit) {
if (fromSplit) {
if (this.nSplit > 1) this.setSpectrumClicked (iSpec);
} else {
this.setSpectrumClicked (iSpec);
this.stackSelected = false;
this.showAllStacked = false;
}}, "~N,~B");
$_M(c$, "setSpectrumJDX", 
function (spec) {
var pt = this.getFixedSelectedSpectrumIndex ();
this.spectra.remove (pt);
this.spectra.add (pt, spec);
this.pendingMeasurement = null;
this.clearViews ();
this.viewData.newSpectrum (this.spectra);
}, "JSV.common.JDXSpectrum");
$_M(c$, "setZoom", 
function (x1, y1, x2, y2) {
this.setZoomTo (0);
if (x1 == 0 && x2 == 0 && y1 == 0 && y2 == 0) {
this.newPins ();
this.imageView = null;
x1 = this.getScale ().minXOnScale;
x2 = this.getScale ().maxXOnScale;
} else {
this.doZoom (x1, y1, x2, y2, true, (y1 != y2), false, true, true);
}}, "~N,~N,~N,~N");
$_M(c$, "shiftSpectrum", 
function (dx, x1) {
var spec = this.getSpectrum ();
if (!spec.isNMR () || !spec.is1D ()) return false;
if (x1 == 1.7976931348623157E308 || dx == 1.7976931348623157E308) {
dx = -spec.addSpecShift (0);
} else if (x1 == 4.9E-324) {
this.nextClickForSetPeak = true;
this.jsvp.showMessage ("Click on or beside a peak to set its chemical shift.", "Set Reference");
return false;
} else if (Double.isNaN (dx) || dx == 4.9E-324) {
var x0 = (dx == 4.9E-324 ? this.lastClickX : this.getNearestPeak (spec, this.lastClickX, this.toY (this.pd.mouseY)));
if (Double.isNaN (x0)) return false;
if (Double.isNaN (x1)) try {
var s = this.pd.getInput ("New chemical shift (set blank to reset)", "Set Reference", "" + x0).trim ();
if (s.length == 0) x1 = x0 - spec.addSpecShift (0);
 else x1 = Double.parseDouble (s);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return false;
} else {
throw e;
}
}
dx = x1 - x0;
}if (dx == 0) return false;
spec.addSpecShift (dx);
if (this.annotations != null) for (var i = this.annotations.size (); --i >= 0; ) if (this.annotations.get (i).spec === spec) this.annotations.get (i).addSpecShift (dx);

if (this.dialogs != null) for (var e, $e = this.dialogs.entrySet ().iterator (); $e.hasNext () && ((e = $e.next ()) || true);) if (e.getValue ().getSpectrum () === spec) e.getValue ().setSpecShift (dx);

this.getScale ().addSpecShift (dx);
if (!Double.isNaN (this.lastClickX)) this.lastClickX += dx;
this.updateDialogs ();
this.doZoom (0, this.getScale ().minYOnScale, 0, this.getScale ().maxYOnScale, true, true, false, true, false);
this.pd.repaint ();
return true;
}, "~N,~N");
$_M(c$, "toPeak", 
function (istep) {
istep *= (this.drawXAxisLeftToRight ? 1 : -1);
if (Double.isNaN (this.lastClickX)) this.lastClickX = this.lastPixelX = 0;
var spec = this.getSpectrum ();
var coord = this.setCoordClicked (this.lastPixelX, this.lastClickX, 0);
var iPeak = spec.setNextPeak (coord, istep);
if (iPeak < 0) return;
var peak = spec.getPeakList ().get (iPeak);
spec.setSelectedPeak (peak);
this.setCoordClicked (peak.getXPixel (), peak.getX (), 0);
this.pd.notifyPeakPickedListeners ( new JSV.common.PeakPickEvent (this.jsvp, this.pd.coordClicked, peak));
}, "~N");
$_M(c$, "scaleSelectedBy", 
function (f) {
for (var i = this.bsSelected.nextSetBit (0); i >= 0; i = this.bsSelected.nextSetBit (i + 1)) this.viewData.scaleSpectrum (i, f);

}, "~N");
$_V(c$, "toString", 
function () {
return "gs: " + this.nSpectra + " " + this.spectra + " " + this.spectra.get (0).getFilePath ();
});
$_M(c$, "setXPointer", 
function (spec, x) {
if (spec != null) this.setSpectrumClicked (this.getSpectrumIndex (spec));
this.xValueMovedTo = this.lastClickX = x;
this.lastPixelX = this.toPixelX (x);
this.setXPixelMovedTo (x, 1.7976931348623157E308, 0, 0);
this.yValueMovedTo = NaN;
}, "JSV.common.JDXSpectrum,~N");
$_M(c$, "setXPointer2", 
function (spec, x) {
if (spec != null) this.setSpectrumClicked (this.getSpectrumIndex (spec));
this.setXPixelMovedTo (1.7976931348623157E308, x, 0, 0);
}, "JSV.common.JDXSpectrum,~N");
$_M(c$, "hasCurrentMeasurement", 
function (type) {
return ((type === JSV.common.Annotation.AType.Integration ? this.selectedSpectrumIntegrals : this.selectedSpectrumMeasurements) != null);
}, "JSV.common.Annotation.AType");
$_M(c$, "getDialog", 
function (type, iSpec) {
if (iSpec == -1) iSpec = this.getCurrentSpectrumIndex ();
return (this.dialogs == null || iSpec < 0 ? null : this.dialogs.get (type + "_" + iSpec));
}, "JSV.common.Annotation.AType,~N");
$_M(c$, "removeDialog", 
function (iSpec, type) {
if (this.dialogs != null && iSpec >= 0) this.dialogs.remove (type + "_" + iSpec);
}, "~N,JSV.common.Annotation.AType");
$_M(c$, "addDialog", 
function (iSpec, type, dialog) {
if (this.dialogs == null) this.dialogs =  new java.util.Hashtable ();
var key = type + "_" + iSpec;
dialog.setGraphSetKey (key);
this.dialogs.put (key, dialog);
return dialog;
}, "~N,JSV.common.Annotation.AType,JSV.api.AnnotationData");
$_M(c$, "removeDialog", 
function (dialog) {
var key = dialog.getGraphSetKey ();
this.dialogs.remove (key);
var data = dialog.getData ();
if (data != null) this.dialogs.put (key, data);
}, "JSV.dialog.JSVDialog");
$_M(c$, "getPeakListing", 
function (iSpec, p, forceNew) {
if (iSpec < 0) iSpec = this.getCurrentSpectrumIndex ();
if (iSpec < 0) return null;
var dialog = this.getDialog (JSV.common.Annotation.AType.PeakList, -1);
if (dialog == null) {
if (!forceNew) return null;
this.addDialog (iSpec, JSV.common.Annotation.AType.PeakList, dialog =  new JSV.common.PeakData (JSV.common.Annotation.AType.PeakList, this.getSpectrum ()));
}(dialog.getData ()).setPeakList (p, -2147483648, this.viewData.getScale ());
if (Clazz_instanceOf (dialog, JSV.dialog.JSVDialog)) (dialog).setFields ();
return dialog.getData ();
}, "~N,JSV.common.Parameters,~B");
$_M(c$, "setPeakListing", 
function (tfToggle) {
var dialog = this.getDialog (JSV.common.Annotation.AType.PeakList, -1);
var ad = (Clazz_instanceOf (dialog, JSV.dialog.JSVDialog) ? dialog : null);
var isON = (tfToggle == null ? ad == null || !ad.isVisible () : tfToggle.booleanValue ());
if (isON) {
this.pd.showDialog (JSV.common.Annotation.AType.PeakList);
} else {
if (Clazz_instanceOf (dialog, JSV.dialog.JSVDialog)) (dialog).setVisible (false);
}}, "Boolean");
$_M(c$, "haveIntegralDisplayed", 
function (i) {
var ad = this.getDialog (JSV.common.Annotation.AType.Integration, i);
return (ad != null && ad.getState ());
}, "~N");
$_M(c$, "getIntegrationGraph", 
function (i) {
var ad = this.getDialog (JSV.common.Annotation.AType.Integration, i);
return (ad == null ? null : ad.getData ());
}, "~N");
$_M(c$, "setIntegrationRatios", 
function (value) {
var iSpec = this.getFixedSelectedSpectrumIndex ();
if (this.aIntegrationRatios == null) this.aIntegrationRatios =  new Array (this.nSpectra);
this.aIntegrationRatios[iSpec] = JSV.common.IntegralData.getIntegrationRatiosFromString (this.getSpectrum (), value);
}, "~S");
$_M(c$, "getIntegrationRatios", 
function (i) {
return (this.aIntegrationRatios == null ? null : this.aIntegrationRatios[i]);
}, "~N");
$_M(c$, "integrate", 
function (iSpec, parameters) {
var spec = this.getSpectrumAt (iSpec);
if (parameters == null || !spec.canIntegrate ()) {
this.removeDialog (iSpec, JSV.common.Annotation.AType.Integration);
return false;
}this.addDialog (iSpec, JSV.common.Annotation.AType.Integration,  new JSV.common.IntegralData (spec, parameters));
return true;
}, "~N,JSV.common.Parameters");
$_M(c$, "getIntegration", 
function (iSpec, p, forceNew) {
if (iSpec < 0) iSpec = this.getCurrentSpectrumIndex ();
if (iSpec < 0) return null;
var dialog = this.getDialog (JSV.common.Annotation.AType.Integration, -1);
if (dialog == null) {
if (!forceNew) return null;
dialog = this.addDialog (iSpec, JSV.common.Annotation.AType.Integration,  new JSV.common.IntegralData (this.getSpectrum (), p));
}return dialog.getData ();
}, "~N,JSV.common.Parameters,~B");
$_M(c$, "getMeasurementInfo", 
function (type, iSpec) {
var md;
switch (type) {
case JSV.common.Annotation.AType.PeakList:
md = this.getPeakListing (iSpec, null, false);
break;
case JSV.common.Annotation.AType.Integration:
md = this.getIntegration (iSpec, null, false);
break;
default:
return null;
}
if (md == null) return null;
var info =  new java.util.Hashtable ();
md.getInfo (info);
return info;
}, "JSV.common.Annotation.AType,~N");
$_M(c$, "getInfo", 
function (key, iSpec) {
var spectraInfo =  new java.util.Hashtable ();
if ("viewInfo".equalsIgnoreCase (key)) return this.getScale ().getInfo (spectraInfo);
var specInfo =  new JU.List ();
spectraInfo.put ("spectra", specInfo);
for (var i = 0; i < this.nSpectra; i++) {
if (iSpec >= 0 && i != iSpec) continue;
var spec = this.spectra.get (i);
var info = spec.getInfo (key);
if (iSpec >= 0 && key != null && (info.size () == 2 || key.equalsIgnoreCase ("id"))) {
if (info.size () == 2) info.remove ("id");
return info;
}JSV.common.Parameters.putInfo (key, info, "type", spec.getDataType ());
JSV.common.Parameters.putInfo (key, info, "titleLabel", spec.getTitleLabel ());
JSV.common.Parameters.putInfo (key, info, "filePath", spec.getFilePath ().$replace ('\\', '/'));
JSV.common.Parameters.putInfo (key, info, "PeakList", (JSV.common.Parameters.isMatch (key, "PeakList") ? this.getMeasurementInfo (JSV.common.Annotation.AType.PeakList, i) : null));
JSV.common.Parameters.putInfo (key, info, "Integration", (JSV.common.Parameters.isMatch (key, "Integration") ? this.getMeasurementInfo (JSV.common.Annotation.AType.Integration, i) : null));
if (iSpec >= 0) return info;
specInfo.addLast (info);
}
return spectraInfo;
}, "~S,~N");
$_M(c$, "getTitle", 
function (forPrinting) {
return (this.nSpectra == 1 || this.iSpectrumSelected >= 0 && (!forPrinting || this.nSplit == 1) ? this.getSpectrum ().getTitle () : null);
}, "~B");
$_M(c$, "getCurrentView", 
function () {
this.setScale (this.getFixedSelectedSpectrumIndex ());
return this.viewData.getScale ();
});
$_M(c$, "set2DXY", 
function (x, y, isLocked) {
if (this.gs2dLinkedX != null) this.cur1D2x1.setX (x, this.toPixelX (x));
if (this.gs2dLinkedY != null) this.cur1D2x2.setX (y, this.toPixelX (y));
this.cur1D2Locked = isLocked;
}, "~N,~N,~B");
$_M(c$, "dialogsToFront", 
function () {
if (this.dialogs == null) return;
for (var e, $e = this.dialogs.entrySet ().iterator (); $e.hasNext () && ((e = $e.next ()) || true);) {
var ad = e.getValue ();
if (this.isVisible (ad)) (ad).setVisible (true);
}
});
$_M(c$, "setPlotColors", 
function (oColors) {
var colors = oColors;
if (colors.length > this.nSpectra) {
var tmpPlotColors =  new Array (this.nSpectra);
System.arraycopy (colors, 0, tmpPlotColors, 0, this.nSpectra);
colors = tmpPlotColors;
} else if (this.nSpectra > colors.length) {
var tmpPlotColors =  new Array (this.nSpectra);
var numAdditionColors = this.nSpectra - colors.length;
System.arraycopy (colors, 0, tmpPlotColors, 0, colors.length);
for (var i = 0, j = colors.length; i < numAdditionColors; i++, j++) tmpPlotColors[j] = this.generateRandomColor ();

colors = tmpPlotColors;
}this.plotColors = colors;
}, "~O");
$_M(c$, "disposeImage", 
function () {
{
if (this.image2D != null)
this.image2D.parentNode.removeChild(this.image2D);
}this.image2D = null;
this.jsvp = null;
this.pd = null;
this.highlights = null;
this.plotColors = null;
});
$_M(c$, "generateRandomColor", 
function () {
while (true) {
var red = Clazz_doubleToInt (Math.random () * 255);
var green = Clazz_doubleToInt (Math.random () * 255);
var blue = Clazz_doubleToInt (Math.random () * 255);
var randomColor = this.g2d.getColor3 (red, green, blue);
if (randomColor.getRGB () != 0) return randomColor;
}
});
$_M(c$, "setPlotColor0", 
function (oColor) {
this.plotColors[0] = oColor;
}, "~O");
$_M(c$, "getPlotColor", 
function (index) {
if (index >= this.plotColors.length) return null;
return this.plotColors[index];
}, "~N");
$_M(c$, "setColorFromToken", 
function (og, whatColor) {
if (whatColor != null) this.g2d.setGraphicsColor (og, whatColor === JSV.common.ScriptToken.PLOTCOLOR ? this.plotColors[0] : this.pd.getColor (whatColor));
}, "~O,JSV.common.ScriptToken");
$_M(c$, "setPlotColor", 
function (og, i) {
var c;
switch (i) {
case -3:
c = JSV.common.GraphSet.veryLightGrey;
break;
case -2:
c = this.pd.BLACK;
break;
case -1:
c = this.pd.getColor (JSV.common.ScriptToken.INTEGRALPLOTCOLOR);
break;
default:
c = this.plotColors[i];
break;
}
this.g2d.setGraphicsColor (og, c);
}, "~O,~N");
$_M(c$, "draw2DImage", 
function () {
if (this.imageView != null) this.g2d.drawGrayScaleImage (this.gMain, this.image2D, this.imageView.xPixel0, this.imageView.yPixel0, this.imageView.xPixel0 + this.imageView.xPixels - 1, this.imageView.yPixel0 + this.imageView.yPixels - 1, this.imageView.xView1, this.imageView.yView1, this.imageView.xView2, this.imageView.yView2);
});
$_M(c$, "get2DImage", 
function () {
this.imageView =  new JSV.common.ImageView ();
this.imageView.set (this.viewList.get (0).getScale ());
if (!this.update2dImage (true)) return false;
this.imageView.resetZoom ();
this.sticky2Dcursor = true;
return true;
});
$_M(c$, "update2dImage", 
function (isCreation) {
this.imageView.set (this.viewData.getScale ());
var spec = this.getSpectrumAt (0);
var buffer = this.imageView.get2dBuffer (spec, !isCreation);
if (buffer == null) {
this.image2D = null;
this.imageView = null;
return false;
}if (isCreation) {
buffer = this.imageView.adjustView (spec, this.viewData);
this.imageView.resetView ();
}this.image2D = this.g2d.newGrayScaleImage (this.gMain, this.image2D, this.imageView.imageWidth, this.imageView.imageHeight, buffer);
this.setImageWindow ();
return true;
}, "~B");
$_M(c$, "getAnnotation", 
function (x, y, text, isPixels, is2d, offsetX, offsetY) {
return  new JSV.common.ColoredAnnotation ().setCA (x, y, this.getSpectrum (), text, this.pd.BLACK, isPixels, is2d, offsetX, offsetY);
}, "~N,~N,~S,~B,~B,~N,~N");
$_M(c$, "getAnnotation", 
function (args, lastAnnotation) {
return JSV.common.Annotation.getColoredAnnotation (this.g2d, this.getSpectrum (), args, lastAnnotation);
}, "JU.List,JSV.common.Annotation");
$_M(c$, "fillBox", 
function (g, x0, y0, x1, y1, whatColor) {
this.setColorFromToken (g, whatColor);
this.g2d.fillRect (g, Math.min (x0, x1), Math.min (y0, y1), Math.abs (x0 - x1), Math.abs (y0 - y1));
}, "~O,~N,~N,~N,~N,JSV.common.ScriptToken");
$_M(c$, "drawHandle", 
function (g, x, y, outlineOnly) {
if (outlineOnly) this.g2d.drawRect (g, x - 2, y - 2, 4, 4);
 else this.g2d.fillRect (g, x - 2, y - 2, 5, 5);
}, "~O,~N,~N,~B");
$_M(c$, "setCurrentBoxColor", 
function (g) {
this.g2d.setGraphicsColor (g, this.pd.BLACK);
}, "~O");
$_M(c$, "fillArrow", 
function (g, type, x, y, doFill) {
var f = 1;
switch (type) {
case JSV.common.GraphSet.ArrowType.LEFT:
case JSV.common.GraphSet.ArrowType.UP:
f = -1;
break;
}
var axPoints = [x - 5, x - 5, x + 5, x + 5, x + 8, x, x - 8];
var ayPoints = [y + 5 * f, y - f, y - f, y + 5 * f, y + 5 * f, y + 10 * f, y + 5 * f];
switch (type) {
case JSV.common.GraphSet.ArrowType.LEFT:
case JSV.common.GraphSet.ArrowType.RIGHT:
if (doFill) this.g2d.fillPolygon (g, ayPoints, axPoints, 7);
 else this.g2d.drawPolygon (g, ayPoints, axPoints, 7);
break;
case JSV.common.GraphSet.ArrowType.UP:
case JSV.common.GraphSet.ArrowType.DOWN:
if (doFill) this.g2d.fillPolygon (g, axPoints, ayPoints, 7);
 else this.g2d.drawPolygon (g, axPoints, ayPoints, 7);
}
}, "~O,JSV.common.GraphSet.ArrowType,~N,~N,~B");
$_M(c$, "fillCircle", 
function (g, x, y, doFill) {
if (doFill) this.g2d.fillCircle (g, x - 4, y - 4, 8);
 else this.g2d.drawCircle (g, x - 4, y - 4, 8);
}, "~O,~N,~N,~B");
$_M(c$, "setAnnotationColor", 
function (g, note, whatColor) {
if (whatColor != null) {
this.setColorFromToken (g, whatColor);
return;
}var color = null;
if (Clazz_instanceOf (note, JSV.common.ColoredAnnotation)) color = (note).getColor ();
if (color == null) color = this.pd.BLACK;
this.g2d.setGraphicsColor (g, color);
}, "~O,JSV.common.Annotation,JSV.common.ScriptToken");
c$.$GraphSet$Highlight$ = function () {
Clazz_pu$h ();
c$ = Clazz_decorateAsClass (function () {
Clazz_prepareCallback (this, arguments);
this.x1 = 0;
this.x2 = 0;
this.color = null;
this.spectrum = null;
Clazz_instantialize (this, arguments);
}, JSV.common.GraphSet, "Highlight");
$_V(c$, "toString", 
function () {
return "highlight " + this.x1 + " " + this.x2 + " " + this.spectrum;
});
Clazz_makeConstructor (c$, 
function (a, b, c, d) {
this.x1 = a;
this.x2 = b;
this.color = d;
this.spectrum = c;
}, "~N,~N,JSV.common.JDXSpectrum,javajs.api.GenericColor");
$_V(c$, "equals", 
function (a) {
if (!(Clazz_instanceOf (a, JSV.common.GraphSet.Highlight))) return false;
var b = a;
return ((b.x1 == this.x1) && (b.x2 == this.x2));
}, "~O");
c$ = Clazz_p0p ();
};
Clazz_pu$h ();
c$ = Clazz_declareType (JSV.common.GraphSet, "ArrowType", Enum);
Clazz_defineEnumConstant (c$, "LEFT", 0, []);
Clazz_defineEnumConstant (c$, "RIGHT", 1, []);
Clazz_defineEnumConstant (c$, "UP", 2, []);
Clazz_defineEnumConstant (c$, "DOWN", 3, []);
Clazz_defineEnumConstant (c$, "RESET", 4, []);
Clazz_defineEnumConstant (c$, "HOME", 5, []);
c$ = Clazz_p0p ();
c$.RT2 = c$.prototype.RT2 = Math.sqrt (2.0);
Clazz_defineStatics (c$,
"veryLightGrey", null,
"minNumOfPointsForZoom", 3,
"MIN_DRAG_PIXELS", 5,
"ONLINE_CUTOFF", 2);
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "AnnotationData");
Clazz_declarePackage ("JSV.common");
Clazz_load (["JU.List", "JSV.api.AnnotationData"], "JSV.common.MeasurementData", ["JU.AU", "$.DF", "JSV.common.Annotation", "$.Parameters"], function () {
c$ = Clazz_decorateAsClass (function () {
this.type = null;
this.spec = null;
this.units = null;
this.precision = 0;
this.myParams = null;
this.isON = true;
this.key = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "MeasurementData", JU.List, JSV.api.AnnotationData);
Clazz_makeConstructor (c$, 
function (type, spec) {
Clazz_superConstructor (this, JSV.common.MeasurementData, []);
this.type = type;
this.spec = spec;
this.myParams =  new JSV.common.Parameters ().setName ("MeasurementData");
}, "JSV.common.Annotation.AType,JSV.common.JDXSpectrum");
$_M(c$, "getMeasurements", 
function () {
return this;
});
$_V(c$, "getAType", 
function () {
return this.type;
});
$_V(c$, "getState", 
function () {
return this.isON;
});
$_V(c$, "setState", 
function (b) {
this.isON = b;
}, "~B");
$_M(c$, "setMeasurements", 
function (measurements) {
}, "JU.List");
$_V(c$, "getParameters", 
function () {
return this.myParams;
});
$_M(c$, "getDataHeader", 
function () {
return JSV.common.MeasurementData.HEADER;
});
$_M(c$, "getMeasurementListArray", 
function (units) {
this.units = units;
var ddata = this.getMeasurementListArrayReal (units);
var precisionX = (this.spec.isNMR () ? 4 : 2);
var precisionDX = (this.spec.isHNMR () && units.equals ("ppm") ? 4 : 2);
var data =  new Array (this.size ());
for (var i = this.size (); --i >= 0; ) data[i] = ["" + (i + 1), JU.DF.formatDecimalDbl (ddata[i][0], precisionX), JU.DF.formatDecimalDbl (ddata[i][1], precisionX), JU.DF.formatDecimalDbl (ddata[i][2], precisionDX)];

return data;
}, "~S");
$_M(c$, "getMeasurementListArrayReal", 
function (units) {
var toHz = this.spec.isNMR () && units.equalsIgnoreCase ("HZ");
var data = JU.AU.newDouble2 (this.size ());
for (var pt = 0, i = this.size (); --i >= 0; ) {
var y = this.get (i).getValue ();
if (toHz) y *= this.spec.observedFreq;
data[pt++] = [this.get (i).getXVal (), this.get (i).getXVal2 (), y];
}
return data;
}, "~S");
c$.checkParameters = $_M(c$, "checkParameters", 
function (md, p) {
if (md.size () == 0) return false;
var myParams = md.getParameters ();
switch (md.getAType ()) {
case JSV.common.Annotation.AType.Integration:
break;
case JSV.common.Annotation.AType.PeakList:
return (p.peakListInterpolation.equals (myParams.peakListInterpolation) && p.peakListThreshold == myParams.peakListThreshold);
case JSV.common.Annotation.AType.Measurements:
break;
case JSV.common.Annotation.AType.NONE:
}
return false;
}, "JSV.common.MeasurementData,JSV.common.ColorParameters");
$_V(c$, "getSpectrum", 
function () {
return this.spec;
});
$_V(c$, "getData", 
function () {
return this;
});
$_M(c$, "clear", 
function (x1, x2) {
for (var i = this.size (); --i >= 0; ) {
var $in = this.get (i);
if ($in.text.length == 0 || $in.overlaps (x1, x2)) {
this.remove (i);
}}
}, "~N,~N");
$_V(c$, "setSpecShift", 
function (dx) {
for (var i = this.size (); --i >= 0; ) {
var m = this.get (i);
var x = m.getXVal () + dx;
m.setXVal (x);
m.setValue (x);
m.text = JU.DF.formatDecimalDbl (x, this.precision);
}
}, "~N");
$_V(c$, "getGraphSetKey", 
function () {
return this.key;
});
$_V(c$, "setGraphSetKey", 
function (key) {
this.key = key;
}, "~S");
$_V(c$, "isVisible", 
function () {
return true;
});
$_M(c$, "getInfo", 
function (info) {
info.put ("header", this.getDataHeader ());
info.put ("table", this.getMeasurementListArrayReal ("ppm"));
if (this.units != null) info.put ("units", this.units);
}, "java.util.Map");
c$.HEADER = c$.prototype.HEADER = ["", "start", "end", "value"];
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.util.EventObject"], "JSV.common.PeakPickEvent", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.coord = null;
this.peakInfo = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PeakPickEvent", java.util.EventObject);
Clazz_makeConstructor (c$, 
function (source, coord, peakInfo) {
Clazz_superConstructor (this, JSV.common.PeakPickEvent, [source]);
this.coord = coord;
this.peakInfo = (peakInfo == null ? null : peakInfo);
}, "~O,JSV.common.Coordinate,JSV.common.PeakInfo");
$_M(c$, "getCoord", 
function () {
return this.coord;
});
$_M(c$, "getPeakInfo", 
function () {
return this.peakInfo;
});
$_V(c$, "toString", 
function () {
return (this.peakInfo == null ? null : this.peakInfo.toString ());
});
});
Clazz_declarePackage ("JSV.common");
c$ = Clazz_decorateAsClass (function () {
this.isub = 0;
this.title = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "SubSpecChangeEvent");
Clazz_makeConstructor (c$, 
function (isub, title) {
this.isub = isub;
this.title = title;
}, "~N,~S");
$_M(c$, "isValid", 
function () {
return (this.title != null);
});
$_M(c$, "getSubIndex", 
function () {
return this.isub;
});
$_V(c$, "toString", 
function () {
return this.title;
});
Clazz_declarePackage ("JSV.common");
c$ = Clazz_decorateAsClass (function () {
this.x1 = 0;
this.y1 = 0;
this.x2 = 0;
this.y2 = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "ZoomEvent");
Clazz_makeConstructor (c$, 
function (x1, y1, x2, y2) {
this.x1 = x1;
this.y1 = y1;
this.x2 = x2;
this.y2 = y2;
}, "~N,~N,~N,~N");
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.Parameters"], "JSV.common.ColorParameters", ["java.util.Hashtable", "$.StringTokenizer", "JU.CU", "$.List", "JSV.common.ScriptToken"], function () {
c$ = Clazz_decorateAsClass (function () {
this.titleFontName = null;
this.displayFontName = null;
this.elementColors = null;
this.plotColors = null;
this.isDefault = false;
Clazz_instantialize (this, arguments);
}, JSV.common, "ColorParameters", JSV.common.Parameters);
Clazz_makeConstructor (c$, 
function () {
Clazz_superConstructor (this, JSV.common.ColorParameters, []);
JSV.common.ColorParameters.BLACK = this.getColor3 (0, 0, 0);
JSV.common.ColorParameters.RED = this.getColor3 (255, 0, 0);
JSV.common.ColorParameters.LIGHT_GRAY = this.getColor3 (200, 200, 200);
JSV.common.ColorParameters.DARK_GRAY = this.getColor3 (80, 80, 80);
JSV.common.ColorParameters.BLUE = this.getColor3 (0, 0, 255);
JSV.common.ColorParameters.WHITE = this.getColor3 (255, 255, 255);
this.elementColors =  new java.util.Hashtable ();
this.setColor (JSV.common.ScriptToken.TITLECOLOR, JSV.common.ColorParameters.BLACK);
this.setColor (JSV.common.ScriptToken.UNITSCOLOR, JSV.common.ColorParameters.RED);
this.setColor (JSV.common.ScriptToken.SCALECOLOR, JSV.common.ColorParameters.BLACK);
this.setColor (JSV.common.ScriptToken.COORDINATESCOLOR, JSV.common.ColorParameters.RED);
this.setColor (JSV.common.ScriptToken.GRIDCOLOR, JSV.common.ColorParameters.LIGHT_GRAY);
this.setColor (JSV.common.ScriptToken.PLOTCOLOR, JSV.common.ColorParameters.BLUE);
this.setColor (JSV.common.ScriptToken.PLOTAREACOLOR, JSV.common.ColorParameters.WHITE);
this.setColor (JSV.common.ScriptToken.BACKGROUNDCOLOR, this.getColor3 (192, 192, 192));
this.setColor (JSV.common.ScriptToken.INTEGRALPLOTCOLOR, JSV.common.ColorParameters.RED);
this.setColor (JSV.common.ScriptToken.PEAKTABCOLOR, JSV.common.ColorParameters.RED);
this.setColor (JSV.common.ScriptToken.HIGHLIGHTCOLOR, JSV.common.ColorParameters.DARK_GRAY);
for (var i = 0; i < 8; i++) JSV.common.ColorParameters.defaultPlotColors[i] = this.getColorFromString (JSV.common.ColorParameters.defaultPlotColorNames[i]);

this.plotColors =  new Array (8);
System.arraycopy (JSV.common.ColorParameters.defaultPlotColors, 0, this.plotColors, 0, 8);
});
$_M(c$, "setFor", 
function (jsvp, ds, includeMeasures) {
if (ds == null) ds = this;
if (includeMeasures) jsvp.getPanelData ().setBooleans (ds, null);
var pd = jsvp.getPanelData ();
if (pd.getCurrentPlotColor (1) != null) pd.setPlotColors (this.plotColors);
pd.setColorOrFont (ds, null);
}, "JSV.api.JSVPanel,JSV.common.ColorParameters,~B");
$_M(c$, "set", 
function (pd, st, value) {
var param = null;
switch (st) {
default:
this.setP (pd, st, value);
return;
case JSV.common.ScriptToken.PLOTCOLORS:
if (pd == null) this.getPlotColors (value);
 else pd.setPlotColors (this.getPlotColors (value));
return;
case JSV.common.ScriptToken.BACKGROUNDCOLOR:
case JSV.common.ScriptToken.COORDINATESCOLOR:
case JSV.common.ScriptToken.GRIDCOLOR:
case JSV.common.ScriptToken.HIGHLIGHTCOLOR:
case JSV.common.ScriptToken.INTEGRALPLOTCOLOR:
case JSV.common.ScriptToken.PEAKTABCOLOR:
case JSV.common.ScriptToken.PLOTAREACOLOR:
case JSV.common.ScriptToken.PLOTCOLOR:
case JSV.common.ScriptToken.SCALECOLOR:
case JSV.common.ScriptToken.TITLECOLOR:
case JSV.common.ScriptToken.UNITSCOLOR:
param = this.setColorFromString (st, value);
break;
case JSV.common.ScriptToken.TITLEFONTNAME:
case JSV.common.ScriptToken.DISPLAYFONTNAME:
param = this.getFontName (st, value);
break;
}
if (pd == null) return;
if (param != null) pd.setColorOrFont (this, st);
}, "JSV.common.PanelData,JSV.common.ScriptToken,~S");
$_M(c$, "getElementColor", 
function (st) {
return this.elementColors.get (st);
}, "JSV.common.ScriptToken");
$_M(c$, "setColor", 
function (st, color) {
if (color != null) this.elementColors.put (st, color);
return color;
}, "JSV.common.ScriptToken,javajs.api.GenericColor");
$_M(c$, "copy", 
function () {
return this.copy (this.name);
});
$_M(c$, "setElementColors", 
function (p) {
this.displayFontName = p.displayFontName;
for (var entry, $entry = p.elementColors.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) this.setColor (entry.getKey (), entry.getValue ());

return this;
}, "JSV.common.ColorParameters");
$_M(c$, "getColorFromString", 
function (name) {
return this.getColor1 (JU.CU.getArgbFromString (name));
}, "~S");
$_M(c$, "getPlotColors", 
function (plotColorsStr) {
if (plotColorsStr == null) {
this.plotColors[0] = this.getElementColor (JSV.common.ScriptToken.PLOTCOLOR);
return this.plotColors;
}var st =  new java.util.StringTokenizer (plotColorsStr, ",;.- ");
var colors =  new JU.List ();
try {
while (st.hasMoreTokens ()) colors.addLast (this.getColorFromString (st.nextToken ()));

} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return null;
} else {
throw e;
}
}
return colors.toArray ( new Array (colors.size ()));
}, "~S");
$_M(c$, "setColorFromString", 
function (st, value) {
return this.setColor (st, this.getColorFromString (value));
}, "JSV.common.ScriptToken,~S");
$_M(c$, "getFontName", 
function (st, value) {
var isValid = this.isValidFontName (value);
switch (st) {
case JSV.common.ScriptToken.TITLEFONTNAME:
return (isValid ? this.titleFontName = value : this.titleFontName);
case JSV.common.ScriptToken.DISPLAYFONTNAME:
return (isValid ? this.displayFontName = value : this.displayFontName);
}
return null;
}, "JSV.common.ScriptToken,~S");
Clazz_defineStatics (c$,
"BLACK", null,
"RED", null,
"LIGHT_GRAY", null,
"DARK_GRAY", null,
"BLUE", null,
"WHITE", null);
c$.defaultPlotColors = c$.prototype.defaultPlotColors =  new Array (8);
c$.defaultPlotColorNames = c$.prototype.defaultPlotColorNames = ["black", "darkGreen", "darkred", "orange", "magenta", "cyan", "maroon", "darkGray"];
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.Annotation"], "JSV.common.ColoredAnnotation", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.color = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "ColoredAnnotation", JSV.common.Annotation);
$_M(c$, "getColor", 
function () {
return this.color;
});
Clazz_makeConstructor (c$, 
function () {
Clazz_superConstructor (this, JSV.common.ColoredAnnotation, []);
});
$_M(c$, "setCA", 
function (x, y, spec, text, color, isPixels, is2D, offsetX, offsetY) {
this.setA (x, y, spec, text, isPixels, is2D, offsetX, offsetY);
this.color = color;
return this;
}, "~N,~N,JSV.common.JDXSpectrum,~S,javajs.api.GenericColor,~B,~B,~N,~N");
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.api.XYScaleConverter"], "JSV.common.ImageView", ["java.lang.Double", "JSV.common.Coordinate", "$.ScaleData"], function () {
c$ = Clazz_decorateAsClass (function () {
this.buf2d = null;
this.grayFactorLast = 0;
this.averageGray = 0;
this.xPixel0 = 0;
this.yPixel0 = 0;
this.xPixel1 = 0;
this.yPixel1 = 0;
this.imageWidth = 0;
this.imageHeight = 0;
this.xPixels = 0;
this.yPixels = 0;
this.xPixelZoom1 = 0;
this.yPixelZoom1 = 0;
this.xPixelZoom2 = 0;
this.yPixelZoom2 = 0;
this.xView1 = 0;
this.yView1 = 0;
this.xView2 = 0;
this.yView2 = 0;
this.minX = NaN;
this.maxX = 0;
this.minY = 0;
this.maxY = 0;
this.minZ = 0;
this.maxZ = 0;
this.scaleData = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "ImageView", null, JSV.api.XYScaleConverter);
$_M(c$, "set", 
function (view) {
if (Double.isNaN (this.minX)) {
this.minX = view.minX;
this.maxX = view.maxX;
}this.minZ = view.minY;
this.maxZ = view.maxY;
this.scaleData =  new JSV.common.ScaleData ();
}, "JSV.common.ScaleData");
$_M(c$, "setZoom", 
function (xPixel1, yPixel1, xPixel2, yPixel2) {
this.xPixelZoom1 = Math.min (xPixel1, xPixel2);
this.yPixelZoom1 = Math.min (yPixel1, yPixel2);
this.xPixelZoom2 = Math.max (xPixel1, xPixel2);
this.yPixelZoom2 = Math.max (yPixel1, yPixel2);
this.setView ();
}, "~N,~N,~N,~N");
$_M(c$, "setXY0", 
function (spec, xPixel, yPixel) {
this.xPixel0 = xPixel;
this.yPixel0 = yPixel;
this.xPixel1 = this.xPixel0 + this.xPixels - 1;
this.yPixel1 = this.yPixel0 + this.yPixels - 1;
this.setMinMaxY (spec);
}, "JSV.common.JDXSpectrum,~N,~N");
$_M(c$, "setPixelWidthHeight", 
function (xPixels, yPixels) {
this.xPixels = xPixels;
this.yPixels = yPixels;
}, "~N,~N");
$_M(c$, "resetView", 
function () {
this.xView1 = 0;
this.yView1 = 0;
this.xView2 = this.imageWidth - 1;
this.yView2 = this.imageHeight - 1;
});
$_M(c$, "setView", 
function () {
if (this.xPixelZoom1 == 0) this.resetZoom ();
var x1 = this.toImageX (this.xPixelZoom1);
var y1 = this.toImageY (this.yPixelZoom1);
var x2 = this.toImageX (this.xPixelZoom2);
var y2 = this.toImageY (this.yPixelZoom2);
this.xView1 = Math.min (x1, x2);
this.yView1 = Math.min (y1, y2);
this.xView2 = Math.max (x1, x2);
this.yView2 = Math.max (y1, y2);
this.setScaleData ();
this.resetZoom ();
});
$_M(c$, "resetZoom", 
function () {
this.xPixelZoom1 = this.xPixel0;
this.yPixelZoom1 = this.yPixel0;
this.xPixelZoom2 = this.xPixel1;
this.yPixelZoom2 = this.yPixel1;
});
$_M(c$, "toImageX", 
function (xPixel) {
return this.xView1 + Clazz_doubleToInt (Math.floor ((xPixel - this.xPixel0) / (this.xPixels - 1.0) * (this.xView2 - this.xView1)));
}, "~N");
$_M(c$, "toImageY", 
function (yPixel) {
return this.yView1 + Clazz_doubleToInt (Math.floor ((yPixel - this.yPixel0) / (this.yPixels - 1.0) * (this.yView2 - this.yView1)));
}, "~N");
$_M(c$, "toImageX0", 
function (xPixel) {
return JSV.common.Coordinate.intoRange (Clazz_doubleToInt ((1.0 * xPixel - this.xPixel0) / (this.xPixels - 1) * (this.imageWidth - 1)), 0, this.imageWidth - 1);
}, "~N");
$_M(c$, "toImageY0", 
function (yPixel) {
return JSV.common.Coordinate.intoRange (Clazz_doubleToInt ((1.0 * yPixel - this.yPixel0) / (this.yPixels - 1) * (this.imageHeight - 1)), 0, this.imageHeight - 1);
}, "~N");
$_M(c$, "isXWithinRange", 
function (xPixel) {
return (xPixel >= this.xPixel0 - 5 && xPixel < this.xPixel0 + this.xPixels + 5);
}, "~N");
$_M(c$, "toSubspectrumIndex", 
function (yPixel) {
return JSV.common.Coordinate.intoRange (this.imageHeight - 1 - this.toImageY (yPixel), 0, this.imageHeight - 1);
}, "~N");
$_M(c$, "toX0", 
function (xPixel) {
return this.maxX + (this.minX - this.maxX) * (this.fixX (xPixel) - this.xPixel0) / (this.xPixels - 1);
}, "~N");
$_M(c$, "toPixelX0", 
function (x) {
return this.xPixel1 - Clazz_doubleToInt ((x - this.minX) / (this.maxX - this.minX) * (this.xPixels - 1));
}, "~N");
$_M(c$, "toPixelY0", 
function (ysub) {
return this.yPixel1 - Clazz_doubleToInt (ysub / (this.imageHeight - 1) * (this.yPixels - 1));
}, "~N");
$_M(c$, "subIndexToPixelY", 
function (subIndex) {
var f = 1.0 * (this.imageHeight - 1 - subIndex - this.yView1) / (this.yView2 - this.yView1);
var y = this.yPixel0 + Clazz_doubleToInt (f * (this.yPixels - 1));
return y;
}, "~N");
$_M(c$, "fixSubIndex", 
function (subIndex) {
return JSV.common.Coordinate.intoRange (subIndex, this.imageHeight - 1 - this.yView2, this.imageHeight - 1 - this.yView1);
}, "~N");
$_V(c$, "fixX", 
function (xPixel) {
return (xPixel < this.xPixel0 ? this.xPixel0 : xPixel > this.xPixel1 ? this.xPixel1 : xPixel);
}, "~N");
$_V(c$, "toX", 
function (xPixel) {
return this.maxX + (this.minX - this.maxX) * this.toImageX (this.fixX (xPixel)) / (this.imageWidth - 1);
}, "~N");
$_V(c$, "toPixelX", 
function (x) {
var x0 = this.toX (this.xPixel0);
var x1 = this.toX (this.xPixel1);
return this.xPixel0 + Clazz_doubleToInt ((x - x0) / (x1 - x0) * (this.xPixels - 1));
}, "~N");
$_M(c$, "setView0", 
function (xp1, yp1, xp2, yp2) {
var x1 = this.toImageX0 (xp1);
var y1 = this.toImageY0 (yp1);
var x2 = this.toImageX0 (xp2);
var y2 = this.toImageY0 (yp2);
this.xView1 = Math.min (x1, x2);
this.yView1 = Math.min (y1, y2);
this.xView2 = Math.max (x1, x2);
this.yView2 = Math.max (y1, y2);
this.resetZoom ();
}, "~N,~N,~N,~N");
$_M(c$, "get2dBuffer", 
function (spec, forceNew) {
var subSpectra = spec.getSubSpectra ();
if (subSpectra == null || !subSpectra.get (0).isContinuous ()) return null;
var xyCoords = spec.getXYCoords ();
var nSpec = subSpectra.size ();
this.imageWidth = xyCoords.length;
this.imageHeight = nSpec;
var grayFactor = 255 / (this.maxZ - this.minZ);
if (!forceNew && this.buf2d != null && grayFactor == this.grayFactorLast) return this.buf2d;
this.grayFactorLast = grayFactor;
var pt = this.imageWidth * this.imageHeight;
var buf =  Clazz_newIntArray (pt, 0);
var totalGray = 0;
for (var i = 0; i < nSpec; i++) {
var points = subSpectra.get (i).xyCoords;
if (points.length != xyCoords.length) return null;
var f = subSpectra.get (i).getUserYFactor ();
for (var j = 0; j < xyCoords.length; j++) {
var y = points[j].getYVal ();
var gray = 255 - JSV.common.Coordinate.intoRange (Clazz_doubleToInt ((y * f - this.minZ) * grayFactor), 0, 255);
buf[--pt] = gray;
totalGray += gray;
}
}
this.averageGray = (1 - totalGray / (this.imageWidth * this.imageHeight) / 255);
System.out.println ("Average gray = " + this.averageGray);
return (this.buf2d = buf);
}, "JSV.common.JDXSpectrum,~B");
$_M(c$, "adjustView", 
function (spec, view) {
var i = 0;
var isLow = false;
while (((isLow = (this.averageGray < 0.05)) || this.averageGray > 0.3) && i++ < 10) {
view.scaleSpectrum (-2, isLow ? 2 : 0.5);
this.set (view.getScale ());
this.get2dBuffer (spec, false);
}
return this.buf2d;
}, "JSV.common.JDXSpectrum,JSV.common.ViewData");
$_M(c$, "getBuffer", 
function () {
return this.buf2d;
});
$_M(c$, "setMinMaxY", 
function (spec) {
var subSpectra = spec.getSubSpectra ();
var spec0 = subSpectra.get (0);
this.maxY = spec0.getY2D ();
this.minY = subSpectra.get (subSpectra.size () - 1).getY2D ();
if (spec0.y2DUnits.equalsIgnoreCase ("Hz")) {
this.maxY /= spec0.freq2dY;
this.minY /= spec0.freq2dY;
}this.setScaleData ();
}, "JSV.common.JDXSpectrum");
$_M(c$, "setScaleData", 
function () {
this.scaleData.minY = this.minY;
this.scaleData.maxY = this.maxY;
this.scaleData.setYScale (this.toY (this.yPixel0), this.toY (this.yPixel1), false, false);
});
$_V(c$, "getScale", 
function () {
return this.scaleData;
});
$_V(c$, "getYPixels", 
function () {
return this.yPixels;
});
$_V(c$, "getXPixels", 
function () {
return this.xPixels;
});
$_V(c$, "getXPixel0", 
function () {
return this.xPixel0;
});
$_V(c$, "toY", 
function (yPixel) {
var isub = this.toSubspectrumIndex (yPixel);
return this.maxY + (this.minY - this.maxY) * isub / (this.imageWidth - 1);
}, "~N");
$_V(c$, "fixY", 
function (yPixel) {
return JSV.common.Coordinate.intoRange (yPixel, this.yPixel0, this.yPixel1);
}, "~N");
$_V(c$, "toPixelY", 
function (y) {
var f = (y - this.scaleData.minYOnScale) / (this.scaleData.maxYOnScale - this.scaleData.minYOnScale);
return Clazz_doubleToInt (this.yPixel0 + f * this.yPixels);
}, "~N");
Clazz_defineStatics (c$,
"DEFAULT_MIN_GRAY", 0.05,
"DEFAULT_MAX_GRAY", 0.30);
});
Clazz_declarePackage ("JSV.common");
c$ = Clazz_declareType (JSV.common, "IntegralComparator", null, java.util.Comparator);
$_V(c$, "compare", 
function (m1, m2) {
return (m1.getXVal () < m2.getXVal () ? -1 : m1.getXVal () > m2.getXVal () ? 1 : 0);
}, "JSV.common.Measurement,JSV.common.Measurement");
Clazz_declarePackage ("JSV.common");
Clazz_load (["java.lang.Enum", "JSV.common.MeasurementData", "$.IntegralComparator"], "JSV.common.IntegralData", ["java.lang.Double", "java.util.Collections", "$.StringTokenizer", "JU.AU", "$.BS", "$.DF", "$.List", "$.PT", "JSV.common.Annotation", "$.Coordinate", "$.Integral", "$.ScriptToken"], function () {
c$ = Clazz_decorateAsClass (function () {
this.percentMinY = 0;
this.percentOffset = 0;
this.intRange = 0;
this.normalizationFactor = 1;
this.percentRange = 0;
this.offset = 0;
this.integralTotal = 0;
this.haveRegions = false;
this.xyCoords = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "IntegralData", JSV.common.MeasurementData);
$_M(c$, "getPercentMinimumY", 
function () {
return this.percentMinY;
});
$_M(c$, "getPercentOffset", 
function () {
return this.percentOffset;
});
$_M(c$, "getIntegralFactor", 
function () {
return this.intRange;
});
Clazz_makeConstructor (c$, 
function (integralMinY, integralOffset, integralRange, spec) {
Clazz_superConstructor (this, JSV.common.IntegralData, [JSV.common.Annotation.AType.Integration, spec]);
this.percentMinY = integralMinY;
this.percentOffset = integralOffset;
this.percentRange = integralRange;
this.calculateIntegral ();
}, "~N,~N,~N,JSV.common.JDXSpectrum");
Clazz_makeConstructor (c$, 
function (spec, p) {
Clazz_superConstructor (this, JSV.common.IntegralData, [JSV.common.Annotation.AType.Integration, spec]);
if (p == null) {
this.autoIntegrate ();
return;
}this.percentOffset = p.integralOffset;
this.percentRange = p.integralRange;
this.calculateIntegral ();
}, "JSV.common.JDXSpectrum,JSV.common.Parameters");
$_M(c$, "update", 
function (parameters) {
this.update (parameters.integralMinY, parameters.integralOffset, parameters.integralRange);
}, "JSV.common.Parameters");
$_M(c$, "update", 
function (integralMinY, integralOffset, integralRange) {
var percentRange0 = this.percentRange;
if (integralRange <= 0 || integralRange == this.percentRange && integralOffset == this.percentOffset) return;
this.percentOffset = integralOffset;
this.percentRange = integralRange;
this.checkRange ();
var intRangeNew = integralRange / 100 / this.integralTotal;
var offsetNew = integralOffset / 100;
for (var i = 0; i < this.xyCoords.length; i++) {
var y = this.xyCoords[i].getYVal ();
y = (y - this.offset) / this.intRange;
this.xyCoords[i].setYVal (y * intRangeNew + offsetNew);
}
if (this.normalizationFactor != 1) this.normalizationFactor *= percentRange0 / integralRange;
if (this.haveRegions) {
for (var i = this.size (); --i >= 0; ) {
var ir = this.get (i);
var y1 = this.getYValueAt (ir.getXVal ());
var y2 = this.getYValueAt (ir.getXVal2 ());
ir.setYVal (y1);
ir.setYVal2 (y2);
ir.setValue (Math.abs (y2 - y1) * 100 * this.normalizationFactor);
}
}this.intRange = intRangeNew;
this.offset = offsetNew;
}, "~N,~N,~N");
$_M(c$, "getYValueAt", 
function (x) {
return JSV.common.Coordinate.getYValueAt (this.xyCoords, x);
}, "~N");
$_M(c$, "addIntegralRegion", 
function (x1, x2) {
if (Double.isNaN (x1)) {
this.haveRegions = false;
this.clear ();
return null;
}if (x1 == x2) return null;
var y1 = this.getYValueAt (x1);
var y2 = this.getYValueAt (x2);
this.haveRegions = true;
var $in =  new JSV.common.Integral ().setInt (x1, y1, this.spec, Math.abs (y2 - y1) * 100 * this.normalizationFactor, x2, y2);
this.clear (x1, x2);
this.addLast ($in);
java.util.Collections.sort (this, JSV.common.IntegralData.c);
return $in;
}, "~N,~N");
$_V(c$, "setSpecShift", 
function (dx) {
JSV.common.Coordinate.shiftX (this.xyCoords, dx);
for (var i = this.size (); --i >= 1; ) {
this.get (i).addSpecShift (dx);
}
}, "~N");
$_M(c$, "addMarks", 
function (ppms) {
ppms = JU.PT.simpleReplace (" " + ppms, ",", " ");
ppms = JU.PT.simpleReplace (ppms, " -", " #");
ppms = JU.PT.simpleReplace (ppms, "--", "-#");
ppms = ppms.$replace ('-', '^');
ppms = ppms.$replace ('#', '-');
var tokens = JSV.common.ScriptToken.getTokens (ppms);
for (var i = 0; i < tokens.size (); i++) {
try {
var s = tokens.get (i);
var norm = 0;
var pt = s.indexOf ('^');
if (pt < 0) continue;
var pt2 = s.indexOf (':');
if (pt2 > pt) {
norm = Double.$valueOf (s.substring (pt2 + 1).trim ()).doubleValue ();
s = s.substring (0, pt2).trim ();
}var x2 = Double.$valueOf (s.substring (0, pt).trim ()).doubleValue ();
var x1 = Double.$valueOf (s.substring (pt + 1).trim ()).doubleValue ();
if (x1 == 0 && x2 == 0) this.clear ();
if (x1 == x2) continue;
var m = this.addIntegralRegion (Math.max (x1, x2), Math.min (x1, x2));
if (m != null && norm > 0) this.setSelectedIntegral (m, norm);
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
continue;
} else {
throw e;
}
}
}
}, "~S");
$_M(c$, "calculateIntegral", 
function () {
var specXyCoords = this.spec.getXYCoords ();
this.xyCoords =  new Array (specXyCoords.length);
var minYForIntegral = -1.7976931348623157E308;
this.integralTotal = 0;
this.checkRange ();
var minY = 1E100;
for (var i = 0; i < specXyCoords.length; i++) {
var y = specXyCoords[i].getYVal ();
if (y < minY && y >= 0) minY = y;
}
for (var i = 0; i < specXyCoords.length; i++) {
var y = specXyCoords[i].getYVal ();
if (y > minYForIntegral) this.integralTotal += (y - minY);
}
this.intRange = (this.percentRange / 100) / this.integralTotal;
this.offset = (this.percentOffset / 100);
var integral = 0;
for (var i = specXyCoords.length; --i >= 0; ) {
var y = specXyCoords[i].getYVal ();
if (y > minYForIntegral) integral += (y - minY);
this.xyCoords[i] =  new JSV.common.Coordinate ().set (specXyCoords[i].getXVal (), integral * this.intRange + this.offset);
}
return this.xyCoords;
});
$_M(c$, "checkRange", 
function () {
this.percentOffset = Math.max (5, this.percentOffset);
this.percentRange = Math.max (10, this.percentRange);
});
c$.getIntegrationRatiosFromString = $_M(c$, "getIntegrationRatiosFromString", 
function (spec, value) {
var ratios =  new JU.List ();
var allParamTokens =  new java.util.StringTokenizer (value, ",");
while (allParamTokens.hasMoreTokens ()) {
var token = allParamTokens.nextToken ();
var eachParam =  new java.util.StringTokenizer (token, ":");
var ratio =  new JSV.common.Annotation ().setA (Double.parseDouble (eachParam.nextToken ()), 0.0, spec, eachParam.nextToken (), true, false, 0, 0);
ratios.addLast (ratio);
}
return ratios;
}, "JSV.common.JDXSpectrum,~S");
$_M(c$, "getXYCoords", 
function () {
return this.xyCoords;
});
$_M(c$, "getPercentYValueAt", 
function (x) {
return this.getYValueAt (x) * 100;
}, "~N");
$_M(c$, "dispose", 
function () {
this.spec = null;
this.xyCoords = null;
});
$_M(c$, "setSelectedIntegral", 
function (integral, val) {
var val0 = integral.getValue ();
var factor = (val <= 0 ? 1 / this.normalizationFactor : val / val0);
this.factorAllIntegrals (factor, val <= 0);
}, "JSV.common.Measurement,~N");
$_M(c$, "factorAllIntegrals", 
function (factor, isReset) {
for (var i = 0; i < this.size (); i++) {
var m = this.get (i);
m.setValue (factor * m.getValue ());
}
this.normalizationFactor = (isReset ? 1 : this.normalizationFactor * factor);
}, "~N,~B");
$_M(c$, "getBitSet", 
function () {
var bs = JU.BS.newN (this.xyCoords.length);
if (this.size () == 0) {
bs.setBits (0, this.xyCoords.length);
return bs;
}for (var i = this.size (); --i >= 0; ) {
var m = this.get (i);
var x1 = JSV.common.Coordinate.getNearestIndexForX (this.xyCoords, m.getXVal ());
var x2 = JSV.common.Coordinate.getNearestIndexForX (this.xyCoords, m.getXVal2 ());
bs.setBits (Math.min (x1, x2), Math.max (x1, x2));
}
return bs;
});
$_V(c$, "getMeasurementListArray", 
function (units) {
var data =  new Array (this.size ());
for (var pt = 0, i = this.size (); --i >= 0; ) data[pt++] = ["" + pt, JU.DF.formatDecimalDbl (this.get (i).getXVal (), 2), JU.DF.formatDecimalDbl (this.get (i).getXVal2 (), 2), this.get (i).text];

return data;
}, "~S");
$_V(c$, "getMeasurementListArrayReal", 
function (units) {
var data = JU.AU.newDouble2 (this.size ());
for (var pt = 0, i = this.size (); --i >= 0; pt++) data[pt] = [this.get (i).getXVal (), this.get (i).getXVal2 (), this.get (i).getValue ()];

return data;
}, "~S");
$_V(c$, "getDataHeader", 
function () {
return JSV.common.IntegralData.$HEADER;
});
$_M(c$, "shiftY", 
function (yOld, yNew, yPixel0, yPixels) {
var pt = Clazz_doubleToInt (100.0 * (yPixel0 + yPixels - yNew) / yPixels);
if (yOld < 0) pt -= this.percentOffset;
if (yOld < 0) {
this.update (0, this.percentOffset, pt);
} else {
this.update (0, pt, this.percentRange);
}}, "~N,~N,~N,~N");
$_M(c$, "autoIntegrate", 
function () {
if (this.xyCoords == null) this.calculateIntegral ();
if (this.xyCoords.length == 0) return;
this.clear ();
var iStart = -1;
var cutoff = 0.0001;
var nCount = 0;
var nMin = 20;
var y0 = this.xyCoords[this.xyCoords.length - 1].getYVal ();
for (var i = this.xyCoords.length - 1; --i >= 0; ) {
var y = this.xyCoords[i].getYVal ();
nCount++;
if ((y - y0) < cutoff && iStart < 0) {
if (y < y0) {
y0 = y;
nCount = 0;
}continue;
}if (iStart < 0) {
iStart = i + Math.min (nCount, nMin);
y0 = y;
nCount = 0;
continue;
}if ((y - y0) < cutoff) {
if (nCount == 1) y0 = y;
if (nCount >= nMin) {
this.addIntegralRegion (this.xyCoords[iStart].getXVal (), this.xyCoords[i].getXVal ());
iStart = -1;
y0 = y;
nCount = 0;
}} else {
nCount = 0;
y0 = y;
}}
if (this.spec.nH > 0) this.factorAllIntegrals (this.spec.nH / this.percentRange, false);
});
$_M(c$, "getInfo", 
function (info) {
info.put ("offset", Double.$valueOf (this.myParams.integralOffset));
info.put ("range", Double.$valueOf (this.myParams.integralRange));
info.put ("normalizationFactor", Double.$valueOf (this.normalizationFactor));
info.put ("integralTotal", Double.$valueOf (this.integralTotal));
Clazz_superCall (this, JSV.common.IntegralData, "getInfo", [info]);
}, "java.util.Map");
$_M(c$, "setMinimumIntegral", 
function (val) {
for (var i = this.size (); --i >= 0; ) if (this.get (i).getValue () < val) this.remove (i);

}, "~N");
Clazz_pu$h ();
c$ = Clazz_declareType (JSV.common.IntegralData, "IntMode", Enum);
c$.getMode = $_M(c$, "getMode", 
function (a) {
for (var mode, $mode = 0, $$mode = JSV.common.IntegralData.IntMode.values (); $mode < $$mode.length && ((mode = $$mode[$mode]) || true); $mode++) if (a.startsWith (mode.name ())) return mode;

return JSV.common.IntegralData.IntMode.ON;
}, "~S");
Clazz_defineEnumConstant (c$, "OFF", 0, []);
Clazz_defineEnumConstant (c$, "ON", 1, []);
Clazz_defineEnumConstant (c$, "TOGGLE", 2, []);
Clazz_defineEnumConstant (c$, "AUTO", 3, []);
Clazz_defineEnumConstant (c$, "LIST", 4, []);
Clazz_defineEnumConstant (c$, "MARK", 5, []);
Clazz_defineEnumConstant (c$, "MIN", 6, []);
Clazz_defineEnumConstant (c$, "UPDATE", 7, []);
c$ = Clazz_p0p ();
Clazz_defineStatics (c$,
"DEFAULT_OFFSET", 30,
"DEFAULT_RANGE", 50,
"DEFAULT_MINY", 0.1);
c$.c = c$.prototype.c =  new JSV.common.IntegralComparator ();
c$.$HEADER = c$.prototype.$HEADER = ["peak", "start/ppm", "end/ppm", "value"];
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.MeasurementData"], "JSV.common.PeakData", ["java.lang.Double", "JU.DF", "JSV.common.Coordinate", "$.PeakPick"], function () {
c$ = Clazz_decorateAsClass (function () {
this.thresh = 0;
this.minY = 0;
this.maxY = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "PeakData", JSV.common.MeasurementData);
$_M(c$, "getThresh", 
function () {
return this.thresh;
});
$_V(c$, "getDataHeader", 
function () {
return (this.spec.isHNMR () ? JSV.common.PeakData.HNMR_HEADER : ["peak", this.spec.getXUnits (), this.spec.getYUnits ()]);
});
$_V(c$, "getMeasurementListArray", 
function (units) {
var data =  new Array (this.size ());
var last = [-1.0E100, 1e100, 1e100];
var ddata;
for (var pt = 0, i = this.size (); --i >= 0; pt++) {
ddata = this.spec.getPeakListArray (this.get (i), last, this.maxY);
if (ddata.length == 2) data[pt] = ["" + (pt + 1), JU.DF.formatDecimalDbl (ddata[0], 2), JU.DF.formatDecimalDbl (ddata[1], 4)];
 else data[pt] = ["" + (pt + 1), JU.DF.formatDecimalDbl (ddata[0], 4), JU.DF.formatDecimalDbl (ddata[1], 4), JU.DF.formatDecimalDbl (ddata[2], 2), (ddata[3] == 0 ? "" : JU.DF.formatDecimalDbl (ddata[3], 2)), (ddata[4] == 0 ? "" : JU.DF.formatDecimalDbl (ddata[4], 2)), (ddata[5] == 0 ? "" : JU.DF.formatDecimalDbl (ddata[5], 2))];
}
return data;
}, "~S");
$_V(c$, "getMeasurementListArrayReal", 
function (units) {
var data =  Clazz_newDoubleArray (this.size (), 0);
var last = [-1.0E100, 1e100, 1e100];
for (var pt = 0, i = this.size (); --i >= 0; pt++) data[pt] = this.spec.getPeakListArray (this.get (i), last, this.maxY);

return data;
}, "~S");
$_M(c$, "getInfo", 
function (info) {
info.put ("interpolation", this.myParams.peakListInterpolation);
info.put ("threshold", Double.$valueOf (this.myParams.peakListThreshold));
Clazz_superCall (this, JSV.common.PeakData, "getInfo", [info]);
}, "java.util.Map");
$_M(c$, "setPeakList", 
function (p, precision, view) {
this.precision = (precision == -2147483648 ? this.spec.getDefaultUnitPrecision () : precision);
var xyCoords = this.spec.getXYCoords ();
if (xyCoords.length < 3) return;
this.clear ();
if (p != null) {
this.myParams.peakListInterpolation = p.peakListInterpolation;
this.myParams.peakListThreshold = p.peakListThreshold;
}var doInterpolate = (this.myParams.peakListInterpolation.equals ("parabolic"));
var isInverted = this.spec.isInverted ();
this.minY = view.minYOnScale;
this.maxY = view.maxYOnScale;
var minX = view.minXOnScale;
var maxX = view.maxXOnScale;
this.thresh = this.myParams.peakListThreshold;
if (Double.isNaN (this.thresh)) this.thresh = this.myParams.peakListThreshold = (this.minY + this.maxY) / 2;
var yLast = 0;
var y3 = [xyCoords[0].getYVal (), yLast = xyCoords[1].getYVal (), 0];
var n = 0;
if (isInverted) for (var i = 2; i < xyCoords.length; i++) {
var y = y3[i % 3] = xyCoords[i].getYVal ();
if (yLast < this.thresh && y3[(i - 2) % 3] > yLast && yLast < y) {
var x = (doInterpolate ? JSV.common.Coordinate.parabolicInterpolation (xyCoords, i - 1) : xyCoords[i - 1].getXVal ());
if (x >= minX || x <= maxX) {
var m =  new JSV.common.PeakPick ().setValue (x, y, this.spec, null, 0);
this.addLast (m);
if (++n == 100) break;
}}yLast = y;
}
 else for (var i = 2; i < xyCoords.length; i++) {
var y = y3[i % 3] = xyCoords[i].getYVal ();
if (yLast > this.thresh && y3[(i - 2) % 3] < yLast && yLast > y) {
var x = (doInterpolate ? JSV.common.Coordinate.parabolicInterpolation (xyCoords, i - 1) : xyCoords[i - 1].getXVal ());
if (x >= minX && x <= maxX) {
var m =  new JSV.common.PeakPick ().setValue (x, y, this.spec, JU.DF.formatDecimalDbl (x, precision), x);
this.addLast (m);
if (++n == 100) break;
}}yLast = y;
}
}, "JSV.common.Parameters,~N,JSV.common.ScaleData");
c$.HNMR_HEADER = c$.prototype.HNMR_HEADER = ["peak", "shift/ppm", "intens", "shift/hz", "diff/hz", "2-diff", "3-diff"];
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.Coordinate", "$.ScriptToken"], "JSV.common.PlotWidget", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.xPixel0 = 0;
this.yPixel0 = 0;
this.xPixel1 = 0;
this.yPixel1 = 0;
this.isPin = false;
this.isPinOrCursor = false;
this.isXtype = false;
this.is2D = false;
this.is2Donly = false;
this.isEnabled = true;
this.isVisible = false;
this.name = null;
this.color = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PlotWidget", JSV.common.Coordinate);
Clazz_prepareFields (c$, function () {
this.color = JSV.common.ScriptToken.PLOTCOLOR;
});
$_V(c$, "toString", 
function () {
return this.name + (!this.isPinOrCursor ? "" + this.xPixel0 + " " + this.yPixel0 + " / " + this.xPixel1 + " " + this.yPixel1 : " x=" + this.getXVal () + "/" + this.xPixel0 + " y=" + this.getYVal () + "/" + this.yPixel0);
});
Clazz_makeConstructor (c$, 
function (name) {
Clazz_superConstructor (this, JSV.common.PlotWidget, []);
this.name = name;
this.isPin = (name.charAt (0) == 'p');
this.isPinOrCursor = (name.charAt (0) != 'z');
this.isXtype = (name.indexOf ("x") >= 0);
this.is2D = (name.indexOf ("2D") >= 0);
this.is2Donly = (this.is2D && name.charAt (0) == 'p');
}, "~S");
$_M(c$, "selected", 
function (xPixel, yPixel) {
return (this.isVisible && Math.abs (xPixel - this.xPixel0) < 5 && Math.abs (yPixel - this.yPixel0) < 5);
}, "~N,~N");
$_M(c$, "setX", 
function (x, xPixel) {
this.setXVal (x);
this.xPixel0 = this.xPixel1 = xPixel;
}, "~N,~N");
$_M(c$, "setY", 
function (y, yPixel) {
this.setYVal (y);
this.yPixel0 = this.yPixel1 = yPixel;
}, "~N,~N");
$_M(c$, "getValue", 
function () {
return (this.isXtype ? this.getXVal () : this.getYVal ());
});
$_M(c$, "setEnabled", 
function (enabled) {
this.isEnabled = enabled;
}, "~B");
});
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.ScaleData", ["java.lang.Double", "JSV.common.Coordinate"], function () {
c$ = Clazz_decorateAsClass (function () {
this.initMinYOnScale = 0;
this.initMaxYOnScale = 0;
this.initMinY = 0;
this.initMaxY = 0;
this.startDataPointIndex = 0;
this.endDataPointIndex = 0;
this.pointCount = 0;
this.minX = 0;
this.maxX = 0;
this.firstX = NaN;
this.minXOnScale = 0;
this.maxXOnScale = 0;
this.specShift = 0;
this.precision = null;
this.exportPrecision = null;
this.steps = null;
this.minorTickCounts = null;
this.minYOnScale = 0;
this.maxYOnScale = 0;
this.minY = 0;
this.maxY = 0;
this.isShiftZoomedY = false;
this.spectrumScaleFactor = 1;
this.spectrumYRef = 0;
this.userYFactor = 1;
this.firstY = 0;
this.minY2D = 0;
this.maxY2D = 0;
this.xFactorForScale = 0;
this.yFactorForScale = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "ScaleData");
Clazz_prepareFields (c$, function () {
this.precision =  Clazz_newIntArray (2, 0);
this.exportPrecision =  Clazz_newIntArray (2, 0);
this.steps =  Clazz_newDoubleArray (2, 0);
this.minorTickCounts =  Clazz_newIntArray (2, 0);
});
Clazz_makeConstructor (c$, 
function () {
});
Clazz_makeConstructor (c$, 
function (iStart, iEnd) {
this.startDataPointIndex = iStart;
this.endDataPointIndex = iEnd;
this.pointCount = this.endDataPointIndex - this.startDataPointIndex + 1;
}, "~N,~N");
Clazz_makeConstructor (c$, 
function (coords, start, end, isContinuous, isInverted) {
this.minX = JSV.common.Coordinate.getMinX (coords, start, end);
this.maxX = JSV.common.Coordinate.getMaxX (coords, start, end);
this.minY = JSV.common.Coordinate.getMinY (coords, start, end);
if (this.minY > 0 && !isContinuous) this.minY = 0;
this.maxY = JSV.common.Coordinate.getMaxY (coords, start, end);
this.setScale (isContinuous, isInverted);
}, "~A,~N,~N,~B,~B");
$_M(c$, "setScale", 
function (isContinuous, isInverted) {
this.setXScale ();
if (!isContinuous) this.maxXOnScale += this.steps[0] / 2;
this.setYScale (this.minY, this.maxY, true, isInverted);
}, "~B,~B");
$_M(c$, "setXScale", 
function () {
var xStep = this.setScaleParams (this.minX, this.maxX, 0);
this.firstX = Math.floor (this.minX / xStep) * xStep;
if (Math.abs ((this.minX - this.firstX) / xStep) > 0.0001) this.firstX += xStep;
this.minXOnScale = this.minX;
this.maxXOnScale = this.maxX;
});
$_M(c$, "isYZeroOnScale", 
function () {
return (this.minYOnScale < this.spectrumYRef && this.maxYOnScale > this.spectrumYRef);
});
$_M(c$, "setYScale", 
function (minY, maxY, setScaleMinMax, isInverted) {
if (minY == 0 && maxY == 0) maxY = 1;
if (this.isShiftZoomedY) {
minY = this.minYOnScale;
maxY = this.maxYOnScale;
}var yStep = this.setScaleParams (minY, maxY, 1);
var dy = (isInverted ? yStep / 2 : yStep / 4);
var dy2 = (isInverted ? yStep / 4 : yStep / 2);
if (!this.isShiftZoomedY) {
this.minYOnScale = (minY == 0 ? 0 : setScaleMinMax ? dy * Math.floor (minY / dy) : minY);
this.maxYOnScale = (setScaleMinMax ? dy2 * Math.ceil (maxY * 1.05 / dy2) : maxY);
}this.firstY = (minY == 0 ? 0 : Math.floor (minY / dy) * dy);
if (this.minYOnScale < 0 && this.maxYOnScale > 0) {
this.firstY = 0;
while (this.firstY - yStep > this.minYOnScale) this.firstY -= yStep;

} else if (this.minYOnScale != 0 && Math.abs ((minY - this.firstY) / dy) > 0.0001) {
this.firstY += dy;
}if (setScaleMinMax) {
this.initMinYOnScale = this.minYOnScale;
this.initMaxYOnScale = this.maxYOnScale;
this.initMinY = minY;
this.initMaxY = maxY;
}}, "~N,~N,~B,~B");
$_M(c$, "scale2D", 
function (f) {
var dy = this.maxY - this.minY;
if (f == 1) {
this.maxY = this.initMaxY;
this.minY = this.initMinY;
return;
}this.maxY = this.minY + dy / f;
}, "~N");
$_M(c$, "setXRange", 
function (x1, x2) {
this.minX = x1;
this.maxX = x2;
this.setXScale ();
}, "~N,~N");
c$.getXRange = $_M(c$, "getXRange", 
function (i, xyCoords, initX, finalX, iStart, iEnd, startIndices, endIndices) {
var index = 0;
var ptCount = 0;
for (index = iStart; index <= iEnd; index++) {
var x = xyCoords[index].getXVal ();
if (x >= initX) {
startIndices[i] = index;
break;
}}
for (; index <= iEnd; index++) {
var x = xyCoords[index].getXVal ();
ptCount++;
if (x >= finalX) {
break;
}}
endIndices[i] = index - 1;
return ptCount;
}, "~N,~A,~N,~N,~N,~N,~A,~A");
$_M(c$, "setScaleParams", 
function (min, max, i) {
var dx = (max == min ? 1 : Math.abs (max - min) / 14);
var log = Math.log10 (Math.abs (dx));
var exp = Clazz_doubleToInt (Math.floor (log));
this.exportPrecision[i] = exp;
this.precision[i] = (exp <= 0 ? Math.min (8, 1 - exp) : exp > 3 ? -2 : 0);
var j = 0;
var dec = Math.pow (10, log - exp);
while (dec > JSV.common.ScaleData.NTICKS[j]) {
j++;
}
this.steps[i] = Math.pow (10, exp) * JSV.common.ScaleData.NTICKS[j];
log = Math.log10 (Math.abs (this.steps[i] * 1.0001e5));
var mantissa = log - Math.floor (log);
var n = 0;
for (j = 0; j < JSV.common.ScaleData.NTICKS.length; j++) if (Math.abs (mantissa - JSV.common.ScaleData.LOGTICKS[j]) < 0.001) {
n = JSV.common.ScaleData.NTICKS[j];
break;
}
this.minorTickCounts[i] = n;
return this.steps[i];
}, "~N,~N,~N");
$_M(c$, "isInRangeX", 
function (x) {
return (x >= this.minX && x <= this.maxX);
}, "~N");
$_M(c$, "addSpecShift", 
function (dx) {
this.specShift += dx;
this.minX += dx;
this.maxX += dx;
this.minXOnScale += dx;
this.maxXOnScale += dx;
this.firstX += dx;
}, "~N");
$_M(c$, "getInfo", 
function (info) {
info.put ("specShift", Double.$valueOf (this.specShift));
info.put ("minX", Double.$valueOf (this.minX));
info.put ("maxX", Double.$valueOf (this.maxX));
info.put ("minXOnScale", Double.$valueOf (this.minXOnScale));
info.put ("maxXOnScale", Double.$valueOf (this.maxXOnScale));
info.put ("minY", Double.$valueOf (this.minY));
info.put ("maxY", Double.$valueOf (this.maxY));
info.put ("minYOnScale", Double.$valueOf (this.minYOnScale));
info.put ("maxYOnScale", Double.$valueOf (this.maxYOnScale));
info.put ("minorTickCountX", Integer.$valueOf (this.minorTickCounts[0]));
info.put ("xStep", Double.$valueOf (this.steps[0]));
return info;
}, "java.util.Map");
$_M(c$, "setMinMax", 
function (minX, maxX, minY, maxY) {
this.minX = minX;
this.maxX = maxX;
this.minY = minY;
this.maxY = maxY;
}, "~N,~N,~N,~N");
$_M(c$, "toX", 
function (xPixel, xPixel1, drawXAxisLeftToRight) {
return this.toXScaled (xPixel, xPixel1, drawXAxisLeftToRight, this.xFactorForScale);
}, "~N,~N,~B");
$_M(c$, "toX0", 
function (xPixel, xPixel0, xPixel1, drawXAxisLeftToRight) {
return this.toXScaled (xPixel, xPixel1, drawXAxisLeftToRight, (this.maxXOnScale - this.minXOnScale) / (xPixel1 - xPixel0));
}, "~N,~N,~N,~B");
$_M(c$, "toXScaled", 
function (xPixel, xPixel1, drawXAxisLeftToRight, factor) {
return (drawXAxisLeftToRight ? this.maxXOnScale - (xPixel1 - xPixel) * factor : this.minXOnScale + (xPixel1 - xPixel) * factor);
}, "~N,~N,~B,~N");
$_M(c$, "toPixelX", 
function (dx, xPixel0, xPixel1, drawXAxisLeftToRight) {
return this.toPixelXScaled (dx, xPixel0, xPixel1, drawXAxisLeftToRight, this.xFactorForScale);
}, "~N,~N,~N,~B");
$_M(c$, "toPixelX0", 
function (dx, xPixel0, xPixel1, drawXAxisLeftToRight) {
return this.toPixelXScaled (dx, xPixel0, xPixel1, drawXAxisLeftToRight, (this.maxXOnScale - this.minXOnScale) / (xPixel1 - xPixel0));
}, "~N,~N,~N,~B");
$_M(c$, "toPixelXScaled", 
function (dx, xPixel0, xPixel1, drawXAxisLeftToRight, factor) {
var x = Clazz_doubleToInt ((dx - this.minXOnScale) / factor);
return (drawXAxisLeftToRight ? xPixel0 + x : xPixel1 - x);
}, "~N,~N,~N,~B,~N");
$_M(c$, "toY", 
function (yPixel, yPixel0) {
return this.maxYOnScale + (yPixel0 - yPixel) * this.yFactorForScale;
}, "~N,~N");
$_M(c$, "toY0", 
function (yPixel, yPixel0, yPixel1) {
var factor = (this.maxYOnScale - this.minYOnScale) / (yPixel1 - yPixel0);
var y = this.maxYOnScale + (yPixel0 - yPixel) * factor;
return Math.max (this.minYOnScale, Math.min (y, this.maxYOnScale));
}, "~N,~N,~N");
$_M(c$, "toPixelY", 
function (yVal, yPixel1) {
return (Double.isNaN (yVal) ? -2147483648 : yPixel1 - Clazz_doubleToInt (((yVal - this.spectrumYRef) * this.userYFactor + this.spectrumYRef - this.minYOnScale) / this.yFactorForScale));
}, "~N,~N");
$_M(c$, "toPixelY0", 
function (y, yPixel0, yPixel1) {
var factor = (this.maxYOnScale - this.minYOnScale) / (yPixel1 - yPixel0);
return Clazz_doubleToInt (yPixel0 + (this.maxYOnScale - y) / factor);
}, "~N,~N,~N");
$_M(c$, "setXYScale", 
function (xPixels, yPixels, isInverted) {
var yRef = this.spectrumYRef;
var f = this.spectrumScaleFactor;
var useInit = (f != 1 || this.isShiftZoomedY);
var minY = (useInit ? this.initMinYOnScale : this.minY);
var maxY = (useInit ? this.initMaxYOnScale : this.maxY);
if (useInit && yRef < minY) yRef = minY;
if (useInit && yRef > maxY) yRef = maxY;
this.setYScale ((minY - yRef) / f + yRef, (maxY - yRef) / f + yRef, f == 1, isInverted);
this.xFactorForScale = (this.maxXOnScale - this.minXOnScale) / (xPixels - 1);
this.yFactorForScale = (this.maxYOnScale - this.minYOnScale) / (yPixels - 1);
}, "~N,~N,~B");
c$.copyScaleFactors = $_M(c$, "copyScaleFactors", 
function (sdFrom, sdTo) {
for (var i = 0; i < sdFrom.length; i++) {
sdTo[i].spectrumScaleFactor = sdFrom[i].spectrumScaleFactor;
sdTo[i].spectrumYRef = sdFrom[i].spectrumYRef;
sdTo[i].userYFactor = sdFrom[i].userYFactor;
sdTo[i].specShift = sdFrom[i].specShift;
sdTo[i].isShiftZoomedY = sdFrom[i].isShiftZoomedY;
}
}, "~A,~A");
c$.copyYScales = $_M(c$, "copyYScales", 
function (sdFrom, sdTo) {
for (var i = 0; i < sdFrom.length; i++) {
sdTo[i].initMinYOnScale = sdFrom[i].initMinYOnScale;
sdTo[i].initMaxYOnScale = sdFrom[i].initMaxYOnScale;
sdTo[i].minY = sdFrom[i].minY;
sdTo[i].maxY = sdFrom[i].maxY;
if (sdFrom[i].isShiftZoomedY) {
sdTo[i].isShiftZoomedY = true;
sdTo[i].minYOnScale = sdFrom[i].minYOnScale;
sdTo[i].maxYOnScale = sdFrom[i].maxYOnScale;
}}
}, "~A,~A");
c$.setDataPointIndices = $_M(c$, "setDataPointIndices", 
function (graphsTemp, initX, finalX, minPoints, startIndices, endIndices) {
var nSpectraOK = 0;
var nSpectra = graphsTemp.size ();
for (var i = 0; i < nSpectra; i++) {
var xyCoords = graphsTemp.get (i).getXYCoords ();
if (JSV.common.ScaleData.getXRange (i, xyCoords, initX, finalX, 0, xyCoords.length - 1, startIndices, endIndices) >= minPoints) nSpectraOK++;
}
return (nSpectraOK == nSpectra);
}, "JU.List,~N,~N,~N,~A,~A");
c$.fixScale = $_M(c$, "fixScale", 
function (map) {
if (map.isEmpty ()) return;
while (true) {
for (var entry, $entry = map.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) {
var s = entry.getValue ();
var pt = s.indexOf ("E");
if (pt >= 0) s = s.substring (0, pt);
if (s.indexOf (".") < 0) return;
if (!s.endsWith ("0") && !s.endsWith (".")) return;
}
for (var entry, $entry = map.entrySet ().iterator (); $entry.hasNext () && ((entry = $entry.next ()) || true);) {
var s = entry.getValue ();
var pt = s.indexOf ("E");
if (pt >= 0) entry.setValue (s.substring (0, pt - 1) + s.substring (pt));
 else entry.setValue (s.substring (0, s.length - 1));
}
}
}, "java.util.Map");
$_M(c$, "scaleBy", 
function (f) {
if (this.isShiftZoomedY) {
var center = (this.isYZeroOnScale () ? this.spectrumYRef : (this.minYOnScale + this.maxYOnScale) / 2);
this.minYOnScale = center - (center - this.minYOnScale) / f;
this.maxYOnScale = center - (center - this.maxYOnScale) / f;
} else {
this.spectrumScaleFactor *= f;
}}, "~N");
Clazz_defineStatics (c$,
"NTICKS", [2, 5, 10, 10]);
c$.LOGTICKS = c$.prototype.LOGTICKS = [Math.log10 (2), Math.log10 (5), 0, 1];
});
Clazz_declarePackage ("JSV.common");
Clazz_load (null, "JSV.common.ViewData", ["JSV.common.Coordinate", "$.ScaleData"], function () {
c$ = Clazz_decorateAsClass (function () {
this.scaleData = null;
this.thisScale = null;
this.nSpectra = 0;
this.iThisScale = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "ViewData");
$_M(c$, "getScaleData", 
function () {
return this.scaleData;
});
$_M(c$, "getScale", 
function () {
return this.thisScale;
});
Clazz_makeConstructor (c$, 
function (spectra, yPt1, yPt2, startList, endList, isContinuous, is2D) {
this.nSpectra = (is2D ? 1 : spectra.size ());
this.scaleData =  new Array (this.nSpectra);
for (var j = 0; j < this.nSpectra; j++) this.scaleData[j] =  new JSV.common.ScaleData (startList[j], endList[j]);

this.init (spectra, yPt1, yPt2, isContinuous);
}, "JU.List,~N,~N,~A,~A,~B,~B");
Clazz_makeConstructor (c$, 
function (spectra, yPt1, yPt2, isContinuous) {
this.nSpectra = spectra.size ();
var n = spectra.get (0).getXYCoords ().length;
this.scaleData =  new Array (1);
this.scaleData[0] =  new JSV.common.ScaleData (0, n - 1);
this.init (spectra, yPt1, yPt2, isContinuous);
}, "JU.List,~N,~N,~B");
$_M(c$, "init", 
function (spectra, yPt1, yPt2, isContinuous) {
this.thisScale = this.scaleData[this.iThisScale = 0];
for (var i = 0; i < this.scaleData.length; i++) {
this.scaleData[i].userYFactor = spectra.get (i).getUserYFactor ();
this.scaleData[i].spectrumYRef = spectra.get (i).getYRef ();
}
this.resetScaleFactors ();
var minX = JSV.common.Coordinate.getMinX (spectra, this);
var maxX = JSV.common.Coordinate.getMaxX (spectra, this);
var minY = JSV.common.Coordinate.getMinYUser (spectra, this);
var maxY = JSV.common.Coordinate.getMaxYUser (spectra, this);
if (yPt1 != yPt2) {
minY = yPt1;
maxY = yPt2;
if (minY > maxY) {
var t = minY;
minY = maxY;
maxY = t;
}}var isInverted = spectra.get (0).isInverted ();
for (var i = 0; i < this.scaleData.length; i++) {
this.scaleData[i].setMinMax (minX, maxX, minY, maxY);
this.scaleData[i].setScale (isContinuous, isInverted);
}
}, "JU.List,~N,~N,~B");
$_M(c$, "newSpectrum", 
function (spectra) {
this.init (spectra, 0, 0, false);
}, "JU.List");
$_M(c$, "setXRangeForSubSpectrum", 
function (xyCoords) {
this.setXRange (0, xyCoords, this.scaleData[0].minX, this.scaleData[0].maxX, 0, xyCoords.length - 1);
}, "~A");
$_M(c$, "setXRange", 
function (i, xyCoords, initX, finalX, iStart, iEnd) {
var index = 0;
var ptCount = 0;
for (index = iStart; index <= iEnd; index++) {
var x = xyCoords[index].getXVal ();
if (x >= initX) {
this.scaleData[i % this.scaleData.length].startDataPointIndex = index;
break;
}}
for (; index <= iEnd; index++) {
var x = xyCoords[index].getXVal ();
ptCount++;
if (x >= finalX) {
break;
}}
this.scaleData[i % this.scaleData.length].endDataPointIndex = index - 1;
return ptCount;
}, "~N,~A,~N,~N,~N,~N");
$_M(c$, "getStartingPointIndex", 
function (i) {
return this.scaleData[i % this.scaleData.length].startDataPointIndex;
}, "~N");
$_M(c$, "getEndingPointIndex", 
function (i) {
return this.scaleData[i % this.scaleData.length].endDataPointIndex;
}, "~N");
$_M(c$, "areYScalesSame", 
function (i, j) {
i %= this.scaleData.length;
j %= this.scaleData.length;
return (this.scaleData[i].minYOnScale == this.scaleData[j].minYOnScale && this.scaleData[i].maxYOnScale == this.scaleData[j].maxYOnScale);
}, "~N,~N");
$_M(c$, "setScale", 
function (i, xPixels, yPixels, isInverted) {
this.iThisScale = i % this.scaleData.length;
this.thisScale = this.scaleData[this.iThisScale];
this.thisScale.setXYScale (xPixels, yPixels, isInverted);
}, "~N,~N,~N,~B");
$_M(c$, "resetScaleFactors", 
function () {
for (var i = 0; i < this.scaleData.length; i++) this.scaleData[i].spectrumScaleFactor = 1;

});
$_M(c$, "scaleSpectrum", 
function (i, f) {
if (f <= 0 || i >= this.nSpectra) return;
if (i == -2) {
this.thisScale.scale2D (f);
return;
}if (i < 0) for (i = 0; i < this.scaleData.length; i++) this.scaleData[i].scaleBy (f);

 else this.scaleData[i % this.scaleData.length].scaleBy (f);
}, "~N,~N");
$_M(c$, "getNewScales", 
function (iSelected, isXOnly, y1, y2) {
if (isXOnly) return this.scaleData;
iSelected %= this.scaleData.length;
var f1 = (y1 - this.thisScale.minYOnScale) / (this.thisScale.maxYOnScale - this.thisScale.minYOnScale);
var f2 = (y2 - this.thisScale.minYOnScale) / (this.thisScale.maxYOnScale - this.thisScale.minYOnScale);
var sd =  new Array (this.scaleData.length);
for (var i = 0; i < this.scaleData.length; i++) sd[i] = (iSelected >= 0 && i != iSelected ? this.scaleData[i] :  new JSV.common.ScaleData ());

JSV.common.ScaleData.copyScaleFactors (this.scaleData, sd);
JSV.common.ScaleData.copyYScales (this.scaleData, sd);
for (var i = 0; i < this.scaleData.length; i++) {
if (iSelected >= 0 && i != iSelected) continue;
sd[i].isShiftZoomedY = true;
sd[i].minYOnScale = this.scaleData[i].minYOnScale * (1 - f1) + f1 * this.scaleData[i].maxYOnScale;
sd[i].maxYOnScale = this.scaleData[i].minYOnScale * (1 - f2) + f2 * this.scaleData[i].maxYOnScale;
}
return sd;
}, "~N,~B,~N,~N");
});
Clazz_declarePackage ("JSV.common");
c$ = Clazz_decorateAsClass (function () {
this.imageableX = 0;
this.imageableY = 0;
this.paperHeight = 0;
this.paperWidth = 0;
this.imageableHeight = 0;
this.imageableWidth = 0;
this.layout = "landscape";
this.position = "fit to page";
this.showGrid = true;
this.showXScale = true;
this.showYScale = true;
this.showTitle = true;
this.font = "Helvetica";
this.paper = null;
this.asPDF = true;
this.title = null;
this.date = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PrintLayout");
Clazz_prepareFields (c$, function () {
this.paperHeight = Clazz_floatToInt (Math.min (11, 11.69) * 72);
this.paperWidth = Clazz_floatToInt (Math.min (8.5, 8.27) * 72);
this.imageableHeight = this.paperHeight;
this.imageableWidth = this.paperWidth;
});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.common.Measurement"], "JSV.common.PeakPick", null, function () {
c$ = Clazz_declareType (JSV.common, "PeakPick", JSV.common.Measurement);
$_M(c$, "setValue", 
function (x, y, spec, text, value) {
if (text == null) {
this.set (x, y);
this.setPt2 (spec, false);
} else {
this.setA (x, y, spec, text, false, false, 0, 6);
this.value = value;
this.setPt2 (this.getXVal (), this.getYVal ());
}return this;
}, "~N,~N,JSV.common.JDXSpectrum,~S,~N");
});
Clazz_declarePackage ("JSV.common");
c$ = Clazz_decorateAsClass (function () {
this.repaintPending = false;
this.viewer = null;
this.n = 0;
Clazz_instantialize (this, arguments);
}, JSV.common, "RepaintManager");
Clazz_makeConstructor (c$, 
function (viewer) {
this.viewer = viewer;
}, "JSV.common.JSViewer");
$_M(c$, "refresh", 
function () {
this.n++;
if (this.repaintPending) {
System.out.println ("Repaint " + this.n + " skipped");
return false;
}this.repaintPending = true;
this.viewer.selectedPanel.getPanelData ().taintedAll = true;
{
if (typeof Jmol != "undefined" && Jmol._repaint && this.viewer.applet)
Jmol._repaint(this.viewer.applet, false);
this.repaintDone();
}return true;
});
$_M(c$, "repaintDone", 
function () {
this.repaintPending = false;
this.notify ();
});
Clazz_declarePackage ("JSV.source");
Clazz_load (["JSV.source.JDXHeader"], "JSV.source.JDXSource", ["JU.List"], function () {
c$ = Clazz_decorateAsClass (function () {
this.type = 0;
this.isCompoundSource = false;
this.jdxSpectra = null;
this.errors = "";
this.filePath = null;
this.peakCount = 0;
this.isView = false;
Clazz_instantialize (this, arguments);
}, JSV.source, "JDXSource", JSV.source.JDXHeader);
$_M(c$, "dispose", 
function () {
this.headerTable = null;
this.jdxSpectra = null;
});
Clazz_makeConstructor (c$, 
function (type, filePath) {
Clazz_superConstructor (this, JSV.source.JDXSource, []);
this.type = type;
this.setFilePath (filePath);
this.headerTable =  new JU.List ();
this.jdxSpectra =  new JU.List ();
this.isCompoundSource = (type != 0);
}, "~N,~S");
$_M(c$, "getJDXSpectrum", 
function (index) {
return (this.jdxSpectra.size () <= index ? null : this.jdxSpectra.get (index));
}, "~N");
$_M(c$, "addJDXSpectrum", 
function (filePath, spectrum, forceSub) {
if (filePath == null) filePath = this.filePath;
spectrum.setFilePath (filePath);
var n = this.jdxSpectra.size ();
if (n == 0 || !this.jdxSpectra.get (n - 1).addSubSpectrum (spectrum, forceSub)) this.jdxSpectra.addLast (spectrum);
}, "~S,JSV.common.JDXSpectrum,~B");
$_M(c$, "getNumberOfSpectra", 
function () {
return this.jdxSpectra.size ();
});
$_M(c$, "getSpectra", 
function () {
return this.jdxSpectra;
});
$_M(c$, "getSpectraAsArray", 
function () {
return (this.jdxSpectra == null ? null : this.jdxSpectra.toArray ());
});
$_M(c$, "getErrorLog", 
function () {
return this.errors;
});
$_M(c$, "setErrorLog", 
function (errors) {
this.errors = errors;
}, "~S");
$_M(c$, "setFilePath", 
function (filePath) {
this.filePath = filePath;
}, "~S");
$_M(c$, "getFilePath", 
function () {
return this.filePath;
});
c$.createView = $_M(c$, "createView", 
function (specs) {
var source =  new JSV.source.JDXSource (-2, "view");
source.isView = true;
for (var i = 0; i < specs.size (); i++) source.addJDXSpectrum (specs.get (i).getFilePath (), specs.get (i), false);

return source;
}, "JU.List");
$_M(c$, "getHeaderRowDataAsArray", 
function (addDataClass, rowData) {
if (rowData == null) rowData =  Clazz_newArray (0, 0, null);
var data = this.getHeaderRowDataAsArray (addDataClass, rowData.length);
for (var i = rowData.length; --i >= 0; ) data[data.length - rowData.length + i] = rowData[i];

return data;
}, "~B,~A");
Clazz_defineStatics (c$,
"TYPE_VIEW", -2,
"TYPE_UNKNOWN", -1,
"TYPE_SIMPLE", 0,
"TYPE_BLOCK", 1,
"TYPE_NTUPLE", 2);
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVTree");
Clazz_declarePackage ("JSV.tree");
Clazz_load (["JSV.api.JSVTree"], "JSV.tree.SimpleTree", ["JSV.common.JSVFileManager", "$.PanelNode", "JSV.tree.SimpleTreeModel", "$.SimpleTreeNode", "$.SimpleTreePath"], function () {
c$ = Clazz_decorateAsClass (function () {
this.si = null;
this.rootNode = null;
this.spectraTreeModel = null;
this.viewer = null;
this.selectedPath = null;
Clazz_instantialize (this, arguments);
}, JSV.tree, "SimpleTree", null, JSV.api.JSVTree);
$_V(c$, "getRootNode", 
function () {
return this.rootNode;
});
Clazz_makeConstructor (c$, 
function (viewer) {
this.viewer = viewer;
this.rootNode =  new JSV.tree.SimpleTreeNode ("Spectra", null);
this.spectraTreeModel =  new JSV.tree.SimpleTreeModel (this.rootNode);
}, "JSV.common.JSViewer");
$_M(c$, "valueChanged", 
function () {
this.viewer.selectedTreeNode (this.getLastSelectedPathComponent ());
});
$_M(c$, "getLastSelectedPathComponent", 
function () {
return (this.selectedPath == null ? null : this.selectedPath.getLastPathComponent ());
});
$_V(c$, "setSelectedPanel", 
function (si, jsvp) {
if (jsvp != null) {
var treeNode = JSV.common.PanelNode.findNode (jsvp, this.viewer.panelNodes).treeNode;
this.setSelectionPath (this.viewer.spectraTree.newTreePath (treeNode.getPath ()));
}}, "JSV.api.ScriptInterface,JSV.api.JSVPanel");
$_M(c$, "setSelectionPath", 
function (newTreePath) {
this.selectedPath = newTreePath;
this.valueChanged ();
}, "JSV.api.JSVTreePath");
$_V(c$, "createTree", 
function (si, source, panels) {
var tree = this.viewer.spectraTree;
var rootNode = tree.getRootNode ();
var panelNodes = this.viewer.panelNodes;
var fileName = JSV.common.JSVFileManager.getName (source.getFilePath ());
var panelNode =  new JSV.common.PanelNode (null, fileName, source, null);
var fileNode =  new JSV.tree.SimpleTreeNode (fileName, panelNode);
panelNode.setTreeNode (fileNode);
tree.spectraTreeModel.insertNodeInto (fileNode, rootNode, rootNode.getChildCount ());
var fileCount = si.siGetFileCount () + 1;
si.siSetFileCount (fileCount);
for (var i = 0; i < panels.length; i++) {
var jsvp = panels[i];
var id = fileCount + "." + (i + 1);
panelNode = si.siGetNewPanelNode (id, fileName, source, jsvp);
var treeNode =  new JSV.tree.SimpleTreeNode (panelNode.toString (), panelNode);
panelNode.setTreeNode (treeNode);
panelNodes.addLast (panelNode);
tree.spectraTreeModel.insertNodeInto (treeNode, fileNode, fileNode.getChildCount ());
}
this.viewer.selectFrameNode (panels[0]);
return fileNode;
}, "JSV.api.ScriptInterface,JSV.source.JDXSource,~A");
$_V(c$, "setPath", 
function (path) {
this.setSelectionPath (path);
}, "JSV.api.JSVTreePath");
$_M(c$, "newTreePath", 
function (path) {
return  new JSV.tree.SimpleTreePath (path);
}, "~A");
$_V(c$, "deleteNodes", 
function (toDelete) {
for (var i = 0; i < toDelete.size (); i++) {
this.spectraTreeModel.removeNodeFromParent (toDelete.get (i));
}
}, "JU.List");
});
Clazz_declarePackage ("JSV.source");
Clazz_load (null, "JSV.source.FileReader", ["java.io.BufferedReader", "$.StringReader", "java.lang.Character", "$.Double", "$.Exception", "$.Float", "java.util.Arrays", "$.Hashtable", "$.StringTokenizer", "JU.BS", "$.List", "$.PT", "$.SB", "JSV.api.JSVZipReader", "JSV.common.Coordinate", "$.JDXSpectrum", "$.JSVFileManager", "$.JSViewer", "$.PeakInfo", "JSV.exception.JDXSourceException", "$.JSpecViewException", "JSV.source.JDXDecompressor", "$.JDXSource", "$.JDXSourceStreamTokenizer", "JSV.util.JSVEscape", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.source = null;
this.t = null;
this.errorLog = null;
this.obscure = false;
this.done = false;
this.isZipFile = false;
this.filePath = null;
this.loadImaginary = true;
this.firstSpec = 0;
this.lastSpec = 0;
this.nSpec = 0;
this.blockID = 0;
this.piUnitsX = null;
this.piUnitsY = null;
this.thisModelID = null;
this.peakIndex = 0;
Clazz_instantialize (this, arguments);
}, JSV.source, "FileReader");
Clazz_makeConstructor (c$, 
function (filePath, obscure, loadImaginary, iSpecFirst, iSpecLast) {
System.out.println ("FileReader filePath=" + filePath + "<<");
filePath = JU.PT.trimQuotes (filePath);
this.filePath = (filePath != null && filePath.startsWith ("http://SIMULATION/MOL=") ? "http://SIMULATION/" + "MOL=" + Math.abs (filePath.hashCode ()) : filePath);
this.obscure = obscure;
this.firstSpec = iSpecFirst;
this.lastSpec = iSpecLast;
this.loadImaginary = loadImaginary;
}, "~S,~B,~B,~N,~N");
c$.createJDXSourceFromStream = $_M(c$, "createJDXSourceFromStream", 
function ($in, obscure, loadImaginary) {
return JSV.source.FileReader.createJDXSource (JSV.common.JSVFileManager.getBufferedReaderForInputStream ($in), "stream", obscure, loadImaginary, -1, -1);
}, "java.io.InputStream,~B,~B");
c$.createJDXSource = $_M(c$, "createJDXSource", 
function (br, filePath, obscure, loadImaginary, iSpecFirst, iSpecLast) {
try {
if (br == null) br = JSV.common.JSVFileManager.getBufferedReaderFromName (filePath, "##TITLE");
br.mark (400);
var chs =  Clazz_newCharArray (400, '\0');
br.read (chs, 0, 400);
br.reset ();
var header =  String.instantialize (chs);
var pt1 = header.indexOf ('#');
var pt2 = header.indexOf ('<');
if (pt1 < 0 || pt2 >= 0 && pt2 < pt1) {
var xmlType = header.toLowerCase ();
if (xmlType.contains ("404")) System.out.println (xmlType);
xmlType = (xmlType.contains ("<animl") || xmlType.contains ("<!doctype technique") ? "AnIML" : xmlType.contains ("xml-cml") ? "CML" : null);
var xmlSource = (JSV.common.JSViewer.getInterface ("JSV.source." + xmlType + "Reader")).getSource (filePath, br);
br.close ();
if (xmlSource == null) throw  new JSV.exception.JSpecViewException ("File type not recognized");
return xmlSource;
}return ( new JSV.source.FileReader (filePath, obscure, loadImaginary, iSpecFirst, iSpecLast)).getJDXSource (br);
} catch (e) {
if (Clazz_exceptionOf (e, JSV.exception.JSpecViewException)) {
br.close ();
throw  new Exception ("Error reading JDX format: " + e);
} else {
throw e;
}
}
}, "java.io.BufferedReader,~S,~B,~B,~N,~N");
$_M(c$, "getJDXSource", 
function (reader) {
this.source =  new JSV.source.JDXSource (0, this.filePath);
this.isZipFile = (Clazz_instanceOf (reader, JSV.api.JSVZipReader));
this.t =  new JSV.source.JDXSourceStreamTokenizer (reader);
this.errorLog =  new JU.SB ();
var label = null;
while (!this.done && "##TITLE".equals (this.t.peakLabel ())) {
if (label != null && !this.isZipFile) this.errorLog.append ("Warning - file is a concatenation without LINK record -- does not conform to IUPAC standards!\n");
var spectrum =  new JSV.common.JDXSpectrum ();
var dataLDRTable =  new JU.List ();
while (!this.done && (label = this.t.getLabel ()) != null && !this.isEnd (label)) {
if (label.equals ("##DATATYPE") && this.t.getValue ().toUpperCase ().equals ("LINK")) {
this.getBlockSpectra (dataLDRTable);
spectrum = null;
continue;
}if (label.equals ("##NTUPLES") || label.equals ("##VARNAME")) {
this.getNTupleSpectra (dataLDRTable, spectrum, label);
spectrum = null;
continue;
}if (java.util.Arrays.binarySearch (JSV.source.FileReader.TABULAR_DATA_LABELS, label) > 0) {
this.setTabularDataType (spectrum, label);
if (!this.processTabularData (spectrum, dataLDRTable)) throw  new JSV.exception.JDXSourceException ("Unable to read JDX file");
this.addSpectrum (spectrum, false);
spectrum = null;
continue;
}if (spectrum == null) spectrum =  new JSV.common.JDXSpectrum ();
if (JSV.source.FileReader.readDataLabel (spectrum, label, this.t, this.errorLog, this.obscure)) continue;
var value = this.t.getValue ();
JSV.source.FileReader.addHeader (dataLDRTable, this.t.getRawLabel (), value);
if (this.checkCustomTags (spectrum, label, value)) continue;
}
}
this.source.setErrorLog (this.errorLog.toString ());
return this.source;
}, "~O");
$_M(c$, "isEnd", 
function (label) {
if (!label.equals ("##END")) return false;
this.t.getValue ();
return true;
}, "~S");
$_M(c$, "addSpectrum", 
function (spectrum, forceSub) {
if (!this.loadImaginary && spectrum.isImaginary ()) {
J.util.Logger.info ("FileReader skipping imaginary spectrum -- use LOADIMAGINARY TRUE to load this spectrum.");
return true;
}this.nSpec++;
if (this.firstSpec > 0 && this.nSpec < this.firstSpec) return true;
if (this.lastSpec > 0 && this.nSpec > this.lastSpec) return !(this.done = true);
spectrum.setBlockID (this.blockID);
this.source.addJDXSpectrum (null, spectrum, forceSub);
return true;
}, "JSV.common.JDXSpectrum,~B");
$_M(c$, "getBlockSpectra", 
function (sourceLDRTable) {
J.util.Logger.debug ("--JDX block start--");
var label = "";
var isNew = (this.source.type == 0);
var forceSub = false;
while ((label = this.t.getLabel ()) != null && !label.equals ("##TITLE")) {
if (isNew) {
if (!JSV.source.FileReader.readHeaderLabel (this.source, label, this.t, this.errorLog, this.obscure)) JSV.source.FileReader.addHeader (sourceLDRTable, this.t.getRawLabel (), this.t.getValue ());
} else {
this.t.getValue ();
}if (label.equals ("##BLOCKS")) {
var nBlocks = JU.PT.parseInt (this.t.getValue ());
if (nBlocks > 100 && this.firstSpec <= 0) forceSub = true;
}}
if (!"##TITLE".equals (label)) throw  new JSV.exception.JSpecViewException ("Unable to read block source");
if (isNew) this.source.setHeaderTable (sourceLDRTable);
this.source.type = 1;
this.source.isCompoundSource = true;
var dataLDRTable;
var spectrum =  new JSV.common.JDXSpectrum ();
dataLDRTable =  new JU.List ();
JSV.source.FileReader.readDataLabel (spectrum, label, this.t, this.errorLog, this.obscure);
try {
var tmp;
while ((tmp = this.t.getLabel ()) != null) {
if ("##END".equals (label) && this.isEnd (tmp)) {
J.util.Logger.debug ("##END= " + this.t.getValue ());
break;
}label = tmp;
if (java.util.Arrays.binarySearch (JSV.source.FileReader.TABULAR_DATA_LABELS, label) > 0) {
this.setTabularDataType (spectrum, label);
if (!this.processTabularData (spectrum, dataLDRTable)) throw  new JSV.exception.JDXSourceException ("Unable to read Block Source");
continue;
}if (label.equals ("##DATATYPE") && this.t.getValue ().toUpperCase ().equals ("LINK")) {
this.getBlockSpectra (dataLDRTable);
spectrum = null;
label = null;
} else if (label.equals ("##NTUPLES") || label.equals ("##VARNAME")) {
this.getNTupleSpectra (dataLDRTable, spectrum, label);
spectrum = null;
label = "";
} else if (label.equals ("##JCAMPCS")) {
while (!(label = this.t.getLabel ()).equals ("##TITLE")) {
this.t.getValue ();
}
spectrum = null;
} else {
this.t.getValue ();
}if (this.done) break;
if (spectrum == null) {
spectrum =  new JSV.common.JDXSpectrum ();
dataLDRTable =  new JU.List ();
if (label === "") continue;
if (label == null) {
label = "##END";
continue;
}}if (JSV.source.FileReader.readDataLabel (spectrum, label, this.t, this.errorLog, this.obscure)) continue;
if (this.isEnd (label)) {
if (spectrum.getXYCoords ().length > 0 && !this.addSpectrum (spectrum, forceSub)) return this.source;
spectrum =  new JSV.common.JDXSpectrum ();
dataLDRTable =  new JU.List ();
this.t.getValue ();
continue;
}var value = this.t.getValue ();
JSV.source.FileReader.addHeader (dataLDRTable, this.t.getRawLabel (), value);
if (this.checkCustomTags (spectrum, label, value)) continue;
}
} catch (e$$) {
if (Clazz_exceptionOf (e$$, java.util.NoSuchElementException)) {
var nsee = e$$;
{
throw  new JSV.exception.JSpecViewException ("Unable to Read Block Source");
}
} else if (Clazz_exceptionOf (e$$, JSV.exception.JSpecViewException)) {
var jsve = e$$;
{
throw jsve;
}
} else {
throw e$$;
}
}
this.addErrorLogSeparator ();
this.source.setErrorLog (this.errorLog.toString ());
J.util.Logger.debug ("--JDX block end--");
return this.source;
}, "JU.List");
$_M(c$, "checkCustomTags", 
function (spectrum, label, value) {
var pt = "##$MODELS ##$PEAKS  ##$SIGNALS".indexOf (label);
switch (pt) {
default:
return false;
case 0:
this.thisModelID = JU.PT.getQuotedAttribute (value, "id");
return true;
case 10:
case 20:
this.peakIndex = this.source.peakCount;
this.source.peakCount += this.readPeaks (spectrum, value, pt == 20);
return true;
}
}, "JSV.common.JDXSpectrum,~S,~S");
$_M(c$, "addErrorLogSeparator", 
function () {
if (this.errorLog.length () > 0 && this.errorLog.lastIndexOf ("=====================\n") != this.errorLog.length () - "=====================\n".length) this.errorLog.append ("=====================\n");
});
$_M(c$, "getNTupleSpectra", 
function (sourceLDRTable, spectrum0, label) {
var minMaxY = [1.7976931348623157E308, 4.9E-324];
this.blockID = Math.random ();
var isOK = true;
if (this.firstSpec > 0) spectrum0.numDim = 1;
var isVARNAME = label.equals ("##VARNAME");
if (!isVARNAME) {
label = "";
this.t.getValue ();
}var nTupleTable =  new java.util.Hashtable ();
var plotSymbols =  new Array (2);
var isNew = (this.source.type == 0);
if (isNew) {
this.source.type = 2;
this.source.isCompoundSource = true;
this.source.setHeaderTable (sourceLDRTable);
}while (!(label = (isVARNAME ? label : this.t.getLabel ())).equals ("##PAGE")) {
isVARNAME = false;
var st =  new java.util.StringTokenizer (this.t.getValue (), ",");
var attrList =  new JU.List ();
while (st.hasMoreTokens ()) attrList.addLast (st.nextToken ().trim ());

nTupleTable.put (label, attrList);
}
var symbols = nTupleTable.get ("##SYMBOL");
if (!label.equals ("##PAGE")) throw  new JSV.exception.JSpecViewException ("Error Reading NTuple Source");
var page = this.t.getValue ();
var spectrum = null;
var isFirst = true;
while (!this.done) {
if ((label = this.t.getLabel ()).equals ("##ENDNTUPLES")) {
this.t.getValue ();
break;
}if (label.equals ("##PAGE")) {
page = this.t.getValue ();
continue;
}if (spectrum == null) {
spectrum =  new JSV.common.JDXSpectrum ();
spectrum0.copyTo (spectrum);
spectrum.setTitle (spectrum0.getTitle ());
if (!spectrum.is1D ()) {
var pt = page.indexOf ('=');
if (pt >= 0) try {
spectrum.setY2D (Double.parseDouble (page.substring (pt + 1).trim ()));
var y2dUnits = page.substring (0, pt).trim ();
var i = symbols.indexOf (y2dUnits);
if (i >= 0) spectrum.setY2DUnits (nTupleTable.get ("##UNITS").get (i));
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
} else {
throw e;
}
}
}}var dataLDRTable =  new JU.List ();
spectrum.setHeaderTable (dataLDRTable);
while (!label.equals ("##DATATABLE")) {
JSV.source.FileReader.addHeader (dataLDRTable, this.t.getRawLabel (), this.t.getValue ());
label = this.t.getLabel ();
}
var continuous = true;
var line = this.t.flushLine ();
if (line.trim ().indexOf ("PEAKS") > 0) continuous = false;
var index1 = line.indexOf ('(');
var index2 = line.lastIndexOf (')');
if (index1 == -1 || index2 == -1) throw  new JSV.exception.JDXSourceException ("Variable List not Found");
var varList = line.substring (index1, index2 + 1);
var countSyms = 0;
for (var i = 0; i < symbols.size (); i++) {
var sym = symbols.get (i).trim ();
if (varList.indexOf (sym) != -1) {
plotSymbols[countSyms++] = sym;
}if (countSyms == 2) break;
}
this.setTabularDataType (spectrum, "##" + (continuous ? "XYDATA" : "PEAKTABLE"));
if (!this.readNTUPLECoords (spectrum, nTupleTable, plotSymbols, minMaxY)) throw  new JSV.exception.JDXSourceException ("Unable to read Ntuple Source");
if (!spectrum.nucleusX.equals ("?")) spectrum0.nucleusX = spectrum.nucleusX;
spectrum0.nucleusY = spectrum.nucleusY;
spectrum0.freq2dX = spectrum.freq2dX;
spectrum0.freq2dY = spectrum.freq2dY;
spectrum0.y2DUnits = spectrum.y2DUnits;
for (var i = 0; i < sourceLDRTable.size (); i++) {
var entry = sourceLDRTable.get (i);
var key = JSV.source.JDXSourceStreamTokenizer.cleanLabel (entry[0]);
if (!key.equals ("##TITLE") && !key.equals ("##DATACLASS") && !key.equals ("##NTUPLES")) dataLDRTable.addLast (entry);
}
if (isOK) this.addSpectrum (spectrum, !isFirst);
isFirst = false;
spectrum = null;
}
this.addErrorLogSeparator ();
this.source.setErrorLog (this.errorLog.toString ());
J.util.Logger.info ("NTUPLE MIN/MAX Y = " + minMaxY[0] + " " + minMaxY[1]);
return this.source;
}, "JU.List,JSV.source.JDXDataObject,~S");
$_M(c$, "readPeaks", 
function (spectrum, peakList, isSignals) {
var peakData =  new JU.List ();
var reader =  new java.io.BufferedReader ( new java.io.StringReader (peakList));
try {
var offset = (isSignals ? 1 : 0);
System.out.println ("offset is " + offset + " isSignals=" + isSignals);
var tag1 = (isSignals ? "Signals" : "Peaks");
var tag2 = (isSignals ? "<Signal" : "<PeakData");
var line = this.discardUntil (reader, tag1);
if (line.indexOf ("<" + tag1) < 0) line = this.discardUntil (reader, "<" + tag1);
if (line.indexOf ("<" + tag1) < 0) return 0;
var file = this.getPeakFilePath ();
var model = this.getQuotedAttribute (line, "model");
model = " model=" + this.escape (model == null ? this.thisModelID : model);
var type = this.getQuotedAttribute (line, "type");
if ("HNMR".equals (type)) type = "1HNMR";
 else if ("CNMR".equals (type)) type = "13CNMR";
type = (type == null ? "" : " type=" + this.escape (type));
this.piUnitsX = this.getQuotedAttribute (line, "xLabel");
this.piUnitsY = this.getQuotedAttribute (line, "yLabel");
var htSets =  new java.util.Hashtable ();
var list =  new JU.List ();
while ((line = reader.readLine ()) != null && !(line = line.trim ()).startsWith ("</" + tag1)) {
if (line.startsWith (tag2)) {
this.info (line);
var title = this.getQuotedAttribute (line, "title");
if (title == null) {
title = (type === "1HNMR" ? "atom%S%: %ATOMS%; integration: %NATOMS%" : "");
title = " title=" + this.escape (title);
} else {
title = "";
}var stringInfo = "<PeakData " + file + " index=\"%INDEX%\"" + title + type + (this.getQuotedAttribute (line, "model") == null ? model : "") + " " + line.substring (tag2.length).trim ();
var atoms = this.getQuotedAttribute (stringInfo, "atoms");
if (atoms != null) stringInfo = this.simpleReplace (stringInfo, "atoms=\"" + atoms + "\"", "atoms=\"%ATOMS%\"");
var key = (Clazz_floatToInt (this.parseFloatStr (this.getQuotedAttribute (line, "xMin")) * 100)) + "_" + (Clazz_floatToInt (this.parseFloatStr (this.getQuotedAttribute (line, "xMax")) * 100));
var o = htSets.get (key);
if (o == null) {
o = [stringInfo, (atoms == null ? null :  new JU.BS ())];
htSets.put (key, o);
list.addLast (o);
}var bs = o[1];
if (atoms != null && bs != null) {
atoms = atoms.$replace (',', ' ');
bs.or (this.unescapeBitSet ("({" + atoms + "})"));
System.out.println ("bs is  " + bs);
}}}
var nH = 0;
var n = list.size ();
for (var i = 0; i < n; i++) {
var o = list.get (i);
var stringInfo = o[0];
stringInfo = this.simpleReplace (stringInfo, "%INDEX%", "" + this.getPeakIndex ());
var bs = o[1];
if (bs != null) {
System.out.println ("bs " + i + " is " + bs);
var s = "";
for (var j = bs.nextSetBit (0); j >= 0; j = bs.nextSetBit (j + 1)) s += "," + (j + offset);

var na = bs.cardinality ();
nH += na;
stringInfo = this.simpleReplace (stringInfo, "%ATOMS%", s.substring (1));
stringInfo = this.simpleReplace (stringInfo, "%S%", (na == 1 ? "" : "s"));
stringInfo = this.simpleReplace (stringInfo, "%NATOMS%", "" + na);
}this.info ("JSpecView using " + stringInfo);
this.add (peakData, stringInfo);
}
this.setSpectrumPeaks (spectrum, peakData, nH);
return n;
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return 0;
} else {
throw e;
}
}
}, "JSV.common.JDXSpectrum,~S,~B");
$_M(c$, "add", 
function (peakData, info) {
peakData.addLast ( new JSV.common.PeakInfo (info));
}, "JU.List,~S");
$_M(c$, "info", 
function (s) {
J.util.Logger.info (s);
}, "~S");
$_M(c$, "unescapeBitSet", 
function (str) {
var ch;
var len;
if (str == null || (len = (str = str.trim ()).length) < 4 || str.equalsIgnoreCase ("({null})") || (ch = str.charAt (0)) != '(' && ch != '[' || str.charAt (len - 1) != (ch == '(' ? ')' : ']') || str.charAt (1) != '{' || str.indexOf ('}') != len - 2) return null;
len -= 2;
for (var i = len; --i >= 2; ) if (!Character.isDigit (ch = str.charAt (i)) && ch != ' ' && ch != '\t' && ch != ':') return null;

var lastN = len;
while (Character.isDigit (str.charAt (--lastN))) {
}
if (++lastN == len) lastN = 0;
 else try {
lastN = Integer.parseInt (str.substring (lastN, len));
} catch (e) {
if (Clazz_exceptionOf (e, NumberFormatException)) {
return null;
} else {
throw e;
}
}
var bs = JU.BS.newN (lastN);
lastN = -1;
var iPrev = -1;
var iThis = -2;
for (var i = 2; i <= len; i++) {
switch (ch = str.charAt (i)) {
case '\t':
case ' ':
case '}':
if (iThis < 0) break;
if (iThis < lastN) return null;
lastN = iThis;
if (iPrev < 0) iPrev = iThis;
bs.setBits (iPrev, iThis + 1);
iPrev = -1;
iThis = -2;
break;
case ':':
iPrev = lastN = iThis;
iThis = -2;
break;
default:
if (Character.isDigit (ch)) {
if (iThis < 0) iThis = 0;
iThis = (iThis * 10) + (ch.charCodeAt (0) - 48);
}}
}
return (iPrev >= 0 ? null : bs);
}, "~S");
$_M(c$, "parseFloatStr", 
function (s) {
return JU.PT.parseFloat (s);
}, "~S");
$_M(c$, "simpleReplace", 
function (s, sfrom, sto) {
return JU.PT.simpleReplace (s, sfrom, sto);
}, "~S,~S,~S");
$_M(c$, "escape", 
function (s) {
return JSV.util.JSVEscape.eS (s);
}, "~S");
$_M(c$, "getQuotedAttribute", 
function (s, attr) {
return JU.PT.getQuotedAttribute (s, attr);
}, "~S,~S");
$_M(c$, "getPeakFilePath", 
function () {
return " file=" + JSV.util.JSVEscape.eS (JU.PT.trimQuotes (this.filePath).$replace ('\\', '/'));
});
$_M(c$, "setSpectrumPeaks", 
function (spectrum, peakData, nH) {
spectrum.setPeakList (peakData, this.piUnitsX, this.piUnitsY);
spectrum.setNHydrogens (nH);
}, "JSV.common.JDXSpectrum,JU.List,~N");
$_M(c$, "discardUntil", 
function (reader, tag) {
var line;
while ((line = reader.readLine ()) != null && line.indexOf ("<" + tag) < 0 && line.indexOf ("##") < 0) {
}
return line;
}, "java.io.BufferedReader,~S");
$_M(c$, "getPeakIndex", 
function () {
return ++this.peakIndex;
});
c$.readDataLabel = $_M(c$, "readDataLabel", 
function (spectrum, label, t, errorLog, obscure) {
if (JSV.source.FileReader.readHeaderLabel (spectrum, label, t, errorLog, obscure)) return true;
if (label.equals ("##MINX") || label.equals ("##MINY") || label.equals ("##MAXX") || label.equals ("##MAXY") || label.equals ("##FIRSTY") || label.equals ("##DELTAX") || label.equals ("##DATACLASS")) {
t.getValue ();
return true;
}if (label.equals ("##FIRSTX")) {
spectrum.fileFirstX = Double.parseDouble (t.getValue ());
return true;
}if (label.equals ("##LASTX")) {
spectrum.fileLastX = Double.parseDouble (t.getValue ());
return true;
}if (label.equals ("##NPOINTS")) {
spectrum.nPointsFile = Integer.parseInt (t.getValue ());
return true;
}if (label.equals ("##XFACTOR")) {
spectrum.xFactor = Double.parseDouble (t.getValue ());
return true;
}if (label.equals ("##YFACTOR")) {
spectrum.yFactor = Double.parseDouble (t.getValue ());
return true;
}if (label.equals ("##XUNITS")) {
spectrum.setXUnits (t.getValue ());
return true;
}if (label.equals ("##YUNITS")) {
spectrum.setYUnits (t.getValue ());
return true;
}if (label.equals ("##XLABEL")) {
spectrum.setXLabel (t.getValue ());
return false;
}if (label.equals ("##YLABEL")) {
spectrum.setYLabel (t.getValue ());
return false;
}if (label.equals ("##NUMDIM")) {
spectrum.numDim = Integer.parseInt (t.getValue ());
return true;
}if (label.equals ("##.OBSERVEFREQUENCY")) {
spectrum.observedFreq = Double.parseDouble (t.getValue ());
return true;
}if (label.equals ("##.OBSERVENUCLEUS")) {
spectrum.setObservedNucleus (t.getValue ());
return true;
}if (label.equals ("##$OFFSET") && spectrum.shiftRefType != 0) {
if (spectrum.offset == 1.7976931348623157E308) spectrum.offset = Double.parseDouble (t.getValue ());
spectrum.dataPointNum = 1;
spectrum.shiftRefType = 1;
return false;
}if ((label.equals ("##$REFERENCEPOINT")) && (spectrum.shiftRefType != 0)) {
spectrum.offset = Double.parseDouble (t.getValue ());
spectrum.dataPointNum = 1;
spectrum.shiftRefType = 2;
return false;
}if (label.equals ("##.SHIFTREFERENCE")) {
var val = t.getValue ();
if (!(spectrum.dataType.toUpperCase ().contains ("SPECTRUM"))) return true;
var srt =  new java.util.StringTokenizer (val, ",");
if (srt.countTokens () != 4) return true;
try {
srt.nextToken ();
srt.nextToken ();
spectrum.dataPointNum = Integer.parseInt (srt.nextToken ().trim ());
spectrum.offset = Double.parseDouble (srt.nextToken ().trim ());
} catch (e$$) {
if (Clazz_exceptionOf (e$$, NumberFormatException)) {
var nfe = e$$;
{
return true;
}
} else if (Clazz_exceptionOf (e$$, java.util.NoSuchElementException)) {
var nsee = e$$;
{
return true;
}
} else {
throw e$$;
}
}
if (spectrum.dataPointNum <= 0) spectrum.dataPointNum = 1;
spectrum.shiftRefType = 0;
return true;
}return false;
}, "JSV.source.JDXDataObject,~S,JSV.source.JDXSourceStreamTokenizer,JU.SB,~B");
c$.readHeaderLabel = $_M(c$, "readHeaderLabel", 
function (jdxHeader, label, t, errorLog, obscure) {
if (label.equals ("##TITLE")) {
var value = t.getValue ();
jdxHeader.setTitle (obscure || value == null || value.equals ("") ? "Unknown" : value);
return true;
}if (label.equals ("##JCAMPDX")) {
var value = t.getValue ();
jdxHeader.jcampdx = value;
var version = JU.PT.parseFloat (value);
if (version >= 6.0 || Float.isNaN (version)) {
if (errorLog != null) errorLog.append ("Warning: JCAMP-DX version may not be fully supported: " + value + "\n");
}return true;
}if (label.equals ("##ORIGIN")) {
var value = t.getValue ();
jdxHeader.origin = (value != null && !value.equals ("") ? value : "Unknown");
return true;
}if (label.equals ("##OWNER")) {
var value = t.getValue ();
jdxHeader.owner = (value != null && !value.equals ("") ? value : "Unknown");
return true;
}if (label.equals ("##DATATYPE")) {
jdxHeader.dataType = t.getValue ();
return true;
}if (label.equals ("##LONGDATE")) {
jdxHeader.longDate = t.getValue ();
return true;
}if (label.equals ("##DATE")) {
jdxHeader.date = t.getValue ();
return true;
}if (label.equals ("##TIME")) {
jdxHeader.time = t.getValue ();
return true;
}return false;
}, "JSV.source.JDXHeader,~S,JSV.source.JDXSourceStreamTokenizer,JU.SB,~B");
$_M(c$, "setTabularDataType", 
function (spectrum, label) {
if (label.equals ("##PEAKASSIGNMENTS")) spectrum.setDataClass ("PEAKASSIGNMENTS");
 else if (label.equals ("##PEAKTABLE")) spectrum.setDataClass ("PEAKTABLE");
 else if (label.equals ("##XYDATA")) spectrum.setDataClass ("XYDATA");
 else if (label.equals ("##XYPOINTS")) spectrum.setDataClass ("XYPOINTS");
}, "JSV.source.JDXDataObject,~S");
$_M(c$, "processTabularData", 
function (spec, table) {
if (spec.dataClass.equals ("PEAKASSIGNMENTS")) return true;
spec.setHeaderTable (table);
if (spec.dataClass.equals ("XYDATA")) {
spec.checkRequiredTokens ();
this.decompressData (spec, null);
return true;
}if (spec.dataClass.equals ("PEAKTABLE") || spec.dataClass.equals ("XYPOINTS")) {
spec.setContinuous (spec.dataClass.equals ("XYPOINTS"));
try {
this.t.readLineTrimmed ();
} catch (e) {
if (Clazz_exceptionOf (e, java.io.IOException)) {
e.printStackTrace ();
} else {
throw e;
}
}
var xyCoords;
if (spec.xFactor != 1.7976931348623157E308 && spec.yFactor != 1.7976931348623157E308) xyCoords = JSV.common.Coordinate.parseDSV (this.t.getValue (), spec.xFactor, spec.yFactor);
 else xyCoords = JSV.common.Coordinate.parseDSV (this.t.getValue (), 1, 1);
spec.setXYCoords (xyCoords);
var fileDeltaX = JSV.common.Coordinate.deltaX (xyCoords[xyCoords.length - 1].getXVal (), xyCoords[0].getXVal (), xyCoords.length);
spec.setIncreasing (fileDeltaX > 0);
return true;
}return false;
}, "JSV.source.JDXDataObject,JU.List");
$_M(c$, "readNTUPLECoords", 
function (spec, nTupleTable, plotSymbols, minMaxY) {
var list;
if (spec.dataClass.equals ("XYDATA")) {
list = nTupleTable.get ("##SYMBOL");
var index1 = list.indexOf (plotSymbols[0]);
var index2 = list.indexOf (plotSymbols[1]);
list = nTupleTable.get ("##VARNAME");
spec.varName = list.get (index2).toUpperCase ();
list = nTupleTable.get ("##FACTOR");
spec.xFactor = Double.parseDouble (list.get (index1));
spec.yFactor = Double.parseDouble (list.get (index2));
list = nTupleTable.get ("##LAST");
spec.fileLastX = Double.parseDouble (list.get (index1));
list = nTupleTable.get ("##FIRST");
spec.fileFirstX = Double.parseDouble (list.get (index1));
list = nTupleTable.get ("##VARDIM");
spec.nPointsFile = Integer.parseInt (list.get (index1));
list = nTupleTable.get ("##UNITS");
spec.setXUnits (list.get (index1));
spec.setYUnits (list.get (index2));
if (spec.nucleusX == null && (list = nTupleTable.get ("##.NUCLEUS")) != null) {
spec.setNucleus (list.get (0), false);
spec.setNucleus (list.get (index1), true);
} else {
if (spec.nucleusX == null) spec.nucleusX = "?";
}this.decompressData (spec, minMaxY);
return true;
}if (spec.dataClass.equals ("PEAKTABLE") || spec.dataClass.equals ("XYPOINTS")) {
spec.setContinuous (spec.dataClass.equals ("XYPOINTS"));
list = nTupleTable.get ("##SYMBOL");
var index1 = list.indexOf (plotSymbols[0]);
var index2 = list.indexOf (plotSymbols[1]);
list = nTupleTable.get ("##UNITS");
spec.setXUnits (list.get (index1));
spec.setYUnits (list.get (index2));
spec.setXYCoords (JSV.common.Coordinate.parseDSV (this.t.getValue (), spec.xFactor, spec.yFactor));
return true;
}return false;
}, "JSV.source.JDXDataObject,java.util.Map,~A,~A");
$_M(c$, "decompressData", 
function (spec, minMaxY) {
var errPt = this.errorLog.length ();
var fileDeltaX = JSV.common.Coordinate.deltaX (spec.fileLastX, spec.fileFirstX, spec.nPointsFile);
spec.setIncreasing (fileDeltaX > 0);
spec.setContinuous (true);
var decompressor =  new JSV.source.JDXDecompressor (this.t, spec.fileFirstX, spec.xFactor, spec.yFactor, fileDeltaX, spec.nPointsFile);
var firstLastX =  Clazz_newDoubleArray (2, 0);
var t = System.currentTimeMillis ();
var xyCoords = decompressor.decompressData (this.errorLog, firstLastX);
System.out.println ("decompression time = " + (System.currentTimeMillis () - t) + " ms");
spec.setXYCoords (xyCoords);
var d = decompressor.getMinY ();
if (minMaxY != null) {
if (d < minMaxY[0]) minMaxY[0] = d;
d = decompressor.getMaxY ();
if (d > minMaxY[1]) minMaxY[1] = d;
}var freq = (Double.isNaN (spec.freq2dX) ? spec.observedFreq : spec.freq2dX);
if (spec.offset != 1.7976931348623157E308 && freq != 1.7976931348623157E308 && spec.dataType.toUpperCase ().contains ("SPECTRUM")) {
JSV.common.Coordinate.applyShiftReference (xyCoords, spec.dataPointNum, spec.fileFirstX, spec.fileLastX, spec.offset, freq, spec.shiftRefType);
}if (freq != 1.7976931348623157E308 && spec.getXUnits ().toUpperCase ().equals ("HZ")) {
var xScale = freq;
JSV.common.Coordinate.applyScale (xyCoords, (1 / xScale), 1);
spec.setXUnits ("PPM");
spec.setHZtoPPM (true);
}if (this.errorLog.length () != errPt) {
this.errorLog.append (spec.getTitle ()).append ("\n");
this.errorLog.append ("firstX: " + spec.fileFirstX + " Found " + firstLastX[0] + "\n");
this.errorLog.append ("lastX from Header " + spec.fileLastX + " Found " + firstLastX[1] + "\n");
this.errorLog.append ("deltaX from Header " + fileDeltaX + "\n");
this.errorLog.append ("Number of points in Header " + spec.nPointsFile + " Found " + xyCoords.length + "\n");
} else {
}if (J.util.Logger.debugging) {
System.err.println (this.errorLog.toString ());
}}, "JSV.source.JDXDataObject,~A");
c$.addHeader = $_M(c$, "addHeader", 
function (table, label, value) {
var entry;
for (var i = 0; i < table.size (); i++) if ((entry = table.get (i))[0].equals (label)) {
entry[1] = value;
return;
}
table.addLast ([label, value, JSV.source.JDXSourceStreamTokenizer.cleanLabel (label)]);
}, "JU.List,~S,~S");
Clazz_defineStatics (c$,
"VAR_LIST_TABLE", [["PEAKTABLE", "XYDATA", "XYPOINTS"], ["(XY..XY)", "(X++(Y..Y))", "(XY..XY)"]],
"ERROR_SEPARATOR", "=====================\n",
"TABULAR_DATA_LABELS", ["##XYDATA", "##XYPOINTS", "##PEAKTABLE", "##DATATABLE", "##PEAKASSIGNMENTS"]);
{
java.util.Arrays.sort (JSV.source.FileReader.TABULAR_DATA_LABELS);
}});
Clazz_declarePackage ("JSV.common");
Clazz_load (["JSV.api.JSVGraphics"], "JSV.common.PDFWriter", ["java.util.Hashtable", "javajs.export.PDFCreator", "JU.CU", "JSV.common.JSVersion"], function () {
c$ = Clazz_decorateAsClass (function () {
this.g2d = null;
this.date = null;
this.pdf = null;
this.inPath = false;
this.rgb = null;
Clazz_instantialize (this, arguments);
}, JSV.common, "PDFWriter", null, JSV.api.JSVGraphics);
Clazz_prepareFields (c$, function () {
this.rgb =  Clazz_newFloatArray (3, 0);
});
Clazz_makeConstructor (c$, 
function () {
this.pdf =  new javajs["export"].PDFCreator ();
});
$_M(c$, "createPdfDocument", 
function (panel, pl, os) {
var isLandscape = pl.layout.equals ("landscape");
this.date = pl.date;
this.pdf.setOutputStream (os);
this.g2d = panel.getPanelData ().g2d;
try {
this.pdf.newDocument (pl.paperWidth, pl.paperHeight, isLandscape);
var ht =  new java.util.Hashtable ();
ht.put ("Producer", JSV.common.JSVersion.VERSION);
ht.put ("Creator", "JSpecView " + JSV.common.JSVersion.VERSION);
ht.put ("Author", "JSpecView");
if (this.date != null) ht.put ("CreationDate", this.date);
this.pdf.addInfo (ht);
panel.getPanelData ().printPdf (this, pl);
this.pdf.closeDocument ();
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
e.printStackTrace ();
panel.showMessage (e.toString (), "PDF Creation Error");
} else {
throw e;
}
}
}, "JSV.api.JSVPanel,JSV.common.PrintLayout,java.io.OutputStream");
$_V(c$, "canDoLineTo", 
function () {
return true;
});
$_V(c$, "doStroke", 
function (g, isBegin) {
this.inPath = isBegin;
if (!this.inPath) this.pdf.stroke ();
}, "~O,~B");
$_V(c$, "drawCircle", 
function (g, x, y, diameter) {
this.pdf.doCircle (x, y, Clazz_doubleToInt (diameter / 2.0), false);
}, "~O,~N,~N,~N");
$_V(c$, "drawLine", 
function (g, x0, y0, x1, y1) {
this.pdf.moveto (x0, y0);
this.pdf.lineto (x1, y1);
if (!this.inPath) this.pdf.stroke ();
}, "~O,~N,~N,~N,~N");
$_V(c$, "drawPolygon", 
function (g, axPoints, ayPoints, nPoints) {
this.pdf.doPolygon (axPoints, ayPoints, nPoints, false);
}, "~O,~A,~A,~N");
$_V(c$, "drawRect", 
function (g, x, y, width, height) {
this.pdf.doRect (x, y, width, height, false);
}, "~O,~N,~N,~N,~N");
$_V(c$, "drawString", 
function (g, s, x, y) {
this.pdf.drawStringRotated (s, x, y, 0);
}, "~O,~S,~N,~N");
$_V(c$, "drawStringRotated", 
function (g, s, x, y, angle) {
this.pdf.drawStringRotated (s, x, y, Clazz_doubleToInt (angle));
}, "~O,~S,~N,~N,~N");
$_V(c$, "fillBackground", 
function (g, bgcolor) {
}, "~O,javajs.api.GenericColor");
$_V(c$, "fillCircle", 
function (g, x, y, diameter) {
this.pdf.doCircle (x, y, Clazz_doubleToInt (diameter / 2.0), true);
}, "~O,~N,~N,~N");
$_V(c$, "fillPolygon", 
function (g, ayPoints, axPoints, nPoints) {
this.pdf.doPolygon (axPoints, ayPoints, nPoints, true);
}, "~O,~A,~A,~N");
$_V(c$, "fillRect", 
function (g, x, y, width, height) {
this.pdf.doRect (x, y, width, height, true);
}, "~O,~N,~N,~N,~N");
$_V(c$, "lineTo", 
function (g, x, y) {
this.pdf.lineto (x, y);
}, "~O,~N,~N");
$_V(c$, "setGraphicsColor", 
function (g, c) {
JU.CU.toRGB3f (c.getRGB (), this.rgb);
this.pdf.setColor (this.rgb, true);
this.pdf.setColor (this.rgb, false);
}, "~O,javajs.api.GenericColor");
$_V(c$, "setFont", 
function (g, font) {
var fname = "/Helvetica";
switch (font.idFontStyle) {
case 1:
fname += "-Bold";
break;
case 3:
fname += "-BoldOblique";
break;
case 2:
fname += "-Oblique";
break;
}
this.pdf.setFont (fname, font.fontSizeNominal);
return font;
}, "~O,javajs.awt.Font");
$_V(c$, "setStrokeBold", 
function (g, tf) {
this.pdf.setLineWidth (tf ? 2 : 1);
}, "~O,~B");
$_V(c$, "translateScale", 
function (g, x, y, scale) {
this.pdf.translateScale (x, y, scale);
}, "~O,~N,~N,~N");
$_V(c$, "newGrayScaleImage", 
function (g, image, width, height, buffer) {
this.pdf.addImageResource (image, width, height, buffer, false);
return image;
}, "~O,~O,~N,~N,~A");
$_V(c$, "drawGrayScaleImage", 
function (g, image, destX0, destY0, destX1, destY1, srcX0, srcY0, srcX1, srcY1) {
this.pdf.drawImage (image, destX0, destY0, destX1, destY1, srcX0, srcY0, srcX1, srcY1);
}, "~O,~O,~N,~N,~N,~N,~N,~N,~N,~N");
$_V(c$, "setWindowParameters", 
function (width, height) {
}, "~N,~N");
$_M(c$, "getColor1", 
function (argb) {
return this.g2d.getColor1 (argb);
}, "~N");
$_M(c$, "getColor3", 
function (red, green, blue) {
return this.g2d.getColor3 (red, green, blue);
}, "~N,~N,~N");
$_M(c$, "getColor4", 
function (r, g, b, a) {
return this.g2d.getColor4 (r, g, b, a);
}, "~N,~N,~N,~N");
});
Clazz_declarePackage ("JSV.tree");
c$ = Clazz_decorateAsClass (function () {
this.rootNode = null;
Clazz_instantialize (this, arguments);
}, JSV.tree, "SimpleTreeModel");
Clazz_makeConstructor (c$, 
function (rootNode) {
this.rootNode = rootNode;
}, "JSV.api.JSVTreeNode");
$_M(c$, "insertNodeInto", 
function (fileNode, rootNode, i) {
var node = rootNode;
node.$children.add (i, fileNode);
(fileNode).prevNode = node;
}, "JSV.api.JSVTreeNode,JSV.api.JSVTreeNode,~N");
$_M(c$, "removeNodeFromParent", 
function (node) {
(node).prevNode.$children.removeObj (node);
}, "JSV.api.JSVTreeNode");
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVTreeNode");
Clazz_declarePackage ("JSV.tree");
Clazz_load (["JSV.api.JSVTreeNode"], "JSV.tree.SimpleTreeNode", ["JU.List", "JSV.tree.SimpleTreeEnumeration"], function () {
c$ = Clazz_decorateAsClass (function () {
this.panelNode = null;
this.index = 0;
this.prevNode = null;
this.$children = null;
this.text = null;
Clazz_instantialize (this, arguments);
}, JSV.tree, "SimpleTreeNode", null, JSV.api.JSVTreeNode);
Clazz_makeConstructor (c$, 
function (text, panelNode) {
this.text = text;
this.panelNode = panelNode;
this.$children =  new JU.List ();
}, "~S,JSV.common.PanelNode");
$_V(c$, "getPanelNode", 
function () {
return this.panelNode;
});
$_V(c$, "getIndex", 
function () {
return this.index;
});
$_V(c$, "setIndex", 
function (index) {
this.index = index;
}, "~N");
$_V(c$, "children", 
function () {
return  new JSV.tree.SimpleTreeEnumeration (this);
});
$_V(c$, "getChildCount", 
function () {
return this.$children.size ();
});
$_V(c$, "getPath", 
function () {
var o =  new JU.List ();
var node = this;
o.addLast (node);
while ((node = node.prevNode) != null) o.add (0, node);

return o.toArray ();
});
$_V(c$, "isLeaf", 
function () {
return (this.prevNode != null && this.prevNode.prevNode != null);
});
$_V(c$, "toString", 
function () {
return this.text;
});
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVTreePath");
Clazz_declarePackage ("JSV.tree");
Clazz_load (["JSV.api.JSVTreePath"], "JSV.tree.SimpleTreePath", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.path = null;
Clazz_instantialize (this, arguments);
}, JSV.tree, "SimpleTreePath", null, JSV.api.JSVTreePath);
Clazz_makeConstructor (c$, 
function (path) {
this.path = path;
}, "~A");
$_V(c$, "getLastPathComponent", 
function () {
return (this.path == null || this.path.length == 0 ? null : this.path[this.path.length - 1]);
});
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVZipReader");
Clazz_declarePackage ("JSV.exception");
Clazz_load (["JSV.exception.JSpecViewException"], "JSV.exception.JDXSourceException", null, function () {
c$ = Clazz_declareType (JSV.exception, "JDXSourceException", JSV.exception.JSpecViewException);
});
Clazz_declarePackage ("JSV.source");
Clazz_load (null, "JSV.source.JDXDecompressor", ["java.lang.Double", "JSV.common.Coordinate", "J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.xFactor = 0;
this.yFactor = 0;
this.deltaX = 0;
this.nPoints = 0;
this.ich = 0;
this.lineNumber = 0;
this.t = null;
this.firstX = 0;
this.dx = 0;
this.maxY = 4.9E-324;
this.minY = 1.7976931348623157E308;
this.debugging = false;
this.xyCoords = null;
this.ipt = 0;
this.line = null;
this.lastLine = null;
this.lineLen = 0;
this.errorLog = null;
this.difVal = -2147483648;
this.lastDif = -2147483648;
this.dupCount = 0;
this.xval = 0;
this.yval = 0;
this.firstLastX = null;
Clazz_instantialize (this, arguments);
}, JSV.source, "JDXDecompressor");
$_M(c$, "getMinY", 
function () {
return this.minY;
});
$_M(c$, "getMaxY", 
function () {
return this.maxY;
});
Clazz_makeConstructor (c$, 
function (t, firstX, xFactor, yFactor, deltaX, nPoints) {
this.t = t;
this.firstX = firstX;
this.xFactor = xFactor;
this.yFactor = yFactor;
this.deltaX = deltaX;
this.nPoints = nPoints;
this.lineNumber = t.getLabelLineNo ();
this.debugging = J.util.Logger.isActiveLevel (6);
}, "JSV.source.JDXSourceStreamTokenizer,~N,~N,~N,~N,~N");
$_M(c$, "addPoint", 
function (pt) {
if (this.ipt == this.xyCoords.length) {
var t =  new Array (this.ipt * 2);
System.arraycopy (this.xyCoords, 0, t, 0, this.ipt);
this.xyCoords = t;
}var d = pt.getYVal ();
if (d > this.maxY) this.maxY = d;
 else if (d < this.minY) this.minY = d;
if (this.debugging) this.logError ("Coord: " + this.ipt + pt);
this.xyCoords[this.ipt++] = pt;
this.firstLastX[1] = pt.getXVal ();
}, "JSV.common.Coordinate");
$_M(c$, "decompressData", 
function (errorLog, firstLastX) {
this.errorLog = errorLog;
this.firstLastX = firstLastX;
if (this.debugging) this.logError ("firstX=" + this.firstX + " xFactor=" + this.xFactor + " yFactor=" + this.yFactor + " deltaX=" + this.deltaX + " nPoints=" + this.nPoints);
this.testAlgorithm ();
this.xyCoords =  new Array (this.nPoints);
var difMax = Math.abs (0.35 * this.deltaX);
var dif14 = Math.abs (1.4 * this.deltaX);
var dif06 = Math.abs (0.6 * this.deltaX);
try {
while ((this.line = this.t.readLineTrimmed ()) != null && this.line.indexOf ("##") < 0) {
this.lineNumber++;
if (this.debugging) this.logError (this.lineNumber + "\t" + this.line);
if ((this.lineLen = this.line.length) == 0) continue;
this.ich = 0;
var isCheckPoint = (this.lastDif != -2147483648);
this.xval = this.getValueDelim () * this.xFactor;
if (this.ipt == 0) {
firstLastX[0] = this.xval;
this.dx = this.firstX - this.xval;
}this.xval += this.dx;
var point =  new JSV.common.Coordinate ().set (this.xval, (this.yval = this.getYValue ()) * this.yFactor);
if (this.ipt == 0) {
this.addPoint (point);
} else {
var lastPoint = this.xyCoords[this.ipt - 1];
var xdif = Math.abs (lastPoint.getXVal () - point.getXVal ());
if (isCheckPoint && xdif < difMax) {
this.xyCoords[this.ipt - 1] = point;
var y = lastPoint.getYVal ();
var y1 = point.getYVal ();
if (y1 != y) this.logError (this.lastLine + "\n" + this.line + "\nY-value Checkpoint Error! Line " + this.lineNumber + " for y1=" + y1 + " y0=" + y);
} else {
this.addPoint (point);
if (xdif < dif06 || xdif > dif14) this.logError (this.lastLine + "\n" + this.line + "\nX-sequence Checkpoint Error! Line " + this.lineNumber + " |x1-x0|=" + xdif + " instead of " + Math.abs (this.deltaX) + " for x1=" + point.getXVal () + " x0=" + lastPoint.getXVal ());
}}while (this.ich < this.lineLen || this.difVal != -2147483648 || this.dupCount > 0) {
this.xval += this.deltaX;
if (!Double.isNaN (this.yval = this.getYValue ())) this.addPoint ( new JSV.common.Coordinate ().set (this.xval, this.yval * this.yFactor));
}
this.lastLine = this.line;
}
} catch (ioe) {
if (Clazz_exceptionOf (ioe, java.io.IOException)) {
} else {
throw ioe;
}
}
if (this.nPoints != this.ipt) {
this.logError ("Decompressor did not find " + this.nPoints + " points -- instead " + this.ipt);
var temp =  new Array (this.ipt);
System.arraycopy (this.xyCoords, 0, temp, 0, this.ipt);
this.xyCoords = temp;
}return (this.deltaX > 0 ? this.xyCoords : JSV.common.Coordinate.reverse (this.xyCoords));
}, "JU.SB,~A");
$_M(c$, "logError", 
function (s) {
if (this.debugging) J.util.Logger.debug (s);
this.errorLog.append (s).appendC ('\n');
}, "~S");
$_M(c$, "getYValue", 
function () {
if (this.dupCount > 0) {
--this.dupCount;
this.yval = (this.lastDif == -2147483648 ? this.yval : this.yval + this.lastDif);
return this.yval;
}if (this.difVal != -2147483648) {
this.yval += this.difVal;
this.lastDif = this.difVal;
this.difVal = -2147483648;
return this.yval;
}if (this.ich == this.lineLen) return NaN;
var ch = this.line.charAt (this.ich);
if (this.debugging) J.util.Logger.info ("" + ch);
switch (ch) {
case '%':
this.difVal = 0;
break;
case 'J':
case 'K':
case 'L':
case 'M':
case 'N':
case 'O':
case 'P':
case 'Q':
case 'R':
this.difVal = ch.charCodeAt (0) - 73;
break;
case 'j':
case 'k':
case 'l':
case 'm':
case 'n':
case 'o':
case 'p':
case 'q':
case 'r':
this.difVal = 105 - ch.charCodeAt (0);
break;
case 'S':
case 'T':
case 'U':
case 'V':
case 'W':
case 'X':
case 'Y':
case 'Z':
this.dupCount = ch.charCodeAt (0) - 82;
break;
case 's':
this.dupCount = 9;
break;
case '+':
case '-':
case '.':
case '0':
case '1':
case '2':
case '3':
case '4':
case '5':
case '6':
case '7':
case '8':
case '9':
case '@':
case 'A':
case 'B':
case 'C':
case 'D':
case 'E':
case 'F':
case 'G':
case 'H':
case 'I':
case 'a':
case 'b':
case 'c':
case 'd':
case 'e':
case 'f':
case 'g':
case 'h':
case 'i':
this.lastDif = -2147483648;
return this.getValue ();
case '?':
this.lastDif = -2147483648;
return NaN;
default:
this.ich++;
this.lastDif = -2147483648;
return this.getYValue ();
}
this.ich++;
if (this.difVal != -2147483648) this.difVal = this.getDifDup (this.difVal);
 else this.dupCount = this.getDifDup (this.dupCount) - 1;
return this.getYValue ();
});
$_M(c$, "getDifDup", 
function (i) {
var ich0 = this.ich;
this.next ();
var s = i + this.line.substring (ich0, this.ich);
return (ich0 == this.ich ? i : Integer.$valueOf (s).intValue ());
}, "~N");
$_M(c$, "getValue", 
function () {
var ich0 = this.ich;
if (this.ich == this.lineLen) return NaN;
var ch = this.line.charAt (this.ich);
var leader = 0;
switch (ch) {
case '+':
case '-':
case '.':
case '0':
case '1':
case '2':
case '3':
case '4':
case '5':
case '6':
case '7':
case '8':
case '9':
return this.getValueDelim ();
case '@':
case 'A':
case 'B':
case 'C':
case 'D':
case 'E':
case 'F':
case 'G':
case 'H':
case 'I':
leader = ch.charCodeAt (0) - 64;
ich0 = ++this.ich;
break;
case 'a':
case 'b':
case 'c':
case 'd':
case 'e':
case 'f':
case 'g':
case 'h':
case 'i':
leader = 96 - ch.charCodeAt (0);
ich0 = ++this.ich;
break;
default:
this.ich++;
return this.getValue ();
}
this.next ();
return Double.$valueOf (leader + this.line.substring (ich0, this.ich)).doubleValue ();
});
$_M(c$, "getValueDelim", 
function () {
var ich0 = this.ich;
var ch = '\u0000';
while (this.ich < this.lineLen && " ,\t\n".indexOf (ch = this.line.charAt (this.ich)) >= 0) this.ich++;

var factor = 1;
switch (ch) {
case '-':
factor = -1;
case '+':
ich0 = ++this.ich;
break;
}
ch = this.next ();
if (ch == 'E' && this.ich + 3 < this.lineLen) switch (this.line.charAt (this.ich + 1)) {
case '-':
case '+':
this.ich += 4;
if (this.ich < this.lineLen && (ch = this.line.charAt (this.ich)) >= '0' && ch <= '9') this.ich++;
break;
}
return factor * ((Double.$valueOf (this.line.substring (ich0, this.ich))).doubleValue ());
});
$_M(c$, "next", 
function () {
while (this.ich < this.lineLen && "+-%@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrs? ,\t\n".indexOf (this.line.charAt (this.ich)) < 0) this.ich++;

return (this.ich == this.lineLen ? '\0' : this.line.charAt (this.ich));
});
$_M(c$, "testAlgorithm", 
function () {
});
Clazz_defineStatics (c$,
"allDelim", "+-%@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrs? ,\t\n",
"WHITE_SPACE", " ,\t\n");
});
Clazz_declarePackage ("javajs.export");
Clazz_load (null, "javajs.export.PDFCreator", ["java.lang.Double", "java.util.Hashtable", "javajs.export.PDFObject", "JU.List", "$.SB"], function () {
c$ = Clazz_decorateAsClass (function () {
this.os = null;
this.indirectObjects = null;
this.root = null;
this.graphics = null;
this.pt = 0;
this.xrefPt = 0;
this.count = 0;
this.height = 0;
this.width = 0;
this.fonts = null;
this.images = null;
Clazz_instantialize (this, arguments);
}, javajs["export"], "PDFCreator");
Clazz_makeConstructor (c$, 
function () {
});
$_M(c$, "setOutputStream", 
function (os) {
this.os = os;
}, "java.io.OutputStream");
$_M(c$, "newDocument", 
function (paperWidth, paperHeight, isLandscape) {
this.width = (isLandscape ? paperHeight : paperWidth);
this.height = (isLandscape ? paperWidth : paperHeight);
System.out.println ("Creating PDF with width=" + this.width + " and height=" + this.height);
this.fonts =  new java.util.Hashtable ();
this.indirectObjects =  new JU.List ();
this.root = this.newObject ("Catalog");
var pages = this.newObject ("Pages");
var page = this.newObject ("Page");
var pageContents = this.newObject (null);
this.graphics = this.newObject ("XObject");
this.root.addDef ("Pages", pages.getRef ());
pages.addDef ("Count", "1");
pages.addDef ("Kids", "[ " + page.getRef () + " ]");
page.addDef ("Parent", pages.getRef ());
page.addDef ("MediaBox", "[ 0 0 " + paperWidth + " " + paperHeight + " ]");
if (isLandscape) page.addDef ("Rotate", "90");
pageContents.addDef ("Length", "?");
pageContents.append ((isLandscape ? "q 0 1 1 0 0 0 " : "q 1 0 0 -1 0 " + (paperHeight)) + " cm /" + this.graphics.getID () + " Do Q");
page.addDef ("Contents", pageContents.getRef ());
this.addProcSet (page);
this.addProcSet (this.graphics);
this.graphics.addDef ("Subtype", "/Form");
this.graphics.addDef ("FormType", "1");
this.graphics.addDef ("BBox", "[0 0 " + this.width + " " + this.height + "]");
this.graphics.addDef ("Matrix", "[1 0 0 1 0 0]");
this.graphics.addDef ("Length", "?");
page.addResource ("XObject", this.graphics.getID (), this.graphics.getRef ());
this.g ("q 1 w 1 J 1 j 10 M []0 d q ");
this.clip (0, 0, this.width, this.height);
}, "~N,~N,~B");
$_M(c$, "addProcSet", 
function (o) {
o.addResource (null, "ProcSet", "[/PDF /Text /ImageB /ImageC /ImageI]");
}, "javajs.export.PDFObject");
$_M(c$, "clip", 
function (x1, y1, x2, y2) {
this.moveto (x1, y1);
this.lineto (x2, y1);
this.lineto (x2, y2);
this.lineto (x1, y2);
this.g ("h W n");
}, "~N,~N,~N,~N");
$_M(c$, "moveto", 
function (x, y) {
this.g (x + " " + y + " m");
}, "~N,~N");
$_M(c$, "lineto", 
function (x, y) {
this.g (x + " " + y + " l");
}, "~N,~N");
$_M(c$, "newObject", 
function (type) {
var o =  new javajs["export"].PDFObject (++this.count);
if (type != null) o.addDef ("Type", "/" + type);
this.indirectObjects.addLast (o);
return o;
}, "~S");
$_M(c$, "addInfo", 
function (data) {
var info =  new java.util.Hashtable ();
for (var e, $e = data.entrySet ().iterator (); $e.hasNext () && ((e = $e.next ()) || true);) {
var value = "(" + e.getValue ().$replace (')', '_').$replace ('(', '_') + ")";
info.put (e.getKey (), value);
}
this.root.addDef ("Info", info);
}, "java.util.Map");
$_M(c$, "addFontResource", 
function (fname) {
var f = this.newObject ("Font");
this.fonts.put (fname, f);
f.addDef ("BaseFont", fname);
f.addDef ("Encoding", "/WinAnsiEncoding");
f.addDef ("Subtype", "/Type1");
this.graphics.addResource ("Font", f.getID (), f.getRef ());
return f;
}, "~S");
$_M(c$, "addImageResource", 
function (newImage, width, height, buffer, isRGB) {
var imageObj = this.newObject ("XObject");
if (this.images == null) this.images =  new java.util.Hashtable ();
this.images.put (newImage, imageObj);
imageObj.addDef ("Subtype", "/Image");
imageObj.addDef ("Length", "?");
imageObj.addDef ("ColorSpace", isRGB ? "/DeviceRGB" : "/DeviceGray");
imageObj.addDef ("BitsPerComponent", "8");
imageObj.addDef ("Width", "" + width);
imageObj.addDef ("Height", "" + height);
this.graphics.addResource ("XObject", imageObj.getID (), imageObj.getRef ());
var n = buffer.length;
var stream =  Clazz_newByteArray (n * (isRGB ? 3 : 1), 0);
if (isRGB) {
for (var i = 0, pt = 0; i < n; i++) {
stream[pt++] = ((buffer[i] >> 16) & 0xFF);
stream[pt++] = ((buffer[i] >> 8) & 0xFF);
stream[pt++] = (buffer[i] & 0xFF);
}
} else {
for (var i = 0; i < n; i++) stream[i] = buffer[i];

}imageObj.setStream (stream);
this.graphics.addResource ("XObject", imageObj.getID (), imageObj.getRef ());
}, "~O,~N,~N,~A,~B");
$_M(c$, "g", 
function (cmd) {
this.graphics.append (cmd).appendC ('\n');
}, "~S");
$_M(c$, "output", 
function (s) {
var b = s.getBytes ();
this.os.write (b, 0, b.length);
this.pt += b.length;
}, "~S");
$_M(c$, "closeDocument", 
function () {
this.g ("Q Q");
this.outputHeader ();
this.writeObjects ();
this.writeXRefTable ();
this.writeTrailer ();
this.os.flush ();
this.os.close ();
});
$_M(c$, "outputHeader", 
function () {
this.output ("%PDF-1.3\n%");
var b = [-1, -1, -1, -1];
this.os.write (b, 0, b.length);
this.pt += 4;
this.output ("\n");
});
$_M(c$, "writeTrailer", 
function () {
var trailer =  new javajs["export"].PDFObject (-2);
this.output ("trailer");
trailer.addDef ("Size", "" + this.indirectObjects.size ());
trailer.addDef ("Root", this.root.getRef ());
trailer.output (this.os);
this.output ("startxref\n");
this.output ("" + this.xrefPt + "\n");
this.output ("%%EOF\n");
});
$_M(c$, "writeObjects", 
function () {
var nObj = this.indirectObjects.size ();
for (var i = 0; i < nObj; i++) {
var o = this.indirectObjects.get (i);
if (!o.isFont ()) continue;
o.pt = this.pt;
this.pt += o.output (this.os);
}
for (var i = 0; i < nObj; i++) {
var o = this.indirectObjects.get (i);
if (o.isFont ()) continue;
o.pt = this.pt;
this.pt += o.output (this.os);
}
});
$_M(c$, "writeXRefTable", 
function () {
this.xrefPt = this.pt;
var nObj = this.indirectObjects.size ();
var sb =  new JU.SB ();
sb.append ("xref\n0 " + (nObj + 1) + "\n0000000000 65535 f\r\n");
for (var i = 0; i < nObj; i++) {
var o = this.indirectObjects.get (i);
var s = "0000000000" + o.pt;
sb.append (s.substring (s.length - 10));
sb.append (" 00000 n\r\n");
}
this.output (sb.toString ());
});
$_M(c$, "canDoLineTo", 
function () {
return true;
});
$_M(c$, "fill", 
function () {
this.g ("f");
});
$_M(c$, "stroke", 
function () {
this.g ("S");
});
$_M(c$, "doCircle", 
function (x, y, r, doFill) {
var d = r * 4 * (Math.sqrt (2) - 1) / 3;
var dx = x;
var dy = y;
this.g ((dx + r) + " " + dy + " m");
this.g ((dx + r) + " " + (dy + d) + " " + (dx + d) + " " + (dy + r) + " " + (dx) + " " + (dy + r) + " " + " c");
this.g ((dx - d) + " " + (dy + r) + " " + (dx - r) + " " + (dy + d) + " " + (dx - r) + " " + (dy) + " c");
this.g ((dx - r) + " " + (dy - d) + " " + (dx - d) + " " + (dy - r) + " " + (dx) + " " + (dy - r) + " c");
this.g ((dx + d) + " " + (dy - r) + " " + (dx + r) + " " + (dy - d) + " " + (dx + r) + " " + (dy) + " c");
this.g (doFill ? "f" : "s");
}, "~N,~N,~N,~B");
$_M(c$, "doPolygon", 
function (axPoints, ayPoints, nPoints, doFill) {
this.moveto (axPoints[0], ayPoints[0]);
for (var i = 1; i < nPoints; i++) this.lineto (axPoints[i], ayPoints[i]);

this.g (doFill ? "f" : "s");
}, "~A,~A,~N,~B");
$_M(c$, "doRect", 
function (x, y, width, height, doFill) {
this.g (x + " " + y + " " + width + " " + height + " re " + (doFill ? "f" : "s"));
}, "~N,~N,~N,~N,~B");
$_M(c$, "drawImage", 
function (image, destX0, destY0, destX1, destY1, srcX0, srcY0, srcX1, srcY1) {
var imageObj = this.images.get (image);
if (imageObj == null) return;
this.g ("q");
this.clip (destX0, destY0, destX1, destY1);
var iw = Double.parseDouble (imageObj.getDef ("Width"));
var ih = Double.parseDouble (imageObj.getDef ("Height"));
var dw = (destX1 - destX0 + 1);
var dh = (destY1 - destY0 + 1);
var sw = (srcX1 - srcX0 + 1);
var sh = (srcY1 - srcY0 + 1);
var scaleX = dw / sw;
var scaleY = dh / sh;
var transX = destX0 - srcX0 * scaleX;
var transY = destY0 + (ih - srcY0) * scaleY;
this.g (scaleX * iw + " 0 0 " + -scaleY * ih + " " + transX + " " + transY + " cm");
this.g ("/" + imageObj.getID () + " Do");
this.g ("Q");
}, "~O,~N,~N,~N,~N,~N,~N,~N,~N");
$_M(c$, "drawStringRotated", 
function (s, x, y, angle) {
this.g ("q " + this.getRotation (angle) + " " + x + " " + y + " cm BT(" + s + ")Tj ET Q");
}, "~S,~N,~N,~N");
$_M(c$, "getRotation", 
function (angle) {
var cos = 0;
var sin = 0;
switch (angle) {
case 0:
cos = 1;
break;
case 90:
sin = 1;
break;
case -90:
sin = -1;
break;
case 180:
cos = -1;
break;
default:
var a = (angle / 180.0 * 3.141592653589793);
cos = Math.cos (a);
sin = Math.sin (a);
if (Math.abs (cos) < 0.0001) cos = 0;
if (Math.abs (sin) < 0.0001) sin = 0;
}
return cos + " " + sin + " " + sin + " " + -cos;
}, "~N");
$_M(c$, "setColor", 
function (rgb, isFill) {
this.g (rgb[0] + " " + rgb[1] + " " + rgb[2] + (isFill ? " rg" : " RG"));
}, "~A,~B");
$_M(c$, "setFont", 
function (fname, size) {
var f = this.fonts.get (fname);
if (f == null) f = this.addFontResource (fname);
this.g ("/" + f.getID () + " " + size + " Tf");
}, "~S,~N");
$_M(c$, "setLineWidth", 
function (width) {
this.g (width + " w");
}, "~N");
$_M(c$, "translateScale", 
function (x, y, scale) {
this.g (scale + " 0 0 " + scale + " " + x + " " + y + " cm");
}, "~N,~N,~N");
});
Clazz_declarePackage ("JSV.tree");
Clazz_load (["java.util.Enumeration"], "JSV.tree.SimpleTreeEnumeration", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.node = null;
this.pt = 0;
Clazz_instantialize (this, arguments);
}, JSV.tree, "SimpleTreeEnumeration", null, java.util.Enumeration);
Clazz_makeConstructor (c$, 
function (jsTreeNode) {
this.node = jsTreeNode;
}, "JSV.tree.SimpleTreeNode");
$_V(c$, "hasMoreElements", 
function () {
return (this.pt < this.node.$children.size ());
});
$_V(c$, "nextElement", 
function () {
return this.node.$children.get (this.pt++);
});
});
Clazz_declarePackage ("javajs.export");
Clazz_load (["JU.SB"], "javajs.export.PDFObject", ["java.io.ByteArrayOutputStream", "java.util.Hashtable", "$.Map", "java.util.zip.Deflater", "$.DeflaterOutputStream"], function () {
c$ = Clazz_decorateAsClass (function () {
this.dictionary = null;
this.stream = null;
this.index = 0;
this.type = null;
this.len = 0;
this.pt = 0;
Clazz_instantialize (this, arguments);
}, javajs["export"], "PDFObject", JU.SB);
Clazz_makeConstructor (c$, 
function (index) {
Clazz_superConstructor (this, javajs["export"].PDFObject, []);
this.index = index;
}, "~N");
$_M(c$, "getRef", 
function () {
return this.index + " 0 R";
});
$_M(c$, "getID", 
function () {
return this.type.substring (0, 1) + this.index;
});
$_M(c$, "isFont", 
function () {
return "Font".equals (this.type);
});
$_M(c$, "setStream", 
function (stream) {
this.stream = stream;
}, "~A");
$_M(c$, "getDef", 
function (key) {
return this.dictionary.get (key);
}, "~S");
$_M(c$, "addDef", 
function (key, value) {
if (this.dictionary == null) this.dictionary =  new java.util.Hashtable ();
this.dictionary.put (key, value);
if (key.equals ("Type")) this.type = (value).substring (1);
}, "~S,~O");
$_M(c$, "setAsStream", 
function () {
this.stream = this.toBytes (0, -1);
this.setLength (0);
});
$_M(c$, "output", 
function (os) {
if (this.index > 0) {
var s = this.index + " 0 obj\n";
this.write (os, s.getBytes (), 0);
}var streamLen = 0;
if (this.dictionary != null) {
if (this.dictionary.containsKey ("Length")) {
if (this.stream == null) this.setAsStream ();
streamLen = this.stream.length;
var doDeflate = (streamLen > 1000);
if (doDeflate) {
var deflater =  new java.util.zip.Deflater (9);
var outBytes =  new java.io.ByteArrayOutputStream (1024);
var compBytes =  new java.util.zip.DeflaterOutputStream (outBytes, deflater);
compBytes.write (this.stream, 0, streamLen);
compBytes.finish ();
this.stream = outBytes.toByteArray ();
this.dictionary.put ("Filter", "/FlateDecode");
streamLen = this.stream.length;
}this.dictionary.put ("Length", "" + streamLen);
}this.write (os, this.getDictionaryText (this.dictionary, "\n").getBytes (), 0);
}if (this.length () > 0) this.write (os, this.toString ().getBytes (), 0);
if (this.stream != null) {
this.write (os, "stream\r\n".getBytes (), 0);
this.write (os, this.stream, streamLen);
this.write (os, "\r\nendstream\r\n".getBytes (), 0);
}if (this.index > 0) this.write (os, "endobj\n".getBytes (), 0);
return this.len;
}, "java.io.OutputStream");
$_M(c$, "write", 
function (os, bytes, nBytes) {
if (nBytes == 0) nBytes = bytes.length;
this.len += nBytes;
os.write (bytes, 0, nBytes);
}, "java.io.OutputStream,~A,~N");
$_M(c$, "getDictionaryText", 
function (d, nl) {
var sb =  new JU.SB ();
sb.append ("<<");
if (d.containsKey ("Type")) sb.append ("/Type").appendO (d.get ("Type"));
for (var e, $e = d.entrySet ().iterator (); $e.hasNext () && ((e = $e.next ()) || true);) {
var s = e.getKey ();
if (s.equals ("Type") || s.startsWith ("!")) continue;
sb.append ("/" + s);
var o = e.getValue ();
if (Clazz_instanceOf (o, java.util.Map)) {
sb.append ((this.getDictionaryText (o, "")));
continue;
}s = e.getValue ();
if (!s.startsWith ("/")) sb.append (" ");
sb.appendO (s);
}
return (sb.length () > 3 ? sb.append (">>").append (nl).toString () : "");
}, "java.util.Map,~S");
$_M(c$, "createSubdict", 
function (d0, dict) {
var d = d0.get (dict);
if (d == null) d0.put (dict, d =  new java.util.Hashtable ());
return d;
}, "java.util.Map,~S");
$_M(c$, "addResource", 
function (type, key, value) {
var r = this.createSubdict (this.dictionary, "Resources");
if (type != null) r = this.createSubdict (r, type);
r.put (key, value);
}, "~S,~S,~S");
});
Clazz_declarePackage ("JZ");
Clazz_load (["JZ.ZStream"], "JZ.Deflater", ["JZ.Deflate"], function () {
c$ = Clazz_decorateAsClass (function () {
this.$finished = false;
Clazz_instantialize (this, arguments);
}, JZ, "Deflater", JZ.ZStream);
$_M(c$, "init", 
function (level, bits, nowrap) {
if (bits == 0) bits = 15;
this.$finished = false;
this.setAdler32 ();
this.dstate =  new JZ.Deflate (this);
this.dstate.deflateInit2 (level, nowrap ? -bits : bits);
return this;
}, "~N,~N,~B");
$_V(c$, "deflate", 
function (flush) {
if (this.dstate == null) {
return -2;
}var ret = this.dstate.deflate (flush);
if (ret == 1) this.$finished = true;
return ret;
}, "~N");
$_V(c$, "end", 
function () {
this.$finished = true;
if (this.dstate == null) return -2;
var ret = this.dstate.deflateEnd ();
this.dstate = null;
this.free ();
return ret;
});
$_M(c$, "params", 
function (level, strategy) {
if (this.dstate == null) return -2;
return this.dstate.deflateParams (level, strategy);
}, "~N,~N");
$_M(c$, "setDictionary", 
function (dictionary, dictLength) {
if (this.dstate == null) return -2;
return this.dstate.deflateSetDictionary (dictionary, dictLength);
}, "~A,~N");
$_V(c$, "finished", 
function () {
return this.$finished;
});
$_M(c$, "finish", 
function () {
});
$_M(c$, "getBytesRead", 
function () {
return this.dstate.getBytesRead ();
});
$_M(c$, "getBytesWritten", 
function () {
return this.dstate.getBytesWritten ();
});
Clazz_defineStatics (c$,
"MAX_WBITS", 15,
"Z_STREAM_END", 1,
"$Z_STREAM_ERROR", -2);
});
Clazz_declarePackage ("java.util.zip");
Clazz_load (["JZ.Deflater"], "java.util.zip.Deflater", null, function () {
c$ = Clazz_declareType (java.util.zip, "Deflater", JZ.Deflater);
Clazz_makeConstructor (c$, 
function (compressionLevel) {
if (compressionLevel != 2147483647) this.init (compressionLevel, 0, false);
}, "~N");
Clazz_defineStatics (c$,
"DEFAULT_COMPRESSION", -1);
});
Clazz_load (["java.io.OutputStream"], "java.io.FilterOutputStream", ["java.lang.IndexOutOfBoundsException"], function () {
c$ = Clazz_decorateAsClass (function () {
this.out = null;
Clazz_instantialize (this, arguments);
}, java.io, "FilterOutputStream", java.io.OutputStream);
$_M(c$, "jzSetFOS", 
function (out) {
this.out = out;
}, "java.io.OutputStream");
$_M(c$, "writeByteAsInt", 
function (b) {
this.out.writeByteAsInt (b);
}, "~N");
$_M(c$, "write", 
function (b, off, len) {
if ((off | len | (b.length - (len + off)) | (off + len)) < 0) throw  new IndexOutOfBoundsException ();
for (var i = 0; i < len; i++) {
this.writeByteAsInt (b[off + i]);
}
}, "~A,~N,~N");
$_M(c$, "flush", 
function () {
this.out.flush ();
});
$_M(c$, "close", 
function () {
try {
this.flush ();
} catch (ignored) {
if (Clazz_exceptionOf (ignored, java.io.IOException)) {
} else {
throw ignored;
}
}
this.out.close ();
});
});
Clazz_declarePackage ("JZ");
Clazz_load (["java.io.FilterOutputStream"], "JZ.DeflaterOutputStream", ["java.io.IOException", "java.lang.IndexOutOfBoundsException"], function () {
c$ = Clazz_decorateAsClass (function () {
this.deflater = null;
this.buffer = null;
this.closed = false;
this.syncFlush = false;
this.buf1 = null;
this.mydeflater = false;
this.close_out = true;
Clazz_instantialize (this, arguments);
}, JZ, "DeflaterOutputStream", java.io.FilterOutputStream);
Clazz_prepareFields (c$, function () {
this.buf1 =  Clazz_newByteArray (1, 0);
});
$_M(c$, "jzSetDOS", 
function (out, deflater, size, close_out) {
this.jzSetFOS (out);
if (size == 0) size = 512;
this.deflater = deflater;
this.buffer =  Clazz_newByteArray (size, 0);
this.close_out = close_out;
}, "java.io.OutputStream,JZ.Deflater,~N,~B");
$_V(c$, "writeByteAsInt", 
function (b) {
this.buf1[0] = (b & 0xff);
this.write (this.buf1, 0, 1);
}, "~N");
$_M(c$, "write", 
function (b, off, len) {
if (this.deflater.finished ()) throw  new java.io.IOException ("finished");
if ( new Boolean ( new Boolean (off < 0 | len < 0).valueOf () | off + len > b.length).valueOf ()) throw  new IndexOutOfBoundsException ();
if (len == 0) return;
var flush = this.syncFlush ? 2 : 0;
this.deflater.setInput (b, off, len, true);
while (this.deflater.avail_in > 0) {
var err = this.deflate (flush);
if (err == 1) break;
}
}, "~A,~N,~N");
$_M(c$, "finish", 
function () {
while (!this.deflater.finished ()) {
this.deflate (4);
}
});
$_V(c$, "close", 
function () {
if (!this.closed) {
this.finish ();
if (this.mydeflater) {
this.deflater.end ();
}if (this.close_out) this.out.close ();
this.closed = true;
}});
$_M(c$, "deflate", 
function (flush) {
this.deflater.setOutput (this.buffer, 0, this.buffer.length);
var err = this.deflater.deflate (flush);
switch (err) {
case 0:
case 1:
break;
case -5:
if (this.deflater.avail_in <= 0 && flush != 4) {
break;
}default:
throw  new java.io.IOException ("failed to deflate");
}
var len = this.deflater.next_out_index;
if (len > 0) {
this.out.write (this.buffer, 0, len);
}return err;
}, "~N");
$_V(c$, "flush", 
function () {
if (this.syncFlush && !this.deflater.finished ()) {
while (true) {
var err = this.deflate (2);
if (this.deflater.next_out_index < this.buffer.length) break;
if (err == 1) break;
}
}this.out.flush ();
});
$_M(c$, "getTotalIn", 
function () {
return this.deflater.getTotalIn ();
});
$_M(c$, "getTotalOut", 
function () {
return this.deflater.getTotalOut ();
});
$_M(c$, "setSyncFlush", 
function (syncFlush) {
this.syncFlush = syncFlush;
}, "~B");
$_M(c$, "getSyncFlush", 
function () {
return this.syncFlush;
});
$_M(c$, "getDeflater", 
function () {
return this.deflater;
});
Clazz_defineStatics (c$,
"DEFAULT_BUFSIZE", 512);
});
Clazz_declarePackage ("java.util.zip");
Clazz_load (["JZ.DeflaterOutputStream"], "java.util.zip.DeflaterOutputStream", null, function () {
c$ = Clazz_declareType (java.util.zip, "DeflaterOutputStream", JZ.DeflaterOutputStream);
Clazz_makeConstructor (c$, 
function () {
Clazz_superConstructor (this, java.util.zip.DeflaterOutputStream, []);
});
Clazz_makeConstructor (c$, 
function (bos, deflater) {
Clazz_superConstructor (this, java.util.zip.DeflaterOutputStream, []);
this.setDOS (bos, deflater);
}, "java.io.ByteArrayOutputStream,java.util.zip.Deflater");
$_M(c$, "setDOS", 
function (out, deflater) {
this.jzSetDOS (out, deflater, 0, true);
}, "java.io.OutputStream,java.util.zip.Deflater");
});
Clazz_declarePackage ("JZ");
Clazz_load (["JZ.Tree"], "JZ.Deflate", ["JZ.CRC32", "$.GZIPHeader", "$.StaticTree"], function () {
c$ = Clazz_decorateAsClass (function () {
this.strm = null;
this.status = 0;
this.pending_buf = null;
this.pending_buf_size = 0;
this.pending_out = 0;
this.pending = 0;
this.wrap = 1;
this.data_type = 0;
this.method = 0;
this.last_flush = 0;
this.w_size = 0;
this.w_bits = 0;
this.w_mask = 0;
this.window = null;
this.window_size = 0;
this.prev = null;
this.head = null;
this.ins_h = 0;
this.hash_size = 0;
this.hash_bits = 0;
this.hash_mask = 0;
this.hash_shift = 0;
this.block_start = 0;
this.match_length = 0;
this.prev_match = 0;
this.match_available = 0;
this.strstart = 0;
this.match_start = 0;
this.lookahead = 0;
this.prev_length = 0;
this.max_chain_length = 0;
this.max_lazy_match = 0;
this.level = 0;
this.strategy = 0;
this.good_match = 0;
this.nice_match = 0;
this.dyn_ltree = null;
this.dyn_dtree = null;
this.bl_tree = null;
this.l_desc = null;
this.d_desc = null;
this.bl_desc = null;
this.bl_count = null;
this.heap = null;
this.heap_len = 0;
this.heap_max = 0;
this.depth = null;
this.l_buf = 0;
this.lit_bufsize = 0;
this.last_lit = 0;
this.d_buf = 0;
this.opt_len = 0;
this.static_len = 0;
this.matches = 0;
this.last_eob_len = 0;
this.bi_buf = 0;
this.bi_valid = 0;
this.gheader = null;
Clazz_instantialize (this, arguments);
}, JZ, "Deflate");
Clazz_prepareFields (c$, function () {
this.l_desc =  new JZ.Tree ();
this.d_desc =  new JZ.Tree ();
this.bl_desc =  new JZ.Tree ();
this.bl_count =  Clazz_newShortArray (16, 0);
this.heap =  Clazz_newIntArray (573, 0);
this.depth =  Clazz_newByteArray (573, 0);
});
Clazz_makeConstructor (c$, 
function (strm) {
this.strm = strm;
this.dyn_ltree =  Clazz_newShortArray (1146, 0);
this.dyn_dtree =  Clazz_newShortArray (122, 0);
this.bl_tree =  Clazz_newShortArray (78, 0);
}, "JZ.ZStream");
$_M(c$, "deflateInit", 
function (level) {
return this.deflateInit2 (level, 15);
}, "~N");
$_M(c$, "deflateInit2", 
function (level, bits) {
return this.deflateInit5 (level, 8, bits, 8, 0);
}, "~N,~N");
$_M(c$, "deflateInit3", 
function (level, bits, memlevel) {
return this.deflateInit5 (level, 8, bits, memlevel, 0);
}, "~N,~N,~N");
$_M(c$, "lm_init", 
function () {
this.window_size = 2 * this.w_size;
this.head[this.hash_size - 1] = 0;
for (var i = 0; i < this.hash_size - 1; i++) {
this.head[i] = 0;
}
this.max_lazy_match = JZ.Deflate.config_table[this.level].max_lazy;
this.good_match = JZ.Deflate.config_table[this.level].good_length;
this.nice_match = JZ.Deflate.config_table[this.level].nice_length;
this.max_chain_length = JZ.Deflate.config_table[this.level].max_chain;
this.strstart = 0;
this.block_start = 0;
this.lookahead = 0;
this.match_length = this.prev_length = 2;
this.match_available = 0;
this.ins_h = 0;
});
$_M(c$, "tr_init", 
function () {
this.l_desc.dyn_tree = this.dyn_ltree;
this.l_desc.stat_desc = JZ.StaticTree.static_l_desc;
this.d_desc.dyn_tree = this.dyn_dtree;
this.d_desc.stat_desc = JZ.StaticTree.static_d_desc;
this.bl_desc.dyn_tree = this.bl_tree;
this.bl_desc.stat_desc = JZ.StaticTree.static_bl_desc;
this.bi_buf = 0;
this.bi_valid = 0;
this.last_eob_len = 8;
this.init_block ();
});
$_M(c$, "init_block", 
function () {
for (var i = 0; i < 286; i++) this.dyn_ltree[i * 2] = 0;

for (var i = 0; i < 30; i++) this.dyn_dtree[i * 2] = 0;

for (var i = 0; i < 19; i++) this.bl_tree[i * 2] = 0;

this.dyn_ltree[512] = 1;
this.opt_len = this.static_len = 0;
this.last_lit = this.matches = 0;
});
$_M(c$, "pqdownheap", 
function (tree, k) {
var v = this.heap[k];
var j = k << 1;
while (j <= this.heap_len) {
if (j < this.heap_len && JZ.Deflate.smaller (tree, this.heap[j + 1], this.heap[j], this.depth)) {
j++;
}if (JZ.Deflate.smaller (tree, v, this.heap[j], this.depth)) break;
this.heap[k] = this.heap[j];
k = j;
j <<= 1;
}
this.heap[k] = v;
}, "~A,~N");
c$.smaller = $_M(c$, "smaller", 
function (tree, n, m, depth) {
var tn2 = tree[n * 2];
var tm2 = tree[m * 2];
return (tn2 < tm2 || (tn2 == tm2 && depth[n] <= depth[m]));
}, "~A,~N,~N,~A");
$_M(c$, "scan_tree", 
function (tree, max_code) {
var n;
var prevlen = -1;
var curlen;
var nextlen = tree[1];
var count = 0;
var max_count = 7;
var min_count = 4;
if (nextlen == 0) {
max_count = 138;
min_count = 3;
}tree[(max_code + 1) * 2 + 1] = 0xffff;
for (n = 0; n <= max_code; n++) {
curlen = nextlen;
nextlen = tree[(n + 1) * 2 + 1];
if (++count < max_count && curlen == nextlen) {
continue;
} else if (count < min_count) {
this.bl_tree[curlen * 2] += count;
} else if (curlen != 0) {
if (curlen != prevlen) this.bl_tree[curlen * 2]++;
this.bl_tree[32]++;
} else if (count <= 10) {
this.bl_tree[34]++;
} else {
this.bl_tree[36]++;
}count = 0;
prevlen = curlen;
if (nextlen == 0) {
max_count = 138;
min_count = 3;
} else if (curlen == nextlen) {
max_count = 6;
min_count = 3;
} else {
max_count = 7;
min_count = 4;
}}
}, "~A,~N");
$_M(c$, "build_bl_tree", 
function () {
var max_blindex;
this.scan_tree (this.dyn_ltree, this.l_desc.max_code);
this.scan_tree (this.dyn_dtree, this.d_desc.max_code);
this.bl_desc.build_tree (this);
for (max_blindex = 18; max_blindex >= 3; max_blindex--) {
if (this.bl_tree[JZ.Tree.bl_order[max_blindex] * 2 + 1] != 0) break;
}
this.opt_len += 3 * (max_blindex + 1) + 5 + 5 + 4;
return max_blindex;
});
$_M(c$, "send_all_trees", 
function (lcodes, dcodes, blcodes) {
var rank;
this.send_bits (lcodes - 257, 5);
this.send_bits (dcodes - 1, 5);
this.send_bits (blcodes - 4, 4);
for (rank = 0; rank < blcodes; rank++) {
this.send_bits (this.bl_tree[JZ.Tree.bl_order[rank] * 2 + 1], 3);
}
this.send_tree (this.dyn_ltree, lcodes - 1);
this.send_tree (this.dyn_dtree, dcodes - 1);
}, "~N,~N,~N");
$_M(c$, "send_tree", 
function (tree, max_code) {
var n;
var prevlen = -1;
var curlen;
var nextlen = tree[1];
var count = 0;
var max_count = 7;
var min_count = 4;
if (nextlen == 0) {
max_count = 138;
min_count = 3;
}for (n = 0; n <= max_code; n++) {
curlen = nextlen;
nextlen = tree[(n + 1) * 2 + 1];
if (++count < max_count && curlen == nextlen) {
continue;
} else if (count < min_count) {
do {
this.send_code (curlen, this.bl_tree);
} while (--count != 0);
} else if (curlen != 0) {
if (curlen != prevlen) {
this.send_code (curlen, this.bl_tree);
count--;
}this.send_code (16, this.bl_tree);
this.send_bits (count - 3, 2);
} else if (count <= 10) {
this.send_code (17, this.bl_tree);
this.send_bits (count - 3, 3);
} else {
this.send_code (18, this.bl_tree);
this.send_bits (count - 11, 7);
}count = 0;
prevlen = curlen;
if (nextlen == 0) {
max_count = 138;
min_count = 3;
} else if (curlen == nextlen) {
max_count = 6;
min_count = 3;
} else {
max_count = 7;
min_count = 4;
}}
}, "~A,~N");
$_M(c$, "put_byte", 
function (p, start, len) {
System.arraycopy (p, start, this.pending_buf, this.pending, len);
this.pending += len;
}, "~A,~N,~N");
$_M(c$, "put_byteB", 
function (c) {
{
this.pending_buf[this.pending++] = c&0xff;
}}, "~N");
$_M(c$, "put_short", 
function (w) {
this.put_byteB ((w));
this.put_byteB ((w >>> 8));
}, "~N");
$_M(c$, "putShortMSB", 
function (b) {
this.put_byteB ((b >> 8));
this.put_byteB ((b));
}, "~N");
$_M(c$, "send_code", 
function (c, tree) {
var c2 = c * 2;
this.send_bits ((tree[c2] & 0xffff), (tree[c2 + 1] & 0xffff));
}, "~N,~A");
$_M(c$, "send_bits", 
function (value, length) {
var len = length;
if (this.bi_valid > 16 - len) {
var val = value;
this.bi_buf |= ((val << this.bi_valid) & 0xffff);
this.put_short (this.bi_buf);
this.bi_buf = ((val >>> (16 - this.bi_valid)) & 0xffff);
this.bi_valid += len - 16;
} else {
this.bi_buf |= (((value) << this.bi_valid) & 0xffff);
this.bi_valid += len;
}}, "~N,~N");
$_M(c$, "_tr_align", 
function () {
this.send_bits (2, 3);
this.send_code (256, JZ.StaticTree.static_ltree);
this.bi_flush ();
if (1 + this.last_eob_len + 10 - this.bi_valid < 9) {
this.send_bits (2, 3);
this.send_code (256, JZ.StaticTree.static_ltree);
this.bi_flush ();
}this.last_eob_len = 7;
});
$_M(c$, "_tr_tally", 
function (dist, lc) {
this.pending_buf[this.d_buf + this.last_lit * 2] = (dist >>> 8);
this.pending_buf[this.d_buf + this.last_lit * 2 + 1] = dist;
this.pending_buf[this.l_buf + this.last_lit] = lc;
this.last_lit++;
if (dist == 0) {
this.dyn_ltree[lc * 2]++;
} else {
this.matches++;
dist--;
this.dyn_ltree[(JZ.Tree._length_code[lc] + 256 + 1) * 2]++;
this.dyn_dtree[JZ.Tree.d_code (dist) * 2]++;
}if ((this.last_lit & 0x1fff) == 0 && this.level > 2) {
var out_length = this.last_lit * 8;
var in_length = this.strstart - this.block_start;
var dcode;
for (dcode = 0; dcode < 30; dcode++) {
out_length += this.dyn_dtree[dcode * 2] * (5 + JZ.Tree.extra_dbits[dcode]);
}
out_length >>>= 3;
if ((this.matches < (Clazz_doubleToInt (this.last_lit / 2))) && out_length < Clazz_doubleToInt (in_length / 2)) return true;
}return (this.last_lit == this.lit_bufsize - 1);
}, "~N,~N");
$_M(c$, "compress_block", 
function (ltree, dtree) {
var dist;
var lc;
var lx = 0;
var code;
var extra;
if (this.last_lit != 0) {
do {
dist = ((this.pending_buf[this.d_buf + lx * 2] << 8) & 0xff00) | (this.pending_buf[this.d_buf + lx * 2 + 1] & 0xff);
lc = (this.pending_buf[this.l_buf + lx]) & 0xff;
lx++;
if (dist == 0) {
this.send_code (lc, ltree);
} else {
code = JZ.Tree._length_code[lc];
this.send_code (code + 256 + 1, ltree);
extra = JZ.Tree.extra_lbits[code];
if (extra != 0) {
lc -= JZ.Tree.base_length[code];
this.send_bits (lc, extra);
}dist--;
code = JZ.Tree.d_code (dist);
this.send_code (code, dtree);
extra = JZ.Tree.extra_dbits[code];
if (extra != 0) {
dist -= JZ.Tree.base_dist[code];
this.send_bits (dist, extra);
}}} while (lx < this.last_lit);
}this.send_code (256, ltree);
this.last_eob_len = ltree[513];
}, "~A,~A");
$_M(c$, "set_data_type", 
function () {
var n = 0;
var ascii_freq = 0;
var bin_freq = 0;
while (n < 7) {
bin_freq += this.dyn_ltree[n * 2];
n++;
}
while (n < 128) {
ascii_freq += this.dyn_ltree[n * 2];
n++;
}
while (n < 256) {
bin_freq += this.dyn_ltree[n * 2];
n++;
}
this.data_type = (bin_freq > (ascii_freq >>> 2) ? 0 : 1);
});
$_M(c$, "bi_flush", 
function () {
if (this.bi_valid == 16) {
this.put_short (this.bi_buf);
this.bi_buf = 0;
this.bi_valid = 0;
} else if (this.bi_valid >= 8) {
this.put_byteB (this.bi_buf);
this.bi_buf >>>= 8;
this.bi_valid -= 8;
}});
$_M(c$, "bi_windup", 
function () {
if (this.bi_valid > 8) {
this.put_short (this.bi_buf);
} else if (this.bi_valid > 0) {
this.put_byteB (this.bi_buf);
}this.bi_buf = 0;
this.bi_valid = 0;
});
$_M(c$, "copy_block", 
function (buf, len, header) {
this.bi_windup ();
this.last_eob_len = 8;
if (header) {
this.put_short (len);
this.put_short (~len);
}this.put_byte (this.window, buf, len);
}, "~N,~N,~B");
$_M(c$, "flush_block_only", 
function (eof) {
this._tr_flush_block (this.block_start >= 0 ? this.block_start : -1, this.strstart - this.block_start, eof);
this.block_start = this.strstart;
this.strm.flush_pending ();
}, "~B");
$_M(c$, "deflate_stored", 
function (flush) {
var max_block_size = 0xffff;
var max_start;
if (max_block_size > this.pending_buf_size - 5) {
max_block_size = this.pending_buf_size - 5;
}while (true) {
if (this.lookahead <= 1) {
this.fill_window ();
if (this.lookahead == 0 && flush == 0) return 0;
if (this.lookahead == 0) break;
}this.strstart += this.lookahead;
this.lookahead = 0;
max_start = this.block_start + max_block_size;
if (this.strstart == 0 || this.strstart >= max_start) {
this.lookahead = (this.strstart - max_start);
this.strstart = max_start;
this.flush_block_only (false);
if (this.strm.avail_out == 0) return 0;
}if (this.strstart - this.block_start >= this.w_size - 262) {
this.flush_block_only (false);
if (this.strm.avail_out == 0) return 0;
}}
this.flush_block_only (flush == 4);
if (this.strm.avail_out == 0) return (flush == 4) ? 2 : 0;
return flush == 4 ? 3 : 1;
}, "~N");
$_M(c$, "_tr_stored_block", 
function (buf, stored_len, eof) {
this.send_bits ((0) + (eof ? 1 : 0), 3);
this.copy_block (buf, stored_len, true);
}, "~N,~N,~B");
$_M(c$, "_tr_flush_block", 
function (buf, stored_len, eof) {
var opt_lenb;
var static_lenb;
var max_blindex = 0;
if (this.level > 0) {
if (this.data_type == 2) this.set_data_type ();
this.l_desc.build_tree (this);
this.d_desc.build_tree (this);
max_blindex = this.build_bl_tree ();
opt_lenb = (this.opt_len + 3 + 7) >>> 3;
static_lenb = (this.static_len + 3 + 7) >>> 3;
if (static_lenb <= opt_lenb) opt_lenb = static_lenb;
} else {
opt_lenb = static_lenb = stored_len + 5;
}if (stored_len + 4 <= opt_lenb && buf != -1) {
this._tr_stored_block (buf, stored_len, eof);
} else if (static_lenb == opt_lenb) {
this.send_bits ((2) + (eof ? 1 : 0), 3);
this.compress_block (JZ.StaticTree.static_ltree, JZ.StaticTree.static_dtree);
} else {
this.send_bits ((4) + (eof ? 1 : 0), 3);
this.send_all_trees (this.l_desc.max_code + 1, this.d_desc.max_code + 1, max_blindex + 1);
this.compress_block (this.dyn_ltree, this.dyn_dtree);
}this.init_block ();
if (eof) {
this.bi_windup ();
}}, "~N,~N,~B");
$_M(c$, "fill_window", 
function () {
var n;
var m;
var p;
var more;
do {
more = (this.window_size - this.lookahead - this.strstart);
if (more == 0 && this.strstart == 0 && this.lookahead == 0) {
more = this.w_size;
} else if (more == -1) {
more--;
} else if (this.strstart >= this.w_size + this.w_size - 262) {
System.arraycopy (this.window, this.w_size, this.window, 0, this.w_size);
this.match_start -= this.w_size;
this.strstart -= this.w_size;
this.block_start -= this.w_size;
n = this.hash_size;
p = n;
do {
m = (this.head[--p] & 0xffff);
this.head[p] = (m >= this.w_size ? (m - this.w_size) : 0);
} while (--n != 0);
n = this.w_size;
p = n;
do {
m = (this.prev[--p] & 0xffff);
this.prev[p] = (m >= this.w_size ? (m - this.w_size) : 0);
} while (--n != 0);
more += this.w_size;
}if (this.strm.avail_in == 0) return;
n = this.strm.read_buf (this.window, this.strstart + this.lookahead, more);
this.lookahead += n;
if (this.lookahead >= 3) {
this.ins_h = this.window[this.strstart] & 0xff;
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[this.strstart + 1] & 0xff)) & this.hash_mask;
}} while (this.lookahead < 262 && this.strm.avail_in != 0);
});
$_M(c$, "deflate_fast", 
function (flush) {
var hash_head = 0;
var bflush;
while (true) {
if (this.lookahead < 262) {
this.fill_window ();
if (this.lookahead < 262 && flush == 0) {
return 0;
}if (this.lookahead == 0) break;
}if (this.lookahead >= 3) {
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[(this.strstart) + (2)] & 0xff)) & this.hash_mask;
hash_head = (this.head[this.ins_h] & 0xffff);
this.prev[this.strstart & this.w_mask] = this.head[this.ins_h];
this.head[this.ins_h] = this.strstart;
}if (hash_head != 0 && ((this.strstart - hash_head) & 0xffff) <= this.w_size - 262) {
if (this.strategy != 2) {
this.match_length = this.longest_match (hash_head);
}}if (this.match_length >= 3) {
bflush = this._tr_tally (this.strstart - this.match_start, this.match_length - 3);
this.lookahead -= this.match_length;
if (this.match_length <= this.max_lazy_match && this.lookahead >= 3) {
this.match_length--;
do {
this.strstart++;
this.ins_h = ((this.ins_h << this.hash_shift) ^ (this.window[(this.strstart) + (2)] & 0xff)) & this.hash_mask;
hash_head = (this.head[this.ins_h] & 0xffff);
this.prev[this.strstart & this.w_mask] = this.head[this.ins_h];
this.head[this.ins_h] = this.strstart;
} while (--this.match_length != 0);
this.strstart++;
} else {
this.strstart += this.match_length;
this.match_length = 0;
this.ins_h = this.window[this.strstart] & 0xff;
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[this.strstart + 1] & 0xff)) & this.hash_mask;
}} else {
bflush = this._tr_tally (0, this.window[this.strstart] & 0xff);
this.lookahead--;
this.strstart++;
}if (bflush) {
this.flush_block_only (false);
if (this.strm.avail_out == 0) return 0;
}}
this.flush_block_only (flush == 4);
if (this.strm.avail_out == 0) {
if (flush == 4) return 2;
return 0;
}return flush == 4 ? 3 : 1;
}, "~N");
$_M(c$, "deflate_slow", 
function (flush) {
var hash_head = 0;
var bflush;
while (true) {
if (this.lookahead < 262) {
this.fill_window ();
if (this.lookahead < 262 && flush == 0) {
return 0;
}if (this.lookahead == 0) break;
}if (this.lookahead >= 3) {
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[(this.strstart) + (2)] & 0xff)) & this.hash_mask;
hash_head = (this.head[this.ins_h] & 0xffff);
this.prev[this.strstart & this.w_mask] = this.head[this.ins_h];
this.head[this.ins_h] = this.strstart;
}this.prev_length = this.match_length;
this.prev_match = this.match_start;
this.match_length = 2;
if (hash_head != 0 && this.prev_length < this.max_lazy_match && ((this.strstart - hash_head) & 0xffff) <= this.w_size - 262) {
if (this.strategy != 2) {
this.match_length = this.longest_match (hash_head);
}if (this.match_length <= 5 && (this.strategy == 1 || (this.match_length == 3 && this.strstart - this.match_start > 4096))) {
this.match_length = 2;
}}if (this.prev_length >= 3 && this.match_length <= this.prev_length) {
var max_insert = this.strstart + this.lookahead - 3;
bflush = this._tr_tally (this.strstart - 1 - this.prev_match, this.prev_length - 3);
this.lookahead -= this.prev_length - 1;
this.prev_length -= 2;
do {
if (++this.strstart <= max_insert) {
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[(this.strstart) + (2)] & 0xff)) & this.hash_mask;
hash_head = (this.head[this.ins_h] & 0xffff);
this.prev[this.strstart & this.w_mask] = this.head[this.ins_h];
this.head[this.ins_h] = this.strstart;
}} while (--this.prev_length != 0);
this.match_available = 0;
this.match_length = 2;
this.strstart++;
if (bflush) {
this.flush_block_only (false);
if (this.strm.avail_out == 0) return 0;
}} else if (this.match_available != 0) {
bflush = this._tr_tally (0, this.window[this.strstart - 1] & 0xff);
if (bflush) {
this.flush_block_only (false);
}this.strstart++;
this.lookahead--;
if (this.strm.avail_out == 0) return 0;
} else {
this.match_available = 1;
this.strstart++;
this.lookahead--;
}}
if (this.match_available != 0) {
bflush = this._tr_tally (0, this.window[this.strstart - 1] & 0xff);
this.match_available = 0;
}this.flush_block_only (flush == 4);
if (this.strm.avail_out == 0) {
if (flush == 4) return 2;
return 0;
}return flush == 4 ? 3 : 1;
}, "~N");
$_M(c$, "longest_match", 
function (cur_match) {
var chain_length = this.max_chain_length;
var scan = this.strstart;
var match;
var len;
var best_len = this.prev_length;
var limit = this.strstart > (this.w_size - 262) ? this.strstart - (this.w_size - 262) : 0;
var nice_match = this.nice_match;
var wmask = this.w_mask;
var strend = this.strstart + 258;
var scan_end1 = this.window[scan + best_len - 1];
var scan_end = this.window[scan + best_len];
if (this.prev_length >= this.good_match) {
chain_length >>= 2;
}if (nice_match > this.lookahead) nice_match = this.lookahead;
do {
match = cur_match;
if (this.window[match + best_len] != scan_end || this.window[match + best_len - 1] != scan_end1 || this.window[match] != this.window[scan] || this.window[++match] != this.window[scan + 1]) continue;
scan += 2;
match++;
do {
} while (this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && this.window[++scan] == this.window[++match] && scan < strend);
len = 258 - (strend - scan);
scan = strend - 258;
if (len > best_len) {
this.match_start = cur_match;
best_len = len;
if (len >= nice_match) break;
scan_end1 = this.window[scan + best_len - 1];
scan_end = this.window[scan + best_len];
}} while ((cur_match = (this.prev[cur_match & wmask] & 0xffff)) > limit && --chain_length != 0);
if (best_len <= this.lookahead) return best_len;
return this.lookahead;
}, "~N");
$_M(c$, "deflateInit5", 
function (level, method, windowBits, memLevel, strategy) {
var wrap = 1;
this.strm.msg = null;
if (level == -1) level = 6;
if (windowBits < 0) {
wrap = 0;
windowBits = -windowBits;
} else if (windowBits > 15) {
wrap = 2;
windowBits -= 16;
this.strm.checksum =  new JZ.CRC32 ();
}if (memLevel < 1 || memLevel > 9 || method != 8 || windowBits < 9 || windowBits > 15 || level < 0 || level > 9 || strategy < 0 || strategy > 2) {
return -2;
}this.strm.dstate = this;
this.wrap = wrap;
this.w_bits = windowBits;
this.w_size = 1 << this.w_bits;
this.w_mask = this.w_size - 1;
this.hash_bits = memLevel + 7;
this.hash_size = 1 << this.hash_bits;
this.hash_mask = this.hash_size - 1;
this.hash_shift = (Clazz_doubleToInt ((this.hash_bits + 3 - 1) / 3));
this.window =  Clazz_newByteArray (this.w_size * 2, 0);
this.prev =  Clazz_newShortArray (this.w_size, 0);
this.head =  Clazz_newShortArray (this.hash_size, 0);
this.lit_bufsize = 1 << (memLevel + 6);
this.pending_buf =  Clazz_newByteArray (this.lit_bufsize * 4, 0);
this.pending_buf_size = this.lit_bufsize * 4;
this.d_buf = Clazz_doubleToInt (this.lit_bufsize / 2);
this.l_buf = (3) * this.lit_bufsize;
this.level = level;
this.strategy = strategy;
this.method = method;
return this.deflateReset ();
}, "~N,~N,~N,~N,~N");
$_M(c$, "deflateReset", 
function () {
this.strm.total_in = this.strm.total_out = 0;
this.strm.msg = null;
this.strm.data_type = 2;
this.pending = 0;
this.pending_out = 0;
if (this.wrap < 0) {
this.wrap = -this.wrap;
}this.status = (this.wrap == 0) ? 113 : 42;
this.strm.checksum.reset ();
this.last_flush = 0;
this.tr_init ();
this.lm_init ();
return 0;
});
$_M(c$, "deflateEnd", 
function () {
if (this.status != 42 && this.status != 113 && this.status != 666) {
return -2;
}this.pending_buf = null;
this.head = null;
this.prev = null;
this.window = null;
return this.status == 113 ? -3 : 0;
});
$_M(c$, "deflateParams", 
function (_level, _strategy) {
var err = 0;
if (_level == -1) {
_level = 6;
}if (_level < 0 || _level > 9 || _strategy < 0 || _strategy > 2) {
return -2;
}if (JZ.Deflate.config_table[this.level].func != JZ.Deflate.config_table[_level].func && this.strm.total_in != 0) {
err = this.strm.deflate (1);
}if (this.level != _level) {
this.level = _level;
this.max_lazy_match = JZ.Deflate.config_table[this.level].max_lazy;
this.good_match = JZ.Deflate.config_table[this.level].good_length;
this.nice_match = JZ.Deflate.config_table[this.level].nice_length;
this.max_chain_length = JZ.Deflate.config_table[this.level].max_chain;
}this.strategy = _strategy;
return err;
}, "~N,~N");
$_M(c$, "deflateSetDictionary", 
function (dictionary, dictLength) {
var length = dictLength;
var index = 0;
if (dictionary == null || this.status != 42) return -2;
this.strm.checksum.update (dictionary, 0, dictLength);
if (length < 3) return 0;
if (length > this.w_size - 262) {
length = this.w_size - 262;
index = dictLength - length;
}System.arraycopy (dictionary, index, this.window, 0, length);
this.strstart = length;
this.block_start = length;
this.ins_h = this.window[0] & 0xff;
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[1] & 0xff)) & this.hash_mask;
for (var n = 0; n <= length - 3; n++) {
this.ins_h = (((this.ins_h) << this.hash_shift) ^ (this.window[(n) + (2)] & 0xff)) & this.hash_mask;
this.prev[n & this.w_mask] = this.head[this.ins_h];
this.head[this.ins_h] = n;
}
return 0;
}, "~A,~N");
$_M(c$, "deflate", 
function (flush) {
var old_flush;
if (flush > 4 || flush < 0) {
return -2;
}if (this.strm.next_out == null || (this.strm.next_in == null && this.strm.avail_in != 0) || (this.status == 666 && flush != 4)) {
this.strm.msg = JZ.Deflate.z_errmsg[4];
return -2;
}if (this.strm.avail_out == 0) {
this.strm.msg = JZ.Deflate.z_errmsg[7];
return -5;
}old_flush = this.last_flush;
this.last_flush = flush;
if (this.status == 42) {
if (this.wrap == 2) {
this.getGZIPHeader ().put (this);
this.status = 113;
this.strm.checksum.reset ();
} else {
var header = (8 + ((this.w_bits - 8) << 4)) << 8;
var level_flags = ((this.level - 1) & 0xff) >> 1;
if (level_flags > 3) level_flags = 3;
header |= (level_flags << 6);
if (this.strstart != 0) header |= 32;
header += 31 - (header % 31);
this.status = 113;
this.putShortMSB (header);
if (this.strstart != 0) {
var adler = this.strm.checksum.getValue ();
this.putShortMSB ((adler >>> 16));
this.putShortMSB ((adler & 0xffff));
}this.strm.checksum.reset ();
}}if (this.pending != 0) {
this.strm.flush_pending ();
if (this.strm.avail_out == 0) {
this.last_flush = -1;
return 0;
}} else if (this.strm.avail_in == 0 && flush <= old_flush && flush != 4) {
this.strm.msg = JZ.Deflate.z_errmsg[7];
return -5;
}if (this.status == 666 && this.strm.avail_in != 0) {
this.strm.msg = JZ.Deflate.z_errmsg[7];
return -5;
}if (this.strm.avail_in != 0 || this.lookahead != 0 || (flush != 0 && this.status != 666)) {
var bstate = -1;
switch (JZ.Deflate.config_table[this.level].func) {
case 0:
bstate = this.deflate_stored (flush);
break;
case 1:
bstate = this.deflate_fast (flush);
break;
case 2:
bstate = this.deflate_slow (flush);
break;
default:
}
if (bstate == 2 || bstate == 3) {
this.status = 666;
}if (bstate == 0 || bstate == 2) {
if (this.strm.avail_out == 0) {
this.last_flush = -1;
}return 0;
}if (bstate == 1) {
if (flush == 1) {
this._tr_align ();
} else {
this._tr_stored_block (0, 0, false);
if (flush == 3) {
for (var i = 0; i < this.hash_size; i++) this.head[i] = 0;

}}this.strm.flush_pending ();
if (this.strm.avail_out == 0) {
this.last_flush = -1;
return 0;
}}}if (flush != 4) return 0;
if (this.wrap <= 0) return 1;
if (this.wrap == 2) {
var adler = this.strm.checksum.getValue ();
this.put_byteB ((adler & 0xff));
this.put_byteB (((adler >> 8) & 0xff));
this.put_byteB (((adler >> 16) & 0xff));
this.put_byteB (((adler >> 24) & 0xff));
this.put_byteB ((this.strm.total_in & 0xff));
this.put_byteB (((this.strm.total_in >> 8) & 0xff));
this.put_byteB (((this.strm.total_in >> 16) & 0xff));
this.put_byteB (((this.strm.total_in >> 24) & 0xff));
this.getGZIPHeader ().setCRC (adler);
} else {
var adler = this.strm.checksum.getValue ();
this.putShortMSB ((adler >>> 16));
this.putShortMSB ((adler & 0xffff));
}this.strm.flush_pending ();
if (this.wrap > 0) this.wrap = -this.wrap;
return this.pending != 0 ? 0 : 1;
}, "~N");
$_M(c$, "getGZIPHeader", 
function () {
if (this.gheader == null) {
this.gheader =  new JZ.GZIPHeader ();
}return this.gheader;
});
$_M(c$, "getBytesRead", 
function () {
return this.strm.total_in;
});
$_M(c$, "getBytesWritten", 
function () {
return this.strm.total_out;
});
Clazz_pu$h ();
c$ = Clazz_decorateAsClass (function () {
this.good_length = 0;
this.max_lazy = 0;
this.nice_length = 0;
this.max_chain = 0;
this.func = 0;
Clazz_instantialize (this, arguments);
}, JZ.Deflate, "Config");
Clazz_makeConstructor (c$, 
function (a, b, c, d, e) {
this.good_length = a;
this.max_lazy = b;
this.nice_length = c;
this.max_chain = d;
this.func = e;
}, "~N,~N,~N,~N,~N");
c$ = Clazz_p0p ();
Clazz_defineStatics (c$,
"MAX_MEM_LEVEL", 9,
"Z_DEFAULT_COMPRESSION", -1,
"MAX_WBITS", 15,
"DEF_MEM_LEVEL", 8,
"STORED", 0,
"FAST", 1,
"SLOW", 2,
"config_table", null);
{
JZ.Deflate.config_table =  new Array (10);
JZ.Deflate.config_table[0] =  new JZ.Deflate.Config (0, 0, 0, 0, 0);
JZ.Deflate.config_table[1] =  new JZ.Deflate.Config (4, 4, 8, 4, 1);
JZ.Deflate.config_table[2] =  new JZ.Deflate.Config (4, 5, 16, 8, 1);
JZ.Deflate.config_table[3] =  new JZ.Deflate.Config (4, 6, 32, 32, 1);
JZ.Deflate.config_table[4] =  new JZ.Deflate.Config (4, 4, 16, 16, 2);
JZ.Deflate.config_table[5] =  new JZ.Deflate.Config (8, 16, 32, 32, 2);
JZ.Deflate.config_table[6] =  new JZ.Deflate.Config (8, 16, 128, 128, 2);
JZ.Deflate.config_table[7] =  new JZ.Deflate.Config (8, 32, 128, 256, 2);
JZ.Deflate.config_table[8] =  new JZ.Deflate.Config (32, 128, 258, 1024, 2);
JZ.Deflate.config_table[9] =  new JZ.Deflate.Config (32, 258, 258, 4096, 2);
}Clazz_defineStatics (c$,
"z_errmsg", ["need dictionary", "stream end", "", "file error", "stream error", "data error", "insufficient memory", "buffer error", "incompatible version", ""],
"NeedMore", 0,
"BlockDone", 1,
"FinishStarted", 2,
"FinishDone", 3,
"PRESET_DICT", 0x20,
"Z_FILTERED", 1,
"Z_HUFFMAN_ONLY", 2,
"Z_DEFAULT_STRATEGY", 0,
"Z_NO_FLUSH", 0,
"Z_PARTIAL_FLUSH", 1,
"Z_FULL_FLUSH", 3,
"Z_FINISH", 4,
"Z_OK", 0,
"Z_STREAM_END", 1,
"Z_NEED_DICT", 2,
"Z_STREAM_ERROR", -2,
"Z_DATA_ERROR", -3,
"Z_BUF_ERROR", -5,
"INIT_STATE", 42,
"BUSY_STATE", 113,
"FINISH_STATE", 666,
"Z_DEFLATED", 8,
"STORED_BLOCK", 0,
"STATIC_TREES", 1,
"DYN_TREES", 2,
"Z_BINARY", 0,
"Z_ASCII", 1,
"Z_UNKNOWN", 2,
"Buf_size", 16,
"REP_3_6", 16,
"REPZ_3_10", 17,
"REPZ_11_138", 18,
"MIN_MATCH", 3,
"MAX_MATCH", 258,
"MIN_LOOKAHEAD", (262),
"MAX_BITS", 15,
"D_CODES", 30,
"BL_CODES", 19,
"LENGTH_CODES", 29,
"LITERALS", 256,
"L_CODES", (286),
"HEAP_SIZE", (573),
"END_BLOCK", 256);
});
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["javajs.api.GenericPlatform"], "JSV.js2d.JsPlatform", ["java.net.URL", "JU.AjaxURLStreamHandlerFactory", "JSV.js2d.Display", "$.Image", "$.JsFile", "$.JsFont", "$.Mouse"], function () {
c$ = Clazz_decorateAsClass (function () {
this.canvas = null;
this.viewer = null;
this.context = null;
Clazz_instantialize (this, arguments);
}, JSV.js2d, "JsPlatform", null, javajs.api.GenericPlatform);
$_V(c$, "setViewer", 
function (viewer, canvas) {
{
this.viewer = viewer;
this.canvas = canvas;
if (canvas != null) {
this.context = canvas.getContext("2d");
canvas.imgdata = this.context.getImageData(0, 0, canvas.width, canvas.height);
canvas.buf8 = canvas.imgdata.data;
}
}try {
java.net.URL.setURLStreamHandlerFactory ( new JU.AjaxURLStreamHandlerFactory ());
} catch (e) {
}
}, "javajs.api.PlatformViewer,~O");
$_V(c$, "isSingleThreaded", 
function () {
return true;
});
$_V(c$, "getJsObjectInfo", 
function (jsObject, method, args) {
{
if (method == "localName")return jsObject[0]["nodeName"];
return (args == null ? jsObject[0][method] : jsObject[0][method](args[0]));
}}, "~A,~S,~A");
$_V(c$, "isHeadless", 
function () {
return false;
});
$_M(c$, "getMouseManager", 
function (privateKey, jsvp) {
return  new JSV.js2d.Mouse (jsvp);
}, "~N,~O");
$_V(c$, "convertPointFromScreen", 
function (canvas, ptTemp) {
JSV.js2d.Display.convertPointFromScreen (canvas, ptTemp);
}, "~O,JU.P3");
$_V(c$, "getFullScreenDimensions", 
function (canvas, widthHeight) {
JSV.js2d.Display.getFullScreenDimensions (canvas, widthHeight);
}, "~O,~A");
$_V(c$, "getMenuPopup", 
function (menuStructure, type) {
return null;
}, "~S,~S");
$_V(c$, "hasFocus", 
function (canvas) {
return JSV.js2d.Display.hasFocus (canvas);
}, "~O");
$_V(c$, "prompt", 
function (label, data, list, asButtons) {
return JSV.js2d.Display.prompt (label, data, list, asButtons);
}, "~S,~S,~A,~B");
$_V(c$, "renderScreenImage", 
function (context, size) {
JSV.js2d.Display.renderScreenImage (this.viewer, context, size);
}, "~O,~O");
$_V(c$, "drawImage", 
function (context, canvas, x, y, width, height) {
JSV.js2d.Image.drawImage (context, canvas, x, y, width, height);
}, "~O,~O,~N,~N,~N,~N");
$_V(c$, "requestFocusInWindow", 
function (canvas) {
JSV.js2d.Display.requestFocusInWindow (canvas);
}, "~O");
$_V(c$, "repaint", 
function (canvas) {
JSV.js2d.Display.repaint (canvas);
}, "~O");
$_V(c$, "setTransparentCursor", 
function (canvas) {
JSV.js2d.Display.setTransparentCursor (canvas);
}, "~O");
$_V(c$, "setCursor", 
function (c, canvas) {
JSV.js2d.Display.setCursor (c, canvas);
}, "~N,~O");
$_V(c$, "allocateRgbImage", 
function (windowWidth, windowHeight, pBuffer, windowSize, backgroundTransparent, isImageWrite) {
return JSV.js2d.Image.allocateRgbImage (windowWidth, windowHeight, pBuffer, windowSize, backgroundTransparent, (isImageWrite ? null : this.canvas));
}, "~N,~N,~A,~N,~B,~B");
$_V(c$, "notifyEndOfRendering", 
function () {
});
$_V(c$, "createImage", 
function (data) {
return null;
}, "~O");
$_V(c$, "disposeGraphics", 
function (gOffscreen) {
}, "~O");
$_V(c$, "grabPixels", 
function (canvas, width, height, pixels, startRow, nRows) {
{
if (canvas.image && (width != canvas.width || height != canvas.height))
Jmol._setCanvasImage(canvas, width, height);
if (canvas.buf32) return canvas.buf32;
}var buf = JSV.js2d.Image.grabPixels (JSV.js2d.Image.getGraphics (canvas), width, height);
{
canvas.buf32 = buf;
}return buf;
}, "~O,~N,~N,~A,~N,~N");
$_V(c$, "drawImageToBuffer", 
function (gOffscreen, imageOffscreen, canvas, width, height, bgcolor) {
return this.grabPixels (canvas, width, height, null, 0, 0);
}, "~O,~O,~O,~N,~N,~N");
$_V(c$, "getTextPixels", 
function (text, font3d, context, image, width, height, ascent) {
return JSV.js2d.Image.getTextPixels (text, font3d, context, width, height, ascent);
}, "~S,javajs.awt.Font,~O,~O,~N,~N,~N");
$_V(c$, "flushImage", 
function (imagePixelBuffer) {
}, "~O");
$_V(c$, "getGraphics", 
function (image) {
return JSV.js2d.Image.getGraphics (image);
}, "~O");
$_V(c$, "getImageHeight", 
function (canvas) {
return (canvas == null ? -1 : JSV.js2d.Image.getHeight (canvas));
}, "~O");
$_V(c$, "getImageWidth", 
function (canvas) {
return (canvas == null ? -1 : JSV.js2d.Image.getWidth (canvas));
}, "~O");
$_V(c$, "getStaticGraphics", 
function (image, backgroundTransparent) {
return JSV.js2d.Image.getStaticGraphics (image, backgroundTransparent);
}, "~O,~B");
$_V(c$, "newBufferedImage", 
function (image, w, h) {
{
if (typeof Jmol != "undefined" && Jmol._getHiddenCanvas)
return Jmol._getHiddenCanvas(this.viewer.applet, "stereoImage", w, h);
}return null;
}, "~O,~N,~N");
$_V(c$, "newOffScreenImage", 
function (w, h) {
{
if (typeof Jmol != "undefined" && Jmol._getHiddenCanvas)
return Jmol._getHiddenCanvas(this.viewer.applet, "textImage", w, h);
}return null;
}, "~N,~N");
$_V(c$, "waitForDisplay", 
function (echoNameAndPath, zipBytes) {
{
if (typeof Jmol == "undefined" || !Jmol._getHiddenCanvas) return false;
var viewer = this.viewer;
var sc = viewer.getEvalContextAndHoldQueue(viewer.eval);
var echoName = echoNameAndPath[0];
return Jmol._loadImage(this, echoNameAndPath, zipBytes,
function(canvas, pathOrError) { viewer.loadImageData(canvas, pathOrError, echoName, sc) }
);
}}, "~O,~O");
$_V(c$, "fontStringWidth", 
function (font, text) {
return JSV.js2d.JsFont.stringWidth (font, text);
}, "javajs.awt.Font,~S");
$_V(c$, "getFontAscent", 
function (context) {
return JSV.js2d.JsFont.getAscent (context);
}, "~O");
$_V(c$, "getFontDescent", 
function (context) {
return JSV.js2d.JsFont.getDescent (context);
}, "~O");
$_V(c$, "getFontMetrics", 
function (font, context) {
return JSV.js2d.JsFont.getFontMetrics (font, context);
}, "javajs.awt.Font,~O");
$_V(c$, "newFont", 
function (fontFace, isBold, isItalic, fontSize) {
return JSV.js2d.JsFont.newFont (fontFace, isBold, isItalic, fontSize, "px");
}, "~S,~B,~B,~N");
$_V(c$, "getDateFormat", 
function (isoiec8824) {
{
if (isoiec8824) {
var d = new Date();
var x = d.toString().split(" ");
var MM = "0" + d.getMonth(); MM = MM.substring(MM.length - 2);
var dd = "0" + d.getDate(); dd = dd.substring(dd.length - 2);
return x[3] + MM + dd + x[4].replace(/\:/g,"") + x[5].substring(3,6) + "'" + x[5].substring(6,8) + "'"
}
return ("" + (new Date())).split(" (")[0];
}}, "~B");
$_V(c$, "newFile", 
function (name) {
return  new JSV.js2d.JsFile (name);
}, "~S");
$_V(c$, "getBufferedFileInputStream", 
function (name) {
return null;
}, "~S");
$_V(c$, "getBufferedURLInputStream", 
function (url, outputBytes, post) {
return JSV.js2d.JsFile.getBufferedURLInputStream (url, outputBytes, post);
}, "java.net.URL,~A,~S");
$_M(c$, "getMouseManager", 
function (jsvp) {
return  new JSV.js2d.Mouse (jsvp);
}, "JSV.api.JSVPanel");
$_V(c$, "getLocalUrl", 
function (fileName) {
return null;
}, "~S");
});
Clazz_declarePackage ("JSV.js2d");
c$ = Clazz_declareType (JSV.js2d, "Display");
c$.getFullScreenDimensions = $_M(c$, "getFullScreenDimensions", 
function (canvas, widthHeight) {
{
widthHeight[0] = canvas.width;
widthHeight[1] = canvas.height;
}}, "~O,~A");
c$.hasFocus = $_M(c$, "hasFocus", 
function (canvas) {
{
}return true;
}, "~O");
c$.requestFocusInWindow = $_M(c$, "requestFocusInWindow", 
function (canvas) {
{
}}, "~O");
c$.repaint = $_M(c$, "repaint", 
function (canvas) {
}, "~O");
c$.renderScreenImage = $_M(c$, "renderScreenImage", 
function (viewer, g, size) {
{
}}, "javajs.api.PlatformViewer,~O,~O");
c$.setTransparentCursor = $_M(c$, "setTransparentCursor", 
function (canvas) {
{
}}, "~O");
c$.setCursor = $_M(c$, "setCursor", 
function (c, canvas) {
{
}}, "~N,~O");
c$.prompt = $_M(c$, "prompt", 
function (label, data, list, asButtons) {
{
var s = prompt(label, data);
if (s != null)return s;
}return "null";
}, "~S,~S,~A,~B");
c$.convertPointFromScreen = $_M(c$, "convertPointFromScreen", 
function (canvas, ptTemp) {
{
}}, "~O,JU.P3");
Clazz_declarePackage ("JSV.js2d");
c$ = Clazz_declareType (JSV.js2d, "Image");
c$.getWidth = $_M(c$, "getWidth", 
function (canvas) {
{
return (canvas.imageWidth ? canvas.imageWidth : canvas.width);
}}, "~O");
c$.getHeight = $_M(c$, "getHeight", 
function (canvas) {
{
return (canvas.imageHeight ? canvas.imageHeight : canvas.height);
}}, "~O");
c$.grabPixels = $_M(c$, "grabPixels", 
function (context, width, height) {
{
if (context._buf32) return context._buf32; // non-canvas internal buffer for image writing
var data = context.getImageData(0, 0, width, height).data;
return this.toIntARGB(data);
}}, "~O,~N,~N");
c$.toIntARGB = $_M(c$, "toIntARGB", 
function (imgData) {
var n = Clazz_doubleToInt (imgData.length / 4);
var iData =  Clazz_newIntArray (n, 0);
for (var i = 0, j = 0; i < n; j++) {
iData[i++] = (imgData[j++] << 16) | (imgData[j++] << 8) | imgData[j++] | 0xFF000000;
}
return iData;
}, "~A");
c$.fromIntARGB = $_M(c$, "fromIntARGB", 
function (buf32, buf8) {
var n = buf8.length >> 2;
for (var i = 0, j = 0; i < n; i++) {
buf8[j++] = (buf32[i] >> 16) & 0xFF;
buf8[j++] = (buf32[i] >> 8) & 0xFF;
buf8[j++] = buf32[i] & 0xFF;
buf8[j++] = 0xFF;
}
}, "~A,~A");
c$.getTextPixels = $_M(c$, "getTextPixels", 
function (text, font3d, context, width, height, ascent) {
{
context.fillStyle = "#000000";
context.fillRect(0, 0, width, height);
context.fillStyle = "#FFFFFF";
context.font = font3d.font;
context.fillText(text, 0, ascent);
return this.grabPixels(context, width, height);
}}, "~S,javajs.awt.Font,~O,~N,~N,~N");
c$.allocateRgbImage = $_M(c$, "allocateRgbImage", 
function (windowWidth, windowHeight, pBuffer, windowSize, backgroundTransparent, canvas) {
{
if (canvas == null)
canvas = {width:windowWidth,height:windowHeight};
canvas.buf32 = pBuffer;
return canvas;
}}, "~N,~N,~A,~N,~B,~O");
c$.getStaticGraphics = $_M(c$, "getStaticGraphics", 
function (canvas, backgroundTransparent) {
{
return this.getGraphics(canvas);
}}, "~O,~B");
c$.getGraphics = $_M(c$, "getGraphics", 
function (canvas) {
{
return canvas.getContext("2d");
}}, "~O");
c$.drawImage = $_M(c$, "drawImage", 
function (context, canvas, x, y, width, height) {
{
this.fromIntARGB(canvas.buf32, canvas.buf8);
context.putImageData(canvas.imgdata,x,y);
}}, "~O,~O,~N,~N,~N,~N");
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["javajs.api.GenericFileInterface"], "JSV.js2d.JsFile", ["JU.PT", "JSV.common.JSVFileManager"], function () {
c$ = Clazz_decorateAsClass (function () {
this.name = null;
this.fullName = null;
Clazz_instantialize (this, arguments);
}, JSV.js2d, "JsFile", null, javajs.api.GenericFileInterface);
c$.newFile = $_M(c$, "newFile", 
function (name) {
return  new JSV.js2d.JsFile (name);
}, "~S");
Clazz_makeConstructor (c$, 
function (name) {
this.name = name.$replace ('\\', '/');
this.fullName = name;
if (!this.fullName.startsWith ("/") && JSV.common.JSVFileManager.urlTypeIndex (name) < 0) this.fullName = JSV.common.JSVFileManager.jsDocumentBase + "/" + this.fullName;
this.fullName = JU.PT.simpleReplace (this.fullName, "/./", "/");
name = name.substring (name.lastIndexOf ("/") + 1);
}, "~S");
$_V(c$, "getParentAsFile", 
function () {
var pt = this.fullName.lastIndexOf ("/");
return (pt < 0 ? null :  new JSV.js2d.JsFile (this.fullName.substring (0, pt)));
});
$_V(c$, "getFullPath", 
function () {
return this.fullName;
});
$_V(c$, "getName", 
function () {
return this.name;
});
$_V(c$, "isDirectory", 
function () {
return this.fullName.endsWith ("/");
});
$_V(c$, "length", 
function () {
return 0;
});
c$.getBufferedURLInputStream = $_M(c$, "getBufferedURLInputStream", 
function (url, outputBytes, post) {
try {
var conn = url.openConnection ();
if (outputBytes != null) conn.outputBytes (outputBytes);
 else if (post != null) conn.outputString (post);
return conn.getSB ();
} catch (e) {
if (Clazz_exceptionOf (e, Exception)) {
return e.toString ();
} else {
throw e;
}
}
}, "java.net.URL,~A,~S");
});
Clazz_declarePackage ("JSV.js2d");
c$ = Clazz_declareType (JSV.js2d, "JsFont");
c$.newFont = $_M(c$, "newFont", 
function (fontFace, isBold, isItalic, fontSize, type) {
fontFace = (fontFace.equals ("Monospaced") ? "Courier" : fontFace.startsWith ("Sans") ? "Sans-Serif" : "Serif");
return (isBold ? "bold " : "") + (isItalic ? "italic " : "") + fontSize + type + " " + fontFace;
}, "~S,~B,~B,~N,~S");
c$.getFontMetrics = $_M(c$, "getFontMetrics", 
function (font, context) {
{
if (context.font != font.font) {
context.font = font.font;
font.font = context.font;
context._fontAscent = Math.ceil(font.fontSize); //pt, not px
// the descent is actually (px - pt)
// but I know of no way of getting access to the drawn height
context._fontDescent = Math.ceil(font.fontSize * 0.25);//approx
}
}return context;
}, "javajs.awt.Font,~O");
c$.getAscent = $_M(c$, "getAscent", 
function (context) {
{
return Math.ceil(context._fontAscent);
}}, "~O");
c$.getDescent = $_M(c$, "getDescent", 
function (context) {
{
return Math.ceil(context._fontDescent);
}}, "~O");
c$.stringWidth = $_M(c$, "stringWidth", 
function (font, text) {
{
font.fontMetrics.font = font.font;
return Math.ceil(font.fontMetrics.measureText(text).width);
}}, "javajs.awt.Font,~S");
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["javajs.api.GenericMouseInterface", "javajs.awt.event.Event"], "JSV.js2d.Mouse", ["J.util.Logger"], function () {
c$ = Clazz_decorateAsClass (function () {
this.pd = null;
this.xWhenPressed = 0;
this.yWhenPressed = 0;
this.modifiersWhenPressed10 = 0;
this.isMouseDown = false;
Clazz_instantialize (this, arguments);
}, JSV.js2d, "Mouse", null, javajs.api.GenericMouseInterface);
Clazz_makeConstructor (c$, 
function (viewer) {
this.pd = viewer.getPanelData ();
}, "JSV.api.JSVPanel");
$_V(c$, "clear", 
function () {
});
$_V(c$, "dispose", 
function () {
});
$_V(c$, "processEvent", 
function (id, x, y, modifiers, time) {
if (id != -1) modifiers = JSV.js2d.Mouse.applyLeftMouse (modifiers);
switch (id) {
case -1:
this.wheeled (time, x, modifiers | 32);
break;
case 501:
this.xWhenPressed = x;
this.yWhenPressed = y;
this.modifiersWhenPressed10 = modifiers;
this.pressed (time, x, y, modifiers, false);
break;
case 506:
this.dragged (time, x, y, modifiers);
break;
case 504:
this.entered (time, x, y);
break;
case 505:
this.exited (time, x, y);
break;
case 503:
this.moved (time, x, y, modifiers);
break;
case 502:
this.released (time, x, y, modifiers);
if (x == this.xWhenPressed && y == this.yWhenPressed && modifiers == this.modifiersWhenPressed10) {
this.clicked (time, x, y, modifiers, 1);
}break;
default:
return false;
}
return true;
}, "~N,~N,~N,~N,~N");
$_M(c$, "mouseClicked", 
function (e) {
this.clicked (e.getWhen (), e.getX (), e.getY (), e.getModifiers (), e.getClickCount ());
}, "java.awt.event.MouseEvent");
$_M(c$, "mouseEntered", 
function (e) {
this.entered (e.getWhen (), e.getX (), e.getY ());
}, "java.awt.event.MouseEvent");
$_M(c$, "mouseExited", 
function (e) {
this.exited (e.getWhen (), e.getX (), e.getY ());
}, "java.awt.event.MouseEvent");
$_M(c$, "mousePressed", 
function (e) {
this.pressed (e.getWhen (), e.getX (), e.getY (), e.getModifiers (), e.isPopupTrigger ());
}, "java.awt.event.MouseEvent");
$_M(c$, "mouseReleased", 
function (e) {
this.released (e.getWhen (), e.getX (), e.getY (), e.getModifiers ());
}, "java.awt.event.MouseEvent");
$_M(c$, "mouseDragged", 
function (e) {
var modifiers = e.getModifiers ();
if ((modifiers & 28) == 0) modifiers |= 16;
this.dragged (e.getWhen (), e.getX (), e.getY (), modifiers);
}, "java.awt.event.MouseEvent");
$_M(c$, "mouseMoved", 
function (e) {
this.moved (e.getWhen (), e.getX (), e.getY (), e.getModifiers ());
}, "java.awt.event.MouseEvent");
$_M(c$, "mouseWheelMoved", 
function (e) {
e.consume ();
this.wheeled (e.getWhen (), e.getWheelRotation (), e.getModifiers () | 32);
}, "java.awt.event.MouseWheelEvent");
$_M(c$, "keyTyped", 
function (ke) {
var ch = ke.getKeyChar ();
var modifiers = ke.getModifiers ();
if (J.util.Logger.debuggingHigh || true) J.util.Logger.info ("MouseManager keyTyped: " + ch + " " + (0 + ch.charCodeAt (0)) + " " + modifiers);
if (this.pd.keyTyped (ch.charCodeAt (0), modifiers)) ke.consume ();
}, "java.awt.event.KeyEvent");
$_M(c$, "keyPressed", 
function (ke) {
if (this.pd.keyPressed (ke.getKeyCode (), ke.getModifiers ())) ke.consume ();
}, "java.awt.event.KeyEvent");
$_M(c$, "keyReleased", 
function (ke) {
this.pd.keyReleased (ke.getKeyCode ());
}, "java.awt.event.KeyEvent");
$_M(c$, "entered", 
function (time, x, y) {
this.pd.mouseEnterExit (time, x, y, false);
}, "~N,~N,~N");
$_M(c$, "exited", 
function (time, x, y) {
this.pd.mouseEnterExit (time, x, y, true);
}, "~N,~N,~N");
$_M(c$, "clicked", 
function (time, x, y, modifiers, clickCount) {
this.pd.mouseAction (2, time, x, y, 1, modifiers);
}, "~N,~N,~N,~N,~N");
$_M(c$, "moved", 
function (time, x, y, modifiers) {
if (this.isMouseDown) this.pd.mouseAction (1, time, x, y, 0, JSV.js2d.Mouse.applyLeftMouse (modifiers));
 else this.pd.mouseAction (0, time, x, y, 0, modifiers & -29);
}, "~N,~N,~N,~N");
$_M(c$, "wheeled", 
function (time, rotation, modifiers) {
this.pd.mouseAction (3, time, 0, rotation, 0, modifiers);
}, "~N,~N,~N");
$_M(c$, "pressed", 
function (time, x, y, modifiers, isPopupTrigger) {
this.isMouseDown = true;
this.pd.mouseAction (4, time, x, y, 0, modifiers);
}, "~N,~N,~N,~N,~B");
$_M(c$, "released", 
function (time, x, y, modifiers) {
this.isMouseDown = false;
this.pd.mouseAction (5, time, x, y, 0, modifiers);
}, "~N,~N,~N,~N");
$_M(c$, "dragged", 
function (time, x, y, modifiers) {
if ((modifiers & 20) == 20) modifiers = modifiers & -5 | 2;
this.pd.mouseAction (1, time, x, y, 0, modifiers);
}, "~N,~N,~N,~N");
c$.applyLeftMouse = $_M(c$, "applyLeftMouse", 
function (modifiers) {
return ((modifiers & 28) == 0) ? (modifiers | 16) : modifiers;
}, "~N");
$_V(c$, "processTwoPointGesture", 
function (touches) {
}, "~A");
});
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["JSV.api.JSVGraphics"], "JSV.js2d.JsG2D", ["javajs.awt.Color", "JU.CU"], function () {
c$ = Clazz_decorateAsClass (function () {
this.windowWidth = 0;
this.windowHeight = 0;
this.isShifted = false;
this.inPath = false;
Clazz_instantialize (this, arguments);
}, JSV.js2d, "JsG2D", null, JSV.api.JSVGraphics);
Clazz_makeConstructor (c$, 
function () {
});
$_V(c$, "getColor4", 
function (r, g, b, a) {
return javajs.awt.Color.get4 (r, g, b, a);
}, "~N,~N,~N,~N");
$_V(c$, "getColor3", 
function (r, g, b) {
return javajs.awt.Color.get3 (r, g, b);
}, "~N,~N,~N");
$_V(c$, "getColor1", 
function (rgb) {
return javajs.awt.Color.get1 (rgb);
}, "~N");
$_V(c$, "newGrayScaleImage", 
function (context, image, width, height, grayBuffer) {
{
var id, canvas;
if (image == null) {
id = ("" + Math.random()).substring(3);
canvas = document.createElement("canvas");
canvas.id = id;
canvas.style.width = width + "px";
canvas.style.height = height + "px";
canvas.width = width;
canvas.height = height;
var appId = context.canvas.applet._id;
var layer = document.getElementById(appId + "_imagelayer");
image = new Image();
image.canvas = canvas;
image.appId = appId;
image.id = appId + "_image";
image.layer = layer;
image.w = width;
image.h = height;
image.onload = function(e) {
try {
URL.revokeObjectURL(image.src);
} catch (e) {}
};
var div = document.createElement("div");
image.div = div;
div.style.position="relative";
layer.appendChild(div);
div.appendChild(image);
} else {
canvas = image.canvas;
}
var c = canvas.getContext("2d");
var imageData = c.getImageData(0, 0, width, height);
var buf = imageData.data;
var ng = grayBuffer.length;
var pt = 0;
for (var i = 0; i < ng; i++) {
buf[pt++] = buf[pt++] = buf[pt++] = grayBuffer[i];
buf[pt++] = 0xFF;
}
c.putImageData(imageData, 0, 0);
canvas.toBlob(function(blob){image.src = URL.createObjectURL(blob)});
}return image;
}, "~O,~O,~N,~N,~A");
$_V(c$, "drawGrayScaleImage", 
function (g, image, destX0, destY0, destX1, destY1, srcX0, srcY0, srcX1, srcY1) {
var iw;
var ih;
{
iw = image.w;
ih = image.h;
}var dw = (destX1 - destX0 + 1);
var dh = (destY1 - destY0 + 1);
var sw = (srcX1 - srcX0 + 1);
var sh = (srcY1 - srcY0 + 1);
var x = -srcX0 * dw / sw;
var w = iw * dw / sw;
var y = -srcY0 * dh / sh;
var h = ih * dh / sh;
{
image.width = w;
image.height = h;
var div = image.div;
var layer = image.layer;
layer.style.left = destX0 + "px";
layer.style.top = destY0 + "px";
layer.style.width = dw + "px";
layer.style.height = dh+ "px";
div.style.left= x + "px";
div.style.top = y + "px";
div.style.width = w + "px";
div.style.height = h + "px";
}}, "~O,~O,~N,~N,~N,~N,~N,~N,~N,~N");
$_V(c$, "drawLine", 
function (g, x0, y0, x1, y1) {
{
if (!this.inPath) g.beginPath();
g.moveTo(x0, y0);
g.lineTo(x1, y1);
if (!this.inPath) g.stroke();
}}, "~O,~N,~N,~N,~N");
$_V(c$, "drawCircle", 
function (g, x, y, diameter) {
{
var r = diameter/2;
g.beginPath();
g.arc(x + r, y + r, r, 0, 2 * Math.PI, false);
g.stroke();
}}, "~O,~N,~N,~N");
$_V(c$, "drawPolygon", 
function (g, ayPoints, axPoints, nPoints) {
this.doPoly (g, ayPoints, axPoints, nPoints, false);
}, "~O,~A,~A,~N");
$_M(c$, "doPoly", 
function (g, axPoints, ayPoints, nPoints, doFill) {
{
g.beginPath();
g.moveTo(axPoints[0], ayPoints[0]);
for (var i = 1; i < nPoints; i++)
g.lineTo(axPoints[i], ayPoints[i]);
if (doFill)
g.fill();
else
g.stroke();
}}, "~O,~A,~A,~N,~B");
$_V(c$, "drawRect", 
function (g, x, y, width, height) {
{
g.beginPath();
g.rect(x ,y, width, height);
g.stroke();
}}, "~O,~N,~N,~N,~N");
$_V(c$, "drawString", 
function (g, s, x, y) {
{
g.fillText(s,x,y);
}}, "~O,~S,~N,~N");
$_V(c$, "drawStringRotated", 
function (g, s, x, y, angle) {
}, "~O,~S,~N,~N,~N");
$_V(c$, "fillBackground", 
function (g, bgcolor) {
if (bgcolor == null) {
{
if (!this.isShifted) {
g.translate(-0.5, -0.5);
this.isShifted = true;
}
g.clearRect(0,0, this.windowWidth, this.windowHeight);
return;
}}this.setGraphicsColor (g, bgcolor);
this.fillRect (g, 0, 0, this.windowWidth, this.windowHeight);
}, "~O,javajs.api.GenericColor");
$_V(c$, "fillCircle", 
function (g, x, y, diameter) {
{
var r = diameter/2;
g.beginPath();
g.arc(x + r, y + r, r, 0, 2 * Math.PI, false);
g.fill();
}}, "~O,~N,~N,~N");
$_V(c$, "fillPolygon", 
function (g, ayPoints, axPoints, nPoints) {
this.doPoly (g, ayPoints, axPoints, nPoints, true);
}, "~O,~A,~A,~N");
$_V(c$, "fillRect", 
function (g, x, y, width, height) {
{
g.fillRect(x, y, width, height);
}}, "~O,~N,~N,~N,~N");
$_V(c$, "setGraphicsColor", 
function (g, c) {
var s = JU.CU.toCSSString (c);
{
g.fillStyle = g.strokeStyle = s;
}}, "~O,javajs.api.GenericColor");
$_V(c$, "setFont", 
function (g, font) {
var s = font.getInfo ();
var pt = s.indexOf (" ");
s = s.substring (0, pt) + "px" + s.substring (pt);
{
g.font = s;
}return font;
}, "~O,javajs.awt.Font");
$_V(c$, "setStrokeBold", 
function (g, tf) {
{
g.lineWidth = (tf ? 2 : 1);
}}, "~O,~B");
$_V(c$, "setWindowParameters", 
function (width, height) {
this.windowWidth = width;
this.windowHeight = height;
}, "~N,~N");
$_V(c$, "translateScale", 
function (g, x, y, scale) {
}, "~O,~N,~N,~N");
$_V(c$, "canDoLineTo", 
function () {
return true;
});
$_V(c$, "doStroke", 
function (g, isBegin) {
{
this.inPath = isBegin;
if (isBegin) {
g.beginPath();
} else {
g.stroke();
}
}}, "~O,~B");
$_V(c$, "lineTo", 
function (g, x2, y2) {
{
g.lineTo(x2, y2);
}}, "~O,~N,~N");
});
Clazz_declarePackage ("javajs.awt");
Clazz_load (["javajs.api.GenericColor"], "javajs.awt.Color", null, function () {
c$ = Clazz_decorateAsClass (function () {
this.argb = 0;
Clazz_instantialize (this, arguments);
}, javajs.awt, "Color", null, javajs.api.GenericColor);
$_V(c$, "getRGB", 
function () {
return this.argb & 0x00FFFFFF;
});
$_V(c$, "getOpacity255", 
function () {
return ((this.argb >> 24) & 0xFF);
});
$_V(c$, "setOpacity255", 
function (a) {
this.argb = this.argb & 0xFFFFFF | ((a & 0xFF) << 24);
}, "~N");
c$.get1 = $_M(c$, "get1", 
function (rgb) {
var c =  new javajs.awt.Color ();
c.argb = rgb | 0xFF000000;
return c;
}, "~N");
c$.get3 = $_M(c$, "get3", 
function (r, g, b) {
return  new javajs.awt.Color ().set4 (r, g, b, 0xFF);
}, "~N,~N,~N");
c$.get4 = $_M(c$, "get4", 
function (r, g, b, a) {
return  new javajs.awt.Color ().set4 (r, g, b, a);
}, "~N,~N,~N,~N");
$_M(c$, "set4", 
function (r, g, b, a) {
this.argb = ((a << 24) | (r << 16) | (g << 8) | b) & 0xFFFFFFFF;
return this;
}, "~N,~N,~N,~N");
});
Clazz_declarePackage ("javajs.api");
Clazz_declareInterface (javajs.api, "GenericColor");
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["JSV.common.ColorParameters"], "JSV.js2d.JsParameters", ["javajs.awt.Color"], function () {
c$ = Clazz_declareType (JSV.js2d, "JsParameters", JSV.common.ColorParameters);
Clazz_makeConstructor (c$, 
function () {
Clazz_superConstructor (this, JSV.js2d.JsParameters, []);
});
$_V(c$, "isValidFontName", 
function (name) {
return true;
}, "~S");
$_V(c$, "getColor1", 
function (rgb) {
return javajs.awt.Color.get1 (rgb);
}, "~N");
$_V(c$, "getColor3", 
function (r, g, b) {
return javajs.awt.Color.get3 (r, g, b);
}, "~N,~N,~N");
$_M(c$, "copy", 
function (newName) {
return ( new JSV.js2d.JsParameters ().setName (newName)).setElementColors (this);
}, "~S");
});
Clazz_declarePackage ("JSV.js2d");
Clazz_load (["JSV.api.JSVFileHelper"], "JSV.js2d.JsFileHelper", ["JSV.js2d.JsFile"], function () {
c$ = Clazz_declareType (JSV.js2d, "JsFileHelper", null, JSV.api.JSVFileHelper);
Clazz_makeConstructor (c$, 
function () {
});
$_V(c$, "set", 
function (viewer) {
return this;
}, "JSV.common.JSViewer");
$_V(c$, "getFile", 
function (fileName, panelOrFrame, isSave) {
var f = null;
{
f = prompt("Enter a file name:", fileName);
}return (f == null ? null :  new JSV.js2d.JsFile (f));
}, "~S,~O,~B");
$_V(c$, "setDirLastExported", 
function (name) {
return name;
}, "~S");
$_V(c$, "setFileChooser", 
function (pdf) {
}, "JSV.common.ExportType");
});
Clazz_declarePackage ("JSV.api");
Clazz_declareInterface (JSV.api, "JSVFileHelper");

})();

Clazz_coreLoaded = true;



})(Clazz
,Clazz.newArray
,Clazz.newBooleanArray
,Clazz.newByteArray
,Clazz.newCharArray
,Clazz.newDoubleArray
,Clazz.newFloatArray
,Clazz.newIntArray
,Clazz.newLongArray
,Clazz.newShortArray
,Clazz.prepareCallback
,Clazz.decorateAsClass
,Clazz.isClassDefined
,Clazz.defineEnumConstant
,Clazz.cloneFinals
,Clazz.inheritArgs
,Clazz.pu$h
,Clazz.declareInterface
,Clazz.declarePackage
,Clazz.makeConstructor
,Clazz.overrideConstructor
,Clazz.load
,Clazz.defineMethod
,Clazz.innerTypeInstance
,Clazz.instanceOf
,Clazz.p0p
,Clazz.makeFunction
,Clazz.superConstructor
,Clazz.defineStatics
,Clazz.registerSerializableFields
,Clazz.declareType
,Clazz.superCall
,Clazz.overrideMethod
,Clazz.declareAnonymous
,Clazz.checkPrivateMethod
,Clazz.prepareFields
,Clazz.instantialize
,Clazz.doubleToInt
,Clazz.declarePackage
,Clazz.instanceOf
,Clazz.load
,Clazz.instantialize
,Clazz.decorateAsClass
,Clazz.floatToInt
,Clazz.makeConstructor
,Clazz.defineEnumConstant
,Clazz.exceptionOf
,Clazz.newIntArray
,Clazz.defineStatics
,Clazz.newFloatArray
,Clazz.declareType
,Clazz.prepareFields
,Clazz.superConstructor
,Clazz.newByteArray
,Clazz.declareInterface
,Clazz.p0p
,Clazz.pu$h
,Clazz.newShortArray
,Clazz.innerTypeInstance
,Clazz.isClassDefined
,Clazz.prepareCallback
,Clazz.newArray
,Clazz.castNullAs
,Clazz.floatToShort
,Clazz.superCall
,Clazz.decorateAsType
,Clazz.newBooleanArray
,Clazz.newCharArray
,Clazz.implementOf
,Clazz.newDoubleArray
,Clazz.overrideConstructor
,Clazz.supportsNativeObject
,Clazz.extendedObjectMethods
,Clazz.callingStackTraces
,Clazz.clone
,Clazz.doubleToShort
,Clazz.innerFunctions
,Clazz.getInheritedLevel
,Clazz.getParamsType
,Clazz.isAF
,Clazz.isAI
,Clazz.isAS
,Clazz.isASS
,Clazz.isAP
,Clazz.isAFloat
,Clazz.isAII
,Clazz.isAFF
,Clazz.isAFFF
,Clazz.tryToSearchAndExecute
,Clazz.getStackTrace
,Clazz.inheritArgs
);
