var paper = new Raphael($('#how').get(), 1200, 550);
var p = paper.paper;
//var circle = p.circle(100, 100, 80);
/*
var p1 = 'M 100 100 L 150 50 L 250 50 L 300 100';
var p2 = 'M 200 200 L 100 200';
var p3 = 'M 100 200 C 150 150 250 150 300 200';
var pa = [];
pa[0] = p1;
pa[1] = p2;
var cu = Raphael.path2curve(p3);
console.log(cu);
var nP = p.path(p3);
//var nP = p.path(cu.join(' '));
*/
var link = function(x, y, t){
	
	this.x = x;
	this.y = y;
	this.t = t || 'Link';
	this.instance = p.circle(this.x, this.y, 10);
	this.instance.glow();
	this.text = p.text(this.x, this.y + 20, this.t);
	
	return {
		x : x,
		y : y
	}
};

var g = {

	var paper = new Raphael($('#how').get(), 1200, 550);
	var p = paper.paper;
	var orX = 600, orY = 275;

	center: function(link){
		p.circle(orX, orY, 10).glow().attr({'stroke': 'red'}).animate();
/*		
		var ci = paper.circle(320, 240, 60).glow();
function repeat(){
ci.attr({'transform': 's1'}).animate({'transform': 's1.1'}, 1000, repeat)};
repeat();
*/
	},
	
	clear: function(){
	
	},
	
	outs: function(links){
		var len = links.length;
		var hyp = 300;
		var angle = 0;
		var odd = true;
		for(var i=0; i < len; i++){
			var sin = Math.sin(angle);
			var cos = Math.cos(angle);
			var x = hyp*cos + c.x;
			var lnk = new link(hyp*cos + c.x, hyp*sin + c.y);
			this.connect(this.c, lnk);
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
	},
	
	
	
	connect: function(from, to){
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
		var path = p.path(ps);
		path.attr({stroke: 'white'});
		path.animate({stroke: 'blue'}, 3000);
	}
}

var c = new link(600, 275);
g.center(c);
g.outs([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]);

/*
var o1 = new link(900, 75);
var o2 = new link(900, 175);
var o3 = new link(900, 275);
var o4 = new link(900, 375);
var o5 = new link(900, 475);

g.connect(c, o1);
g.connect(c, o2);
g.connect(c, o3);
g.connect(c, o4);
g.connect(c, o5);

var l1 = new link(800, 75);
var l2 = new link(800, 175);
var l3 = new link(800, 275);
var l4 = new link(800, 375);
var l5 = new link(800, 475);

g.connect(c, l1);
g.connect(c, l2);
g.connect(c, l3);
g.connect(c, l4);
g.connect(c, l5);

var s1 = new link(1000, 75);
var s2 = new link(1000, 175);
var s3 = new link(1000, 275);
var s4 = new link(1000, 375);
var s5 = new link(1000, 475);

g.connect(c, s1);
g.connect(c, s2);
g.connect(c, s3);
g.connect(c, s4);
g.connect(c, s5);
*/