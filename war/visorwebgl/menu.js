//Enumeracion de posiciones
var pos = {topleft: 1, topright: 2, bottomleft: 3, bottomright: 4};

//Posicion del menu
var _position = pos.topright;

//Font del texto
var _textFont = "bold 18px Arial";

//Color del texto
var _textColor = "rgba(255,255,255,1)";

//Color del texto seleccionado
var _selTextColor = "rgba(0,0,0,1)";

//Fuente por defecto
var _defaultFont = {
    font: _textFont,
    fill: _textColor
};

//Fondo del menu no seleccionado
var _bgColor = 0x000000;
var _bgAlpha = .5;

//Fondo del menu seleccionado
var _selBgColor = 0xffffff;
var _selBgAlpha = 0.5;

//amplificacion por defecto del alto
var _fontAmp = 2;

function Menu(title, scene, action, width) {
    //Set attributes
    this.width = width;
    this.scene = scene;
    this.title = title;
    this.action = action;
    this.selected = false;
    this.bgColor = _bgColor;
    this.bgAlpha = _bgAlpha;
    this.selBgColor = _selBgColor;
    this.selBgAlpha = _selBgAlpha;
    this.textColor = _textColor;
    this.selTextColor = _selTextColor;
    this.visible = true;
    this.horizontal = [];
    this.vertical = [];
    this.parent = null;
    this.x = 0;
    this.y = 0;
    this.w = 0;
    this.h = 0;
    //Init the container
    this.container = new PIXI.DisplayObjectContainer();
    //Init the graph
    this.graph = new PIXI.Graphics();
    //Add the graph to container
    this.container.addChild(this.graph);
    //Init the thext
    this.text = new PIXI.Text(title, _defaultFont);
    this.text2 = new PIXI.Text("", {fill: "rgb(255,255,0)", font: "10pt consolas"});
    //Add the texto to container    
    this.container.addChild(this.text);
    this.container.addChild(this.text2);
    //Set true menu interactivity
    this.container.setInteractive(true);
    //Add to scene
    this.scene.addChild(this.container);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    var that = this;
    if (that.action !== undefined) {
        this.container.mousedown = function(mouseData) {
            that.action(mouseData);
        };
    }

    this.setVisible = function(visible) {
        that.visible = visible;
        that.horizontal.forEach(function(hchild) {
            hchild.visible = visible;
        });
        that.vertical.forEach(function(vchild) {
            vchild.visible = visible;
        });
        that.redraw();
    };

    this.isVisible = function() {
        return that.visible;
    };

    this.addVertical = function(child) {
        that.vertical.push(child);
        child.parent = that;
    };

    this.addHorizontal = function(child) {
        that.horizontal.push(child);
        child.parent = that;
    };

    this.setBgColor = function(bgColor) {
        that.bgColor = bgColor;
    };
    this.setBgAlpha = function(bgAlpha) {
        that.bgAlpha = bgAlpha;
    };
    this.setSelBgColor = function(selBgColor) {
        that.selBgColor = selBgColor;
    };
    this.setSelBgAlpha = function(selBgAlpha) {
        that.selBgAlpha = selBgAlpha;
    };

    this.setTitle = function(title) {
        that.title = title;
    };

    this.setSelected = function(selected) {
        that.selected = selected;
    };

    this.isSelected = function() {
        return that.selected;
    };

    this.redraw = function() {
        that.graph.clear();
        that.text.setText(that.title);
        that.text.updateText();
        that.borde = that.text.height * (_fontAmp - 1) / 2;
        that.text.setStyle({font: _textFont, fill: that.selected ? _selTextColor : _textColor});
        that.graph.beginFill(that.selected ? that.selBgColor : that.bgColor, that.selected ? that.selBgAlpha : that.bgAlpha);
        that.w = that.width !== undefined ? that.width : that.text.width;
        that.h = that.text.height + that.borde * 2;
        that.graph.lineStyle(2, 0, .5);
        that.graph.drawRect(0, 0, that.w, that.h);

        that.text.position.x = (that.w-that.text.width)/2;
        that.text.position.y = that.borde;
        that.container.position.x = that.x;
        that.container.position.y = that.y;
        this.container.visible = that.visible;
        //that.text2.setText(that.x+":"+that.y+"\n"+that.w+":"+that.h);
        //Set menu hitArea
        that.container.hitArea = new PIXI.Rectangle(0, 0, that.w, that.h);
        var xpos = 0;
        if (that.horizontal.length > 0)
            if (_position === pos.topright || _position === pos.bottomright) {
                xpos = that.x;
                that.horizontal.forEach(function(hchild) {
                    hchild.x = xpos - hchild.w;
                    hchild.y = that.y;
                    hchild.redraw();
                    xpos = xpos - hchild.w;
                });
            } else {
                xpos = that.x + that.w;
                that.horizontal.forEach(function(hchild) {
                    hchild.x = xpos;
                    hchild.y = that.y;
                    hchild.redraw();
                    menuPrev = hchild;
                    xpos = xpos + hchild.w;
                });
            }

        menuPrev = that;
        that.vertical.forEach(function(vchild) {
            if (_position === pos.topright || _position === pos.bottomright) {
                vchild.x = that.x + that.w - vchild.w;
            } else {
                vchild.x = that.x + 2;
            }
            vchild.y = menuPrev.y + menuPrev.h;
            vchild.redraw();
            menuPrev = vchild;
        });
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

