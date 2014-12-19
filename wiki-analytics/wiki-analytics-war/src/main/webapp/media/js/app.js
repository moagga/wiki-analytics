var Wiki = {};
// Model
Wiki.Link = Backbone.Model.extend({});
Wiki.article = new Wiki.Link();

//Home View
Wiki.HomeView = Backbone.View.extend({

  id:  'home',

  events: {
    'change input[name=q]': 'update',
  },

  	initialize: function() {
	  	this.$el = $('#home');
	  	this.$results = this.$('.container .results');
	  	this.$input = this.$('input[name=q]');
  	},

  	nameChange: function(model) {
  		this.$heading.html(model.get('name'));
  	},

  	update: function(e){
  		var input = this.$input.val();
		G.start(input);
//  		Wiki.article.sync();
  	},
  	
  	click: function(e){
  		
  	},
  	
});
