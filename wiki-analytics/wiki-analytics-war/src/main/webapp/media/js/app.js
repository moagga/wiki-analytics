var Wiki = {};
// Model
Wiki.Link = Backbone.Model.extend({});
Wiki.article = new Wiki.Link();

//Home View
Wiki.HomeView = Backbone.View.extend({

  id:  'home',

  events: {
    'change input[name=q]': 'update',
    'click .link':   'click'
  },

  	initialize: function() {
	  	this.$el = $('#home');
	  	this.$results = this.$('.container .results');
	  	this.$heading = this.$('.results h3');
	  	this.$inwards = this.$('.results .inwards');
	  	this.$outwards = this.$('.results .outwards');
	  	this.$input = this.$('input[name=q]');
	  	this.listenTo(Wiki.article, 'change:name', this.nameChange);
	  	this.listenTo(Wiki.article, 'change:inLinks', this.inChange);
	  	this.listenTo(Wiki.article, 'change:outLinks', this.outChange);
//	  	this.listenTo(Wiki.article, 'destroy', this.onDestroy);
  	},

  	nameChange: function(model) {
  		this.$heading.html(model.get('name'));
  	},

  	inChange: function(model) {
  		var html = this._tpl(model);
  		this.$inwards.html(html);
    },

    outChange: function(model) {
    	var html = this._tpl(model);
		this.$outwards.html(html);
  	},

  	update: function(e){
  		var input = this.$input.val();
  		Wiki.article.sync();
  	},
  	
  	click: function(e){
  		
  	},
  	
	_tpl: function(context){
		var html = "";
		html += "<h4>Pages leading to " + context.get('name') + "</h4>";
		html += "<ul class=\"list-inline\">";
		var links = context.get('inLinks);
		for (var i=0; i < links.length; i++){
			html += "<li><a class=\"link btn-lg\" href=\"#\" >" + links[i] + "</a></li>";
		}
		html += "</ul></div>";
	}
});
