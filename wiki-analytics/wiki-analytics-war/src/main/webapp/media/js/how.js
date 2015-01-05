(function(){

	var w = 1100, h = 480, paper, started = false;
	var origin = {
		x : w/2,
		y : h/2
	};
	
	var init = function(){
		if (started){
			return;
		}
		$('#board').css({width: w, height: h});
		paper = new Raphael($('#board').get(), w, h).paper;
		started = true;
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
		t = t.replace('_', ' ');
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
	
	var getRandomInt = function(min, max) {
		var min = min || 4, max = max || 20;
		return Math.floor(Math.random() * (max - min)) + min;
	};
	
	var fetchFromServer = function(text){
		$.ajax({
			url: 'query?q=' + text,
			success: function(data){
				G.paint(data);
			}
		});
	};

	
	var start = function(text){
		init();
		clear();
		center(text, true);
		fetchFromServer(text);
	}
	
	var clear = function(){
		paper.clear();
	};
	
	var paint = function(data){
		outs(data.out);
		ins(data.in);
	};
	
	var outs = function(links){
		var len = links.length;
		var hyp = 300;
		var angle = 0;
		var odd = true;
		for(var i=0; i < len; i++){
			var x = getRandomInt(w/2 + 100, w);
			var y = getRandomInt(0, h);
			var lnk = new link(x, y, links[i]);
			connect(origin, lnk, false);
		}
	};
	
	var ins = function(links){
		var len = links.length;
		var hyp = 300;
		var angle = 0;
		var odd = true;
		for(var i=0; i < len; i++){
			var x = getRandomInt(0, w/2 - 100);
			var y = getRandomInt(0, h);
			var lnk = new link(x, y, links[i]);
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
	
	$('input[name=q]').on('change', function(){
		var v = $(this).val();
		G.start(v);
	});
	
	window.G = {
		clear: clear,
		paint: paint,
		start: start
	}
	
})();