/**Elemento Canvas*/
var _canvas;
/**Ancho del canvas*/
var _w;
/**Alto del canvas*/
var _h;
/**Renderizador*/
var _renderer;
/**Escenea*/
var _scene;
/**pausa*/
var _pause = false;
/**delay*/
var _delay;
/**fps*/
var _fps;

function getCookie(w) {
    cName = "";
    pCOOKIES = new Array();
    pCOOKIES = document.cookie.split('; ');
    for (bb = 0; bb < pCOOKIES.length; bb++) {
        NmeVal = new Array();
        NmeVal = pCOOKIES[bb].split('=');
        if (NmeVal[0] == w) {
            cName = unescape(NmeVal[1]);
        }
    }
    return cName;
}

function printCookies(w) {
    cStr = "";
    pCOOKIES = new Array();
    pCOOKIES = document.cookie.split('; ');
    for (bb = 0; bb < pCOOKIES.length; bb++) {
        NmeVal = new Array();
        NmeVal = pCOOKIES[bb].split('=');
        if (NmeVal[0]) {
            cStr += NmeVal[0] + '=' + unescape(NmeVal[1]) + '; ';
        }
    }
    return cStr;
}

function setCookie(name, value, expires, path, domain, secure) {
    document.cookie = name + "=" + escape(value) + "; ";

    if (expires) {
        expires = setExpiration(expires);
        document.cookie += "expires=" + expires + "; ";
    }
    if (path) {
        document.cookie += "path=" + path + "; ";
    }
    if (domain) {
        document.cookie += "domain=" + domain + "; ";
    }
    if (secure) {
        document.cookie += "secure; ";
    }
}

function setExpiration(cookieLife) {
    var today = new Date();
    var expr = new Date(today.getTime() + cookieLife * 24 * 60 * 60 * 1000);
    return  expr.toGMTString();
}

Object.prototype.getName = function() {
    var funcNameRegex = /function (.{1,})\(/;
    var results = (funcNameRegex).exec((this).constructor.toString());
    return (results && results.length > 1) ? results[1] : "";
};

/**define una escena con un color de fondo y una funcion donde se realiza la animacion*/
scene = function(bgColor, animFunction) {
    if (bgColor === undefined)
        bgColor = 0xFFFFFF;
    this.stage = new PIXI.Stage(bgColor, true);
    this.animFunction = animFunction;
    this.addChild = function(child) {
        if (child.getName() === "Menu") {
            this.stage.addChild(child.object);
        } else {
            this.stage.addChild(child);
        }
    };
};

sprite = function(imgUrl) {
    this.sprite = PIXI.Sprite.fromImage(imgUrl);
};

/**Inicia un canvas, define la escena inicial y el tamaño de visualizacion*/
function setGame(canvasId, scene, fps) {
    _fps = fps;
    if (_fps === undefined)
        _fps = 25;
    _resize();
    _canvas = document.getElementById(canvasId);
    _canvas.width = _w;
    _canvas.height = _h;
    _delay = 1000 / _fps;
    _scene = scene;
    _renderer = PIXI.autoDetectRenderer(_w, _h, _canvas);
    window.onresize = function() {
        _resize();
    };
    requestAnimFrame(_animate);
}


function _resize() {
    var w = window,
            d = document,
            e = d.documentElement,
            g = d.getElementsByTagName('body')[0],
            x = w.innerWidth || e.clientWidth || g.clientWidth,
            y = w.innerHeight || e.clientHeight || g.clientHeight;

    //__w = x - 20;
    //__h = y - document.getElementById("top").offsetHeight - 20;
    __w = x;
    __h = y;


    if (__w !== _w || __h !== _h) {
        _w = __w;
        _h = __h;
        if (_renderer !== undefined) {
            _canvas.width = _w;
            _canvas.height = _h;
            _renderer.resize(_w, _h);
        }
    }
    resize();
}

/**Establece la escena*/
function setScene(scene) {
    _scene = scene;
}

/**Obtiene la escena*/
function getScene() {
    return _scene;
}

/**Establece la pausa
 @method setPause
 @param pause {Boolean} true o false
 */
function setPause(pause) {
    _pause = pause;
}

/**Obtiene la pausa*/
function isPaused() {
    return _pause;
}

/**Retorna el numero asociado a un color rgb
 @method color
 @param r {Number} valor del rojo 0-255
 @param g {Number} valor del verde 0-255
 @param b {Number} valor del azul 0-255
 */
function color(r, g, b) {
    return Math.floor(r) * 65536 + Math.floor(g) * 256 + Math.floor(b);
}

var _initTime, _currentTime;
var _frame = -1, _oldframe;

function _setTime(milliseconds) {
    _initTime = Date.now() - milliseconds;
}

function _getTime() {
    return Date.now() - _initTime;
}

function _animate() {
    if (_initTime === undefined) {
        _initTime = Date.now();
    } else {
        _currentTime = Date.now();
        _oldframe = _frame;
        _frame = Math.round(_fps * (_currentTime - _initTime) / 1000);
    }
    if (_oldframe !== _frame) {
        setTimeout("requestAnimFrame(_animate)", _delay);
    } else {
        requestAnimFrame(_animate);
    }
    if (!_pause && _scene.animFunction !== undefined)
        _scene.animFunction();
    _renderer.render(_scene.stage);
}

Animation = function(sprite, size, frames) {
    this.size = size;
    this.sprite = sprite;
    this.frames = frames;
    this.setFrame = function(frame) {
        frame = frame % size;
        idx0 = 1;

        while (frame > this.frames[idx0].t) {
            idx0++;
        }
        idx0--;
        idx1 = idx0;
        factor = 0;
        if (idx0 < frames.length - 1) {
            idx1 = idx0 + 1;
            factor = (frame - frames[idx0].t) / ((frames[idx1].t) - frames[idx0].t);
        }
        sprite.position.x = (frames[idx0].x + ((frames[idx1].x) - frames[idx0].x) * factor);
        sprite.position.y = (frames[idx0].y + ((frames[idx1].y) - frames[idx0].y) * factor);
        sprite.rotation = (frames[idx0].r + ((frames[idx1].r) - frames[idx0].r) * factor);
    };
};