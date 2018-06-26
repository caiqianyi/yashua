function PickerSetup(param){
	this._init(param);
}

PickerSetup.prototype = {
	first: [],
	second: [],
	third: [],
	checked: [0, 0, 0],
	picker: null,
	param: {
		selected: [0,0,0],
		title: "地址选择",
		onSelected: function(){},
		onChange: function(){}
	},
	changes: function (){
		var _this = this;
		return {
			_0: function(index0){
				_this.picker.refillColumn(0, _this.first);
				_this.picker.scrollColumn(0, index0)
			},
			_1: function (index0,index1) {
				if(!index1){
					index1 = 0;
				}
				_this.second = [];
				_this.third = [];
				_this.checked[0] = index0;
				var firstCity = city[index0];
				if (firstCity.hasOwnProperty('sub')) {
					_this.creatList(firstCity.sub, _this.second);
					
					var secondCity = city[index0].sub[0]
					if (secondCity.hasOwnProperty('sub')) {
						_this.creatList(secondCity.sub, _this.third);
					} else {
						_this.third = [{text: '', value: 0}];
						_this.checked[2] = 0;
					}
				} else {
					_this.second = [{text: '', value: 0}];
					_this.third = [{text: '', value: 0}];
					_this.checked[1] = index1;
					_this.checked[2] = 0;
				}
				_this.picker.refillColumn(1, _this.second);
				_this.picker.refillColumn(2, _this.third);
				_this.picker.scrollColumn(1, index1)
				_this.picker.scrollColumn(2, 0)
			},
			_2: function (index1,index2) {
				if(!index2){
					index2 = 0;
				}
			    _this.third = [];
			    _this.checked[1] = index1;
			    first_index = _this.checked[0];
			    if (city[first_index].sub[index1].hasOwnProperty('sub')) {
			    	var secondCity = city[first_index].sub[index1];
					_this.creatList(secondCity.sub, _this.third);
					_this.picker.refillColumn(2, _this.third);
					_this.picker.scrollColumn(2, index2)
			    } else {
					_this.third = [{text: '', value: 0}];
					_this.checked[2] = 0;
					_this.picker.refillColumn(2, _this.third);
					_this.picker.scrollColumn(2, index2)
			    }
			    _this.picker.scrollColumn(1, index1)
			},
			_3: function(index2){
				_this.picker.scrollColumn(2, index2)
			}
		};
	},
	creatList: function(obj, list) {
		obj.forEach(function(item, index, arr) {
			var temp = new Object();
			temp.text = item.name;
			temp.value = index;
			list.push(temp);
		})
	},
	_init : function(param){
		var _this = this;
		_this.param = $.extend(_this.param,param);
		_this.creatList(city, _this.first);

		if (city[_this.param.selected[0]].hasOwnProperty('sub')) {
			_this.creatList(city[_this.param.selected[0]].sub, _this.second);
		} else {
			_this.second = [ {
				text : '',
				value : 0
			}];
		}

		if (city[_this.param.selected[0]].sub[_this.param.selected[1]].hasOwnProperty('sub')) {
			_this.creatList(city[_this.param.selected[0]].sub[_this.param.selected[1]].sub, _this.third);
		} else {
			_this.third = [ {
				text : '',
				value : 0
			} ];
		}

		_this.picker = new Picker({
		    data: [_this.first, _this.second, _this.third],
		    selectedIndex: _this.param.selected,
		    title: _this.param.title
		});
		
		_this.picker.on('picker.select', function (val, selected) {
		  	var text = [_this.first[selected[0]].text,_this.second[selected[1]].text,_this.third[selected[2]] ? _this.third[selected[2]].text : ''];
		  	if(typeof _this.param.onSelected == "function"){
				_this.param.onSelected(selected, text);
			}
		});

		_this.picker.on('picker.change', function (index, selectedIndex) {
			_this.onChange(index+1, selectedIndex, 0);
		});
		
		_this.picker.on('picker.valuechange', function (checked, selectedIndex) {
		   //_this.checked = selectedIndex;
		});
	},
	onChange: function (index, fIndex, sIndex) {
		var _this = this;
		var prop = "_"+index;
		var changes = _this.changes();
		if(changes.hasOwnProperty(prop)){
			changes["_"+index](fIndex, sIndex);
		}
		if(typeof _this.param.onChange == "function"){
			_this.param.onChange(index);
		}
	},
	show: function(fn){
		var _this = this;
		_this.picker.show();
		if(typeof fn == "function"){
			fn();
		}
	},
	setSelected: function(selected){
		var s = [];
		for(var i=0;i<selected.length;i++){
			s[i] = parseInt(selected[i],10);
		}
		var _this = this;
		_this.show(function(){
			setTimeout(function(){
				_this.onChange(0,s[0]);
				_this.onChange(1,s[0],s[1]);
				_this.onChange(2,s[1],s[2]);
				_this.onChange(3,s[2]);
				_this.picker.selectedIndex = s;
				$(".picker .picker-panel .picker-choose .confirm").click();
			},50);
		});
	},
	getCityByCode: function(code){
		var _this = this;
		var index0 = code[0];
		var second = [];
		var third = [];
		var firstCity = city[index0];
		if (firstCity.hasOwnProperty('sub')) {
			_this.creatList(firstCity.sub, second);
			
			var secondCity = city[index0].sub[0]
			if (secondCity.hasOwnProperty('sub')) {
				_this.creatList(secondCity.sub, third);
			} else {
				third = [{text: '', value: 0}];
			}
		} else {
			second = [{text: '', value: 0}];
			third = [{text: '', value: 0}];
		}
		return [firstCity.name,second[code[1]].text,third[code[2]].text];
	}
};

