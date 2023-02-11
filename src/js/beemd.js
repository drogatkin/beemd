function extra_actions(s, ctx) {
	// location.host
	WSAPI.init('ws'+s+'://'+location.hostname+':'+location.port+ctx)
	
}

function updateMDt() {
	loadInnerPage('/beemd/webbee/', '#Showmd', '#payload')
}

function subscribeFor(path) {
    WSAPI.subscribe(path, path)
}