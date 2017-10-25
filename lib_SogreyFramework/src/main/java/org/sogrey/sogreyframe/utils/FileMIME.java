/**
 * @author Sogrey
 * 2015年4月14日
 */
package org.sogrey.sogreyframe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.HashMap;

/**
 * 获取文件MIME
 * 
 * @author Sogrey 2015年4月14日
 */
public class FileMIME {
	/**
	 * @author Sogrey
	 * 
	 *         2015年4月14日
	 */
	private static String getMimeType(String docType) {
		String mime = "";
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("", "application/octet-stream");
		h.put("323", "text/h323");
		h.put("3gp", "video/3gpp");
		h.put("aab", "application/x-authoware-bin");
		h.put("aam", "application/x-authoware-map");
		h.put("aas", "application/x-authoware-seg");
		h.put("acx", "application/internet-property-stream");
		h.put("ai", "application/postscript");
		h.put("aif", "audio/x-aiff");
		h.put("aifc", "audio/x-aiff");
		h.put("aiff", "audio/x-aiff");
		h.put("als", "audio/X-Alpha5");
		h.put("amc", "application/x-mpeg");
		h.put("amr", "audio/amr");
		h.put("ani", "application/octet-stream");
		h.put("apk", "application/vnd.android.package-archive");
		h.put("asc", "text/plain");
		h.put("asd", "application/astound");
		h.put("asf", "video/x-ms-asf");
		h.put("asn", "application/astound");
		h.put("asp", "application/x-asap");
		h.put("asr", "video/x-ms-asf");
		h.put("asx", "video/x-ms-asf");
		h.put("au", "audio/basic");
		h.put("avb", "application/octet-stream");
		h.put("avi", "video/x-msvideo");
		h.put("awb", "audio/amr-wb");
		h.put("axs", "application/olescript");
		h.put("bas", "text/plain");
		h.put("bcpio", "application/x-bcpio");
		h.put("bin ", "application/octet-stream");
		h.put("bld", "application/bld");
		h.put("bld2", "application/bld2");
		h.put("bmp", "image/bmp");
		h.put("bpk", "application/octet-stream");
		h.put("bz2", "application/x-bzip2");
		h.put("c", "text/plain");
		h.put("cal", "image/x-cals");
		h.put("cat", "application/vnd.ms-pkiseccat");
		h.put("ccn", "application/x-cnc");
		h.put("cco", "application/x-cocoa");
		h.put("cdf", "application/x-cdf");
		h.put("cer", "application/x-x509-ca-cert");
		h.put("cgi", "magnus-internal/cgi");
		h.put("chat", "application/x-chat");
		h.put("class", "application/octet-stream");
		h.put("clp", "application/x-msclip");
		h.put("cmx", "image/x-cmx");
		h.put("co", "application/x-cult3d-object");
		h.put("cod", "image/cis-cod");
		h.put("conf", "text/plain");
		h.put("cpio", "application/x-cpio");
		h.put("cpp", "text/plain");
		h.put("cpt", "application/mac-compactpro");
		h.put("crd", "application/x-mscardfile");
		h.put("crl", "application/pkix-crl");
		h.put("crt", "application/x-x509-ca-cert");
		h.put("csh", "application/x-csh");
		h.put("csm", "chemical/x-csml");
		h.put("csml", "chemical/x-csml");
		h.put("css", "text/css");
		h.put("cur", "application/octet-stream");
		h.put("dcm", "x-lml/x-evm");
		h.put("dcr", "application/x-director");
		h.put("dcx", "image/x-dcx");
		h.put("der", "application/x-x509-ca-cert");
		h.put("dhtml", "text/html");
		h.put("dir", "application/x-director");
		h.put("dll", "application/x-msdownload");
		h.put("dmg", "application/octet-stream");
		h.put("dms", "application/octet-stream");
		h.put("doc", "application/msword");
		h.put("docx",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		h.put("dot", "application/msword");
		h.put("dvi", "application/x-dvi");
		h.put("dwf", "drawing/x-dwf");
		h.put("dwg", "application/x-autocad");
		h.put("dxf", "application/x-autocad");
		h.put("dxr", "application/x-director");
		h.put("ebk", "application/x-expandedbook");
		h.put("emb", "chemical/x-embl-dl-nucleotide");
		h.put("embl", "chemical/x-embl-dl-nucleotide");
		h.put("eps", "application/postscript");
		h.put("epub", "application/epub+zip");
		h.put("eri", "image/x-eri");
		h.put("es", "audio/echospeech");
		h.put("esl", "audio/echospeech");
		h.put("etc", "application/x-earthtime");
		h.put("etx", "text/x-setext");
		h.put("evm", "x-lml/x-evm");
		h.put("evy", "application/envoy");
		h.put("exe", "application/octet-stream");
		h.put("fh4", "image/x-freehand");
		h.put("fh5", "image/x-freehand");
		h.put("fhc", "image/x-freehand");
		h.put("fif", "application/fractals");
		h.put("flr", "x-world/x-vrml");
		h.put("flv", "flv-application/octet-stream");
		h.put("fm", "application/x-maker");
		h.put("fpx", "image/x-fpx");
		h.put("fvi", "video/isivideo");
		h.put("gau", "chemical/x-gaussian-input");
		h.put("gca", "application/x-gca-compressed");
		h.put("gdb", "x-lml/x-gdb");
		h.put("gif", "image/gif");
		h.put("gps", "application/x-gps");
		h.put("gtar", "application/x-gtar");
		h.put("gz", "application/x-gzip");
		h.put("h", "text/plain");
		h.put("hdf", "application/x-hdf");
		h.put("hdm", "text/x-hdml");
		h.put("hdml", "text/x-hdml");
		h.put("hlp", "application/winhlp");
		h.put("hqx", "application/mac-binhex40");
		h.put("hta", "application/hta");
		h.put("htc", "text/x-component");
		h.put("htm", "text/html");
		h.put("html", "text/html");
		h.put("hts", "text/html");
		h.put("htt", "text/webviewhtml");
		h.put("ice", "x-conference/x-cooltalk");
		h.put("ico", "image/x-icon");
		h.put("ief", "image/ief");
		h.put("ifm", "image/gif");
		h.put("ifs", "image/ifs");
		h.put("iii", "application/x-iphone");
		h.put("imy", "audio/melody");
		h.put("ins", "application/x-internet-signup");
		h.put("ips", "application/x-ipscript");
		h.put("ipx", "application/x-ipix");
		h.put("isp", "application/x-internet-signup");
		h.put("it", "audio/x-mod");
		h.put("itz", "audio/x-mod");
		h.put("ivr", "i-world/i-vrml");
		h.put("j2k", "image/j2k");
		h.put("jad", "text/vnd.sun.j2me.app-descriptor");
		h.put("jam", "application/x-jam");
		h.put("jar", "application/java-archive");
		h.put("java", "text/plain");
		h.put("jfif", "image/pipeg");
		h.put("jnlp", "application/x-java-jnlp-file");
		h.put("jpe", "image/jpeg");
		h.put("jpeg", "image/jpeg");
		h.put("jpg", "image/jpeg");
		h.put("jpz", "image/jpeg");
		h.put("js", "application/x-javascript");
		h.put("json", "application/json");
		h.put("jwc", "application/jwc");
		h.put("kjx", "application/x-kjx");
		h.put("lak", "x-lml/x-lak");
		h.put("latex", "application/x-latex");
		h.put("lcc", "application/fastman");
		h.put("lcl", "application/x-digitalloca");
		h.put("lcr", "application/x-digitalloca");
		h.put("lgh", "application/lgh");
		h.put("lha", "application/octet-stream");
		h.put("lml", "x-lml/x-lml");
		h.put("lmlpack", "x-lml/x-lmlpack");
		h.put("log", "text/plain");
		h.put("lsf", "video/x-la-asf");
		h.put("lsx", "video/x-la-asf");
		h.put("lzh", "application/octet-stream");
		h.put("m13", "application/x-msmediaview");
		h.put("m14", "application/x-msmediaview");
		h.put("m15", "audio/x-mod");
		h.put("m3u", "audio/x-mpegurl");
		h.put("m3u8", "application/x-mpegURL");//XXX  待测
		h.put("m3url", "audio/x-mpegurl");
		h.put("m4a", "audio/mp4a-latm");
		h.put("m4b", "audio/mp4a-latm");
		h.put("m4p", "audio/mp4a-latm");
		h.put("m4u", "video/vnd.mpegurl");
		h.put("m4v", "video/x-m4v");
		h.put("ma1", "audio/ma1");
		h.put("ma2", "audio/ma2");
		h.put("ma3", "audio/ma3");
		h.put("ma5", "audio/ma5");
		h.put("man", "application/x-troff-man");
		h.put("map", "magnus-internal/imagemap");
		h.put("mbd", "application/mbedlet");
		h.put("mct", "application/x-mascot");
		h.put("mdb", "application/x-msaccess");
		h.put("mdz", "audio/x-mod");
		h.put("me", "application/x-troff-me");
		h.put("mel", "text/x-vmel");
		h.put("mht", "message/rfc822");
		h.put("mhtml", "message/rfc822");
		h.put("mi", "application/x-mif");
		h.put("mid", "audio/mid");
		h.put("midi", "audio/midi");
		h.put("mif", "application/x-mif");
		h.put("mil", "image/x-cals");
		h.put("mio", "audio/x-mio");
		h.put("mmf", "application/x-skt-lbs");
		h.put("mng", "video/x-mng");
		h.put("mny", "application/x-msmoney");
		h.put("moc", "application/x-mocha");
		h.put("mocha", "application/x-mocha");
		h.put("mod", "audio/x-mod");
		h.put("mof", "application/x-yumekara");
		h.put("mol", "chemical/x-mdl-molfile");
		h.put("mop", "chemical/x-mopac-input");
		h.put("mov", "video/quicktime");
		h.put("movie", "video/x-sgi-movie");
		h.put("mp2", "video/mpeg");
		h.put("mp3", "audio/mpeg");
		h.put("mp4", "video/mp4");
		h.put("mpa", "video/mpeg");
		h.put("mpc", "application/vnd.mpohun.certificate");
		h.put("mpe", "video/mpeg");
		h.put("mpeg", "video/mpeg");
		h.put("mpg", "video/mpeg");
		h.put("mpg4", "video/mp4");
		h.put("mpga", "audio/mpeg");
		h.put("mpn", "application/vnd.mophun.application");
		h.put("mpp", "application/vnd.ms-project");
		h.put("mps", "application/x-mapserver");
		h.put("mpv2", "video/mpeg");
		h.put("mrl", "text/x-mrml");
		h.put("mrm", "application/x-mrm");
		h.put("ms", "application/x-troff-ms");
		h.put("msg", "application/vnd.ms-outlook");
		h.put("mts", "application/metastream");
		h.put("mtx", "application/metastream");
		h.put("mtz", "application/metastream");
		h.put("mvb", "application/x-msmediaview");
		h.put("mzv", "application/metastream");
		h.put("nar", "application/zip");
		h.put("nbmp", "image/nbmp");
		h.put("nc", "application/x-netcdf");
		h.put("ndb", "x-lml/x-ndb");
		h.put("ndwn", "application/ndwn");
		h.put("nif", "application/x-nif");
		h.put("nmz", "application/x-scream");
		h.put("nokia-op-logo", "image/vnd.nok-oplogo-color");
		h.put("npx", "application/x-netfpx");
		h.put("nsnd", "audio/nsnd");
		h.put("nva", "application/x-neva1");
		h.put("nws", "message/rfc822");
		h.put("oda", "application/oda");
		h.put("ogg", "audio/ogg");
		h.put("oom", "application/x-AtlasMate-Plugin");
		h.put("p10", "application/pkcs10");
		h.put("p12", "application/x-pkcs12");
		h.put("p7b", "application/x-pkcs7-certificates");
		h.put("p7c", "application/x-pkcs7-mime");
		h.put("p7m", "application/x-pkcs7-mime");
		h.put("p7r", "application/x-pkcs7-certreqresp");
		h.put("p7s", "application/x-pkcs7-signature");
		h.put("pac", "audio/x-pac");
		h.put("pae", "audio/x-epac");
		h.put("pan", "application/x-pan");
		h.put("pbm", "image/x-portable-bitmap");
		h.put("pcx", "image/x-pcx");
		h.put("pda", "image/x-pda");
		h.put("pdb", "chemical/x-pdb");
		h.put("pdf", "application/pdf");
		h.put("pfr", "application/font-tdpfr");
		h.put("pfx", "application/x-pkcs12");
		h.put("pgm", "image/x-portable-graymap");
		h.put("pict", "image/x-pict");
		h.put("pko", "application/ynd.ms-pkipko");
		h.put("pm", "application/x-perl");
		h.put("pma", "application/x-perfmon");
		h.put("pmc", "application/x-perfmon");
		h.put("pmd", "application/x-pmd");
		h.put("pml", "application/x-perfmon");
		h.put("pmr", "application/x-perfmon");
		h.put("pmw", "application/x-perfmon");
		h.put("png", "image/png");
		h.put("pnm", "image/x-portable-anymap");
		h.put("pnz", "image/png");
		h.put("pot,", "application/vnd.ms-powerpoint");
		h.put("ppm", "image/x-portable-pixmap");
		h.put("pps", "application/vnd.ms-powerpoint");
		h.put("ppt", "application/vnd.ms-powerpoint");
		h.put("pptx",
				"application/vnd.openxmlformats-officedocument.presentationml.presentation");
		h.put("pqf", "application/x-cprplayer");
		h.put("pqi", "application/cprplayer");
		h.put("prc", "application/x-prc");
		h.put("prf", "application/pics-rules");
		h.put("prop", "text/plain");
		h.put("proxy", "application/x-ns-proxy-autoconfig");
		h.put("ps", "application/postscript");
		h.put("ptlk", "application/listenup");
		h.put("pub", "application/x-mspublisher");
		h.put("pvx", "video/x-pv-pvx");
		h.put("qcp", "audio/vnd.qcelp");
		h.put("qt", "video/quicktime");
		h.put("qti", "image/x-quicktime");
		h.put("qtif", "image/x-quicktime");
		h.put("r3t", "text/vnd.rn-realtext3d");
		h.put("ra", "audio/x-pn-realaudio");
		h.put("ram", "audio/x-pn-realaudio");
		h.put("rar", "application/octet-stream");
		h.put("ras", "image/x-cmu-raster");
		h.put("rc", "text/plain");
		h.put("rdf", "application/rdf+xml");
		h.put("rf", "image/vnd.rn-realflash");
		h.put("rgb", "image/x-rgb");
		h.put("rlf", "application/x-richlink");
		h.put("rm", "audio/x-pn-realaudio");
		h.put("rmf", "audio/x-rmf");
		h.put("rmi", "audio/mid");
		h.put("rmm", "audio/x-pn-realaudio");
		h.put("rmvb", "audio/x-pn-realaudio");
		h.put("rnx", "application/vnd.rn-realplayer");
		h.put("roff", "application/x-troff");
		h.put("rp", "image/vnd.rn-realpix");
		h.put("rpm", "audio/x-pn-realaudio-plugin");
		h.put("rt", "text/vnd.rn-realtext");
		h.put("rte", "x-lml/x-gps");
		h.put("rtf", "application/rtf");
		h.put("rtg", "application/metastream");
		h.put("rtx", "text/richtext");
		h.put("rv", "video/vnd.rn-realvideo");
		h.put("rwc", "application/x-rogerwilco");
		h.put("s3m", "audio/x-mod");
		h.put("s3z", "audio/x-mod");
		h.put("sca", "application/x-supercard");
		h.put("scd", "application/x-msschedule");
		h.put("sct", "text/scriptlet");
		h.put("sdf", "application/e-score");
		h.put("sea", "application/x-stuffit");
		h.put("setpay", "application/set-payment-initiation");
		h.put("setreg", "application/set-registration-initiation");
		h.put("sgm", "text/x-sgml");
		h.put("sgml", "text/x-sgml");
		h.put("sh", "application/x-sh");
		h.put("shar", "application/x-shar");
		h.put("shtml", "magnus-internal/parsed-html");
		h.put("shw", "application/presentations");
		h.put("si6", "image/si6");
		h.put("si7", "image/vnd.stiwap.sis");
		h.put("si9", "image/vnd.lgtwap.sis");
		h.put("sis", "application/vnd.symbian.install");
		h.put("sit", "application/x-stuffit");
		h.put("skd", "application/x-Koan");
		h.put("skm", "application/x-Koan");
		h.put("skp", "application/x-Koan");
		h.put("skt", "application/x-Koan");
		h.put("slc", "application/x-salsa");
		h.put("smd", "audio/x-smd");
		h.put("smi", "application/smil");
		h.put("smil", "application/smil");
		h.put("smp", "application/studiom");
		h.put("smz", "audio/x-smd");
		h.put("snd", "audio/basic");
		h.put("spc", "application/x-pkcs7-certificates");
		h.put("spl", "application/futuresplash");
		h.put("spr", "application/x-sprite");
		h.put("sprite", "application/x-sprite");
		h.put("sdp", "application/sdp");
		h.put("spt", "application/x-spt");
		h.put("src", "application/x-wais-source");
		h.put("sst", "application/vnd.ms-pkicertstore");
		h.put("stk", "application/hyperstudio");
		h.put("stl", "application/vnd.ms-pkistl");
		h.put("stm", "text/html");
		h.put("svg", "image/svg+xml");
		h.put("sv4cpio", "application/x-sv4cpio");
		h.put("sv4crc", "application/x-sv4crc");
		h.put("svf", "image/vnd");
		h.put("svg", "image/svg+xml");
		h.put("svh", "image/svh");
		h.put("svr", "x-world/x-svr");
		h.put("swf", "application/x-shockwave-flash");
		h.put("swfl", "application/x-shockwave-flash");
		h.put("t", "application/x-troff");
		h.put("tad", "application/octet-stream");
		h.put("talk", "text/x-speech");
		h.put("tar", "application/x-tar");
		h.put("taz", "application/x-tar");
		h.put("tbp", "application/x-timbuktu");
		h.put("tbt", "application/x-timbuktu");
		h.put("tcl", "application/x-tcl");
		h.put("tex", "application/x-tex");
		h.put("texi", "application/x-texinfo");
		h.put("texinfo", "application/x-texinfo");
		h.put("tgz", "application/x-compressed");
		h.put("thm", "application/vnd.eri.thm");
		h.put("tif", "image/tiff");
		h.put("tiff", "image/tiff");
		h.put("tki", "application/x-tkined");
		h.put("tkined", "application/x-tkined");
		h.put("toc", "application/toc");
		h.put("toy", "image/toy");
		h.put("tr", "application/x-troff");
		h.put("trk", "x-lml/x-gps");
		h.put("trm", "application/x-msterminal");
		h.put("tsi", "audio/tsplayer");
		h.put("tsp", "application/dsptype");
		h.put("tsv", "text/tab-separated-values");
		h.put("ttf", "application/octet-stream");
		h.put("ttz", "application/t-time");
		h.put("txt", "text/plain");
		h.put("uls", "text/iuls");
		h.put("ult", "audio/x-mod");
		h.put("ustar", "application/x-ustar");
		h.put("uu", "application/x-uuencode");
		h.put("uue", "application/x-uuencode");
		h.put("vcd", "application/x-cdlink");
		h.put("vcf", "text/x-vcard");
		h.put("vdo", "video/vdo");
		h.put("vib", "audio/vib");
		h.put("viv", "video/vivo");
		h.put("vivo", "video/vivo");
		h.put("vmd", "application/vocaltec-media-desc");
		h.put("vmf", "application/vocaltec-media-file");
		h.put("vmi", "application/x-dreamcast-vms-info");
		h.put("vms", "application/x-dreamcast-vms");
		h.put("vox", "audio/voxware");
		h.put("vqe", "audio/x-twinvq-plugin");
		h.put("vqf", "audio/x-twinvq");
		h.put("vql", "audio/x-twinvq");
		h.put("vre", "x-world/x-vream");
		h.put("vrml", "x-world/x-vrml");
		h.put("vrt", "x-world/x-vrt");
		h.put("vrw", "x-world/x-vream");
		h.put("vts", "workbook/formulaone");
		h.put("wav", "audio/x-wav");
		h.put("wax", "audio/x-ms-wax");
		h.put("wbmp", "image/vnd.wap.wbmp");
		h.put("wcm", "application/vnd.ms-works");
		h.put("wdb", "application/vnd.ms-works");
		h.put("web", "application/vnd.xara");
		h.put("wi", "image/wavelet");
		h.put("wis", "application/x-InstallShield");
		h.put("wks", "application/vnd.ms-works");
		h.put("wm", "video/x-ms-wm");
		h.put("wma", "audio/x-ms-wma");
		h.put("wmd", "application/x-ms-wmd");
		h.put("wmf", "application/x-msmetafile");
		h.put("wml", "text/vnd.wap.wml");
		h.put("wmlc", "application/vnd.wap.wmlc");
		h.put("wmls", "text/vnd.wap.wmlscript");
		h.put("wmlsc", "application/vnd.wap.wmlscriptc");
		h.put("wmlscript", "text/vnd.wap.wmlscript");
		h.put("wmv", "audio/x-ms-wmv");
		h.put("wmx", "video/x-ms-wmx");
		h.put("wmz", "application/x-ms-wmz");
		h.put("wpng", "image/x-up-wpng");
		h.put("wps", "application/vnd.ms-works");
		h.put("wpt", "x-lml/x-gps");
		h.put("wri", "application/x-mswrite");
		h.put("wrl", "x-world/x-vrml");
		h.put("wrz", "x-world/x-vrml");
		h.put("ws", "text/vnd.wap.wmlscript");
		h.put("wsc", "application/vnd.wap.wmlscriptc");
		h.put("wv", "video/wavelet");
		h.put("wvx", "video/x-ms-wvx");
		h.put("wxl", "application/x-wxl");
		h.put("x-gzip", "application/x-gzip");
		h.put("xaf", "x-world/x-vrml");
		h.put("xar", "application/vnd.xara");
		h.put("xbm", "image/x-xbitmap");
		h.put("xdm", "application/x-xdma");
		h.put("xdma", "application/x-xdma");
		h.put("xdw", "application/vnd.fujixerox.docuworks");
		h.put("xht", "application/xhtml+xml");
		h.put("xhtm", "application/xhtml+xml");
		h.put("xhtml", "application/xhtml+xml");
		h.put("xla", "application/vnd.ms-excel");
		h.put("xlc", "application/vnd.ms-excel");
		h.put("xll", "application/x-excel");
		h.put("xlm", "application/vnd.ms-excel");
		h.put("xls", "application/vnd.ms-excel");
		h.put("xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		h.put("xlt", "application/vnd.ms-excel");
		h.put("xlw", "application/vnd.ms-excel");
		h.put("xm", "audio/x-mod");
		h.put("xml", "text/plain");
		h.put("xml", "application/xml");
		h.put("xmz", "audio/x-mod");
		h.put("xof", "x-world/x-vrml");
		h.put("xpi", "application/x-xpinstall");
		h.put("xpm", "image/x-xpixmap");
		h.put("xsit", "text/xml");
		h.put("xsl", "text/xml");
		h.put("xul", "text/xul");
		h.put("xwd", "image/x-xwindowdump");
		h.put("xyz", "chemical/x-pdb");
		h.put("yz1", "application/x-yz1");
		h.put("z", "application/x-compress");
		h.put("zac", "application/x-zaurus-zac");
		h.put("zip", "application/zip");

		mime = h.get(docType.toLowerCase());
		if (mime == null) {
			mime = "application/octet-stream";
		}
		return mime;
	}

	/**
	 * 根据文件名（路径/后缀名）获取文件MIME
	 * 
	 * @author Sogrey 2015年4月14日
	 * @param path
	 * @return
	 */
	public static String GetFileMIME(String path) {
		String suffix = "";
		if (path.contains(".")) {
			suffix = path.split("\\.")[path.split("\\.").length - 1];
		}
		return GetFileMIMEBySuffix(suffix);
	}
	/**
	 * 根据后缀名获取文件MIME
	 *
	 * @author Sogrey 2015年4月14日
	 * @param suffix 后缀名，如MP4 不区分大小写
	 * @return
	 */
	public static String GetFileMIMEBySuffix(String suffix) {
		return getMimeType(suffix);
	}

	/**
	 * 根据文件路径打开文件
	 * 
	 * @author Sogrey 2015年4月14日
	 * @param context
	 * @param path
	 */
	@SuppressLint("ShowToast")
	public static void openFile(Context context, String path) {

		File file = new File(path);
		openFile(context, file);
	}

	/**
	 * 根据文件打开文件
	 * 
	 * @author Sogrey 2015年4月14日
	 * @param context
	 * @param file
	 */
	public static void openFile(Context context, File file) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = GetFileMIME(file.getAbsolutePath());
		// 设置intent的data和Type属性。
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转

		try {
			if (file.exists()) {
				context.startActivity(intent); // 这里最好try一下，有可能会报错。
			} else {
				ToastUtil.showToastCenter(context, "文件不存在");
			}
			// //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 最后这样设置即可
	// response.setContentType(mine+"; charset=gb2312");
	// response.setHeader("Content-disposition",
	// (new StringBuilder()).append("attachment;filename=\"")
	// .append(new String(name.getBytes("GBK"),
	// "ISO8859_1")).append("\"").toString());
}
