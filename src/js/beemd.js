function extra_actions(s, ctx) {
	// location.host
	WSAPI.init('ws'+s+'://'+location.hostname+':'+location.port+ctx)
	
}

function updateMD() {
	loadInnerPage('/beemd/webbee/', '#Showmd', '#payload')
}

function subscribeFor(path) {
    WSAPI.subscribe(path, path)
}

function showMD(path) {
    loadInnerPage('/beemd/webbee/', '#Showmd?location='+path, '#payload') // escape special symbols from path
    subscribeFor(path)
}