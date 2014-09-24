var result, aCount = 1;
function getRandomInt() {
	var min = 4, max = 20;
	return Math.floor(Math.random() * (max - min)) + min;
}
function getRandomResults(){
	var count = getRandomInt();
	result = [];
	for(var i = 0; i < count; i++){
		result[i] = 'Article ' + aCount;
	}
	G.paintOut(result);
}

(function(){

	var paper = new Raphael($('#how').get(), 1200, 550).paper,
	origin = {
		x : 600,
		y : 275
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
	
	var outs = function(links){
		var len = links.length;
		var hyp = 300;
		var angle = 0;
		var odd = true;
		for(var i=0; i < len; i++){
			var sin = Math.sin(angle);
			var cos = Math.cos(angle);
			var lnk = new link(hyp*cos + origin.x, hyp*sin + origin.y);
			connect(origin, lnk);
			switch(i%5){
				case 0:
					angle = odd ? (Math.PI/6) : (Math.PI/12);
					break;
				case 1:
					angle = odd ? -(Math.PI/6) : -(Math.PI/12);
					break;
				case 2:
					angle = odd ? (Math.PI/3) : (Math.PI/4);
					break;
				case 3:
					angle = odd ? -(Math.PI/3) : -(Math.PI/4);
					break;
			}
			if (i%5 == 4){
				odd = !odd;
				hyp += 100;
				angle = 0;
			}
		}
	};
	
	
	var connect = function(from, to){
		var ps = 'M ' + from.x + ' ' + from.y;
		var mx = (from.x + to.x)/2, my;
		var up = to.y - from.y;
		
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
		path.animate({stroke: 'blue'}, 3000);
	};
	
	window.G = {
		clear: clear,
		paintOut: outs,
		start: start
	}
})();

G.start('Article 1');
G.paintOut([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]);

