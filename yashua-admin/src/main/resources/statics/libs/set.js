function Set(e) {
    this.init(e);
}

Set.prototype = {
	dataStore: [],
	init: function(e){
		if(e){
			if(e instanceof Array)
				this.addAll(e);
			else
				this.add(e);
		}
	}
};

Set.prototype.add = function (data) {
    if (this.dataStore.indexOf(data) < 0) {
        this.dataStore.push(data);
        return true;
    }
    else {
        return false;
    }
}

Set.prototype.addAll = function (arrays){
	var result = false;
	if(arrays && arrays instanceof Array){
		for(var i=0;i<arrays.length;i++){
			if(this.add(arrays[i]) && result == false){
				result = true;
			}
		}
		return result;
	}
	return result;
}

Set.prototype.remove = function (data) {
    var pos = this.dataStore.indexOf(data);
    if (pos > -1) {
        this.dataStore.splice(pos,1);
        return true;
    }
    else {
        return false;
    }
}

Set.prototype.size = function () {
    return this.dataStore.length;
}

Set.prototype.get = function (i) {
	return this.dataStore[i];
}

Set.prototype.show = function () {
    return "[" + this.dataStore + "]";
}

Set.prototype.contains = function (data) {
    if (this.dataStore.indexOf(data) > -1) {
        return true;
    }
    else {
        return false;
    }
}

Set.prototype.union = function (set) {
    var tempSet = new Set();
    for (var i = 0; i < this.dataStore.length; ++i) {
        tempSet.add(this.dataStore[i]);
    }
    for (var i = 0; i < set.dataStore.length; ++i) {
        if (!tempSet.contains(set.dataStore[i])) {
            tempSet.dataStore.push(set.dataStore[i]);
        }
    }
    return tempSet;
}

Set.prototype.intersect = function (set) {
    var tempSet = new Set();
    for (var i = 0; i < this.dataStore.length; ++i) {
        if (set.contains(this.dataStore[i])) {
            tempSet.add(this.dataStore[i]);
        }
    }
    return tempSet;
}

Set.prototype.subset = function (set) {
    if (this.size() > set.size()) {
        return false;
    }
    else {
        for(var member in this.dataStore) {
            if (!set.contains(member)) {
                return false;
            }
        }
    }
    return true;
}

Set.prototype.difference = function (set) {
    var tempSet = new Set();
    for (var i = 0; i < this.dataStore.length; ++i) {
        if (!set.contains(this.dataStore[i])) {
            tempSet.add(this.dataStore[i]);
        }
    }
    return tempSet;
}