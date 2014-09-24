var result = {}, aCount = 1;
function getRandomInt(min, max) {
	var min = min || 4, max = max || 20;
	return Math.floor(Math.random() * (max - min)) + min;
}
function getRandomResults(){
	var count = getRandomInt();
	var out = [];
	for(var i = 0; i < count; i++){
		out[i] = 'Article ' + aCount;
	}
	result.out = out;
	
	count = getRandomInt();
	var ins = [];
	for(var i = 0; i < count; i++){
		ins[i] = 'Article ' + aCount;
	}
	result.ins = ins;
	G.paint(result);
}

(function(){

	var paper = new Raphael($('#how').get(), 1200, 480).paper,
	origin = {
		x : 600,
		y : 240
	};

	var center = function(link, animate){
		var ci = paper.circle(origin.x, origin.y, 10).glow();
		paper.text(origin.x, origin.y + 20, link);
		if(animate){
			function repeat(){
				ci.attr({'transform': 's1'}).animate({'transform': 's1.5'}, 1000, repeat)
			};
			repeat();
		}
	};
	
	var link = function(x, y, t){
		t = t || 'Link';
		var art = paper.circle(x, y, 10);
		art.glow();
		art.click(function(){
			G.start(t);
		});
		var text = paper.text(x, y + 20, t).attr({'cursor': 'pointer'});
		text.click(function(){
			G.start(t);
		});
		return {
			x : x,
			y : y
		}
	};		
	
	var start = function(text){
		clear();
		center(text, true);
		getRandomResults();
	}
	
	var clear = function(){
		paper.clear();
	};
	
	var paint = function(data){
		outs(data.out);
		ins(data.ins);
	};
	
	var outs = function(links){
		var len = links.length;
		var hyp = 300;
		var angle = 0;
		var odd = true;
		for(var i=0; i < len; i++){
			var x = getRandomInt(600, 1200);
			var y = getRandomInt(0, 480);
			var lnk = new link(x, y);
			connect(origin, lnk, false);
		}
	};
	
	var ins = function(links){
		var len = links.length;
		var hyp = 300;
		var angle = 0;
		var odd = true;
		for(var i=0; i < len; i++){
			var x = getRandomInt(0, 600);
			var y = getRandomInt(0, 480);
			var lnk = new link(x, y);
			connect(lnk, origin, true);
		}
	};
	
	var connect = function(from, to, inward){
		var ps = 'M ' + from.x + ' ' + from.y;
		var mx = (from.x + to.x)/2, my;
		var up = to.y - from.y;
		if (inward){
			up = -1 * up;
		}
		
		if (up < 0){
			my = to.y - 50;
		} else if (up == 0){
			my = to.y;
		} else {
			my = to.y + 50;
		}
		
		ps += ' S ' + mx + ' ' + my + ' ';
		ps += to.x + ' ' + to.y;
		var path = paper.path(ps);
		path.attr({stroke: 'white'});
		if (inward){
			path.animate({stroke: 'green'}, 3000);
		} else {
			path.animate({stroke: 'blue'}, 3000);
		}
	};
	
	window.G = {
		clear: clear,
		paint: paint,
		start: start
	}
})();

G.start('Article 1');
//G.paint([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]);

