function extra_actions(s, ctx) {
	WSAPI.init('ws'+s+'://'+location.hostname+':'+location.port+ctx)
}

function updateMD(path) {
	loadInnerPage(baseServURI, '#Showmd?location='+encodeURIComponent(path), '#payload') 
}

function subscribeFor(path) {
    WSAPI.subscribe(path, path)
}

function showMD(path) {
    updateMD(path)
    subscribeFor(path)
}