function extra_actions(s, ctx) {
	// location.host
	WSAPI.init('ws'+s+'://'+location.hostname+':'+location.port+ctx)
	
}

function updateMD(path) {
	loadInnerPage(baseServURI, '#Showmd?location='+path, '#payload') // escape special symbols from path
}

function subscribeFor(path) {
    WSAPI.subscribe(path, path)
}

function showMD(path) {
    updateMD(path)
    subscribeFor(path)
}