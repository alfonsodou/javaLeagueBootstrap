// loader de imagenes

var golesTextSize = 32;
item = 0;
var resources = ["img/logo.png", "img/clay.png",
    "img/barra.png",
    "img/grass0.png",
    "img/pubvert.png",
    "img/pubhor.png",
    "img/shadow.png",
    "img/ballShadow.png",
    "img/ball0.png",
    "img/ball1.png",
    "img/ball2.png",
    "img/ball3.png",
    "img/ball4.png",
    "img/ball5.png",
    "img/arco_superior.png",
    "img/arco_inferior.png",
    "img/player.png",
    "img/stadium1.png",
    "img/msg0.png",
    "img/msg1.png",
    "img/msg2.png",
    "img/msg3.png",
    "img/saque.png", "img/marcador.png"];

{
    loader = new PIXI.AssetLoader(resources);
    loader.onProgress = function() {
        item++;
    };
    loader.load();

}

_resize();

var zoom = 1;
try {
    val = getCookie("zoom");
    zoom = parseFloat(val);
    if (isNaN(zoom) || zoom === 0) {
        zoom = 1;
    }
} catch (e) {

}

//establece los FPS
var fps = 24;
fpsCookie = getCookie("FPS");
if (fpsCookie !== "")
    fps = parseInt(fpsCookie);
_fps = 22;
fpsCookie = getCookie("_fps");
if (fpsCookie !== "")
    _fps = parseInt(fpsCookie);
_delay = 1000 / _fps;

//Ancho y alto de la cancha de futbol... todo amplificado por 8
canchaAncho = 68 * 8;
canchaAlto = 105 * 8;

//inicializa la escena del juego, color de fondo 0 y funcion anim para realizar la animacion
appScene = new scene(0, anim);

//Camara de visualizacion
cam = new PIXI.DisplayObjectContainer();
//Centro de la camara en el centro del renderizado
cam.position.x = _w / 2;
cam.position.y = _h / 2;



//Dibuja la grabilla
{
    claySprite = new PIXI.TilingSprite(PIXI.Texture.fromImage("img/clay.png"), canchaAncho * 2.4, canchaAlto * 2.4);
    claySprite.position.x = -canchaAncho / 2 - canchaAncho / 10;
    claySprite.position.y = -canchaAlto / 2 - canchaAlto / 10;
    claySprite.scale.x = .5;
    claySprite.scale.y = .5;
    cam.addChild(claySprite);
}

//Dibuja el pasto
{
    grassSprite = new PIXI.TilingSprite(PIXI.Texture.fromImage("img/grass0.png"), canchaAncho * 2, canchaAlto * 2);
    grassSprite.position.x = -canchaAncho / 2;
    grassSprite.position.y = -canchaAlto / 2;
    grassSprite.scale.x = .5;
    grassSprite.scale.y = .5;
    cam.addChild(grassSprite);
}

//Dibujo lineas de la cancha
{
    graph = new PIXI.Graphics();
    graph.lineStyle(2, 0xffffff, .35);
    graph.drawRect(-canchaAncho / 2, -canchaAlto / 2, canchaAncho, canchaAlto);
    graph.moveTo(-canchaAncho / 2, 0);
    graph.lineTo(canchaAncho / 2, 0);
    graph.drawCircle(0, 0, 9.15 * 8);
    graph.drawRect(-73.28, -canchaAlto / 2, 146.56, 44);
    graph.drawRect(-73.28, canchaAlto / 2 - 44, 146.56, 44);
    graph.drawRect(-161.28, -canchaAlto / 2, 322.56, 132);
    graph.drawRect(-161.28, canchaAlto / 2 - 132, 322.56, 132);
    cam.addChild(graph);
}

//Dibuja la publicidad
{
    publicidad = PIXI.Sprite.fromImage("img/pubvert.png");
    publicidad.anchor.x = .5;
    publicidad.anchor.y = 1;
    publicidad.scale.x = .7;
    publicidad.scale.y = .7;
    publicidad.position.x = 0;
    publicidad.position.y = -canchaAlto / 2 - 28;
    cam.addChild(publicidad);

    publicidad = PIXI.Sprite.fromImage("img/pubvert.png");
    publicidad.anchor.x = .5;
    publicidad.anchor.y = 0;
    publicidad.scale.x = .7;
    publicidad.scale.y = .7;
    publicidad.position.x = 0;
    publicidad.position.y = canchaAlto / 2 + 28;
    cam.addChild(publicidad);

    publicidad = PIXI.Sprite.fromImage("img/pubhor.png");
    publicidad.anchor.x = 1;
    publicidad.anchor.y = .5;
    publicidad.scale.x = .7;
    publicidad.scale.y = .7;
    publicidad.position.x = -canchaAncho / 2 - 12;
    publicidad.position.y = 0;
    cam.addChild(publicidad);
    publicidad = PIXI.Sprite.fromImage("img/pubhor.png");
    publicidad.anchor.x = 0;
    publicidad.anchor.y = .5;
    publicidad.scale.x = .7;
    publicidad.scale.y = .7;
    publicidad.position.x = canchaAncho / 2 + 12;
    publicidad.position.y = 0;
    cam.addChild(publicidad);
}

//Sombras de los jugadores
{
    shadow = new Array(2);
    shadow[0] = new Array(11);
    shadow[1] = new Array(11);
    for (i = 0; i < 2; i++) {
        for (j = 0; j < 11; j++) {
            shadow[i][j] = PIXI.Sprite.fromImage("img/shadow.png");
            shadow[i][j].position.x = -500;
            shadow[i][j].anchor.x = .4;
            cam.addChild(shadow[i][j]);
        }
    }
}

//Sombra del balon
{
    ballShadowSprite = PIXI.Sprite.fromImage("img/ballShadow.png");
    ballShadowSprite.anchor.x = .5;
    ballShadowSprite.anchor.y = .5;
    ballShadowSprite.scale.x = .15;
    ballShadowSprite.scale.y = .15;
    cam.addChild(ballShadowSprite);
}

//Texturas del balon
{
    var ballTextures = [];
    for (var i = 0; i < 6; i++) {
        var texture = PIXI.Texture.fromImage("img/ball" + i + ".png");
        ballTextures.push(texture);
    }
}
//Inicializa los sprites del balon
{
    ballSprite = new Array(2);
    for (i = 0; i < 2; i++) {
        ballSprite[i] = new PIXI.MovieClip(ballTextures);
        ballSprite[i].anchor.x = .5;
        ballSprite[i].anchor.y = .5;
        ballSprite[i].scale.x = .1;
        ballSprite[i].scale.y = .1;
    }
}

//Dibuja el balon a nivel del suelo
cam.addChild(ballSprite[0]);

//Carga imagen de estadio
stadiumSprite = PIXI.Sprite.fromImage("img/stadium1.png");


function addSprites()
{
    localSprite = [];
    visitaSprite = [];
    for (i = 0; i < 11; i++) {
        localSprite.push(_anim_player[0][i]);
        cam.addChild(localSprite[i]);
        visitaSprite.push(_anim_player[1][i]);
        cam.addChild(visitaSprite[i]);
        _anim_player[0][i].scale.x = .12;
        _anim_player[0][i].scale.y = .15;
        _anim_player[1][i].scale.x = .12;
        _anim_player[1][i].scale.y = .15;
        _anim_player[0][i].position.x = -500;
        _anim_player[1][i].position.x = -500;
    }

    //Dibuja los arcos
    {
        arcoSup = PIXI.Sprite.fromImage("img/arco_superior.png");
        arcoSup.anchor.x = .5;
        arcoSup.anchor.y = 1;
        arcoSup.scale.x = .5;
        arcoSup.scale.y = .5;
        arcoSup.position.x = 0;
        arcoSup.position.y = -canchaAlto / 2;
        cam.addChild(arcoSup);
        arcoInf = PIXI.Sprite.fromImage("img/arco_inferior.png");
        arcoInf.anchor.x = .5;
        arcoInf.anchor.y = 0;
        arcoInf.scale.x = .5;
        arcoInf.scale.y = .5;
        arcoInf.position.x = 0;
        arcoInf.position.y = canchaAlto / 2;
        cam.addChild(arcoInf);
    }
    //Dibuja el balon a nivel del aire
    cam.addChild(ballSprite[1]);

    marcaSaque = PIXI.Sprite.fromImage("img/saque.png");
    marcaSaque.anchor.x = .5;
    marcaSaque.anchor.y = .5;
    marcaSaque.scale.x = .25;
    marcaSaque.scale.y = .25;
    marcaSaque.visible = false;

    cam.addChild(marcaSaque);

    //Dibuja el estadio

    stadiumSprite.position.x = -550;
    stadiumSprite.position.y = -700;
    cam.addChild(stadiumSprite);

}

//Crea la matris de animacion de jugadores
step = new Array(2);
step[0] = new Array(11);
step[1] = new Array(11);
for (i = 0; i < 2; i++) {
    for (j = 0; j < 11; j++) {
        step[i][j] = 0;
    }
}
appScene.addChild(cam);
cam.alpha = 0;

var messages = new PIXI.MovieClip(
        [PIXI.Texture.fromImage("img/msg0.png"),
            PIXI.Texture.fromImage("img/msg1.png"),
            PIXI.Texture.fromImage("img/msg2.png"),
            PIXI.Texture.fromImage("img/msg3.png")]);

messages.gotoAndStop(2);
messages.anchor.x = .5;
messages.anchor.y = .5;
messages.position.x = _w / 2;
messages.position.y = _h / 4;
messages.visible = false;
//messages.scale.x=.5;
//messages.scale.y=.5;

appScene.addChild(messages);

golesText = new PIXI.Text("");
golesText.anchor.x = .5;
golesText.anchor.y = .5;

golesText2 = new PIXI.Text("");
golesText2.anchor.x = .5;
golesText2.anchor.y = .5;

appScene.addChild(golesText2);
appScene.addChild(golesText);

//MENU

menu = new Menu("   MENU   ", appScene, function() {
    menu.setSelected(!menu.isSelected());
    selected = menu.isSelected();
    //menuTiempo.setVisible(selected);
    menuZoom.setVisible(selected);
    menuVel.setVisible(selected);
    menuFPS.setVisible(selected);
    menuSNDCANCHA.setVisible(selected);
    menu.redraw();
});

/*menuTiempo = new Menu(" >> ", appScene, function() {
    this.setSelected(!this.isSelected());
    this.redraw();
}, 50);
menuTiempo.addHorizontal(new Menu(" > ", appScene, function() {
    this.setSelected(!this.isSelected());
    this.redraw();
}, 50));

menuTiemploPlay = new Menu(" (>) ", appScene, function() {
    this.setSelected(!this.isSelected());
    this.redraw();
}, 80);
menuTiemploPlay.setSelected(true);
menuTiempo.addHorizontal(menuTiemploPlay);
menuTiempo.addHorizontal(new Menu(" < ", appScene, function() {
    this.setSelected(!this.isSelected());
    this.redraw();
}, 50));
menuTiempo.addHorizontal(new Menu(" << ", appScene, function() {
    this.setSelected(!this.isSelected());
    this.redraw();
}, 50));
menu.addVertical(menuTiempo);
*/

menuZoom = new Menu("  +  ", appScene, function() {
    zoom = zoom * 1.05;
    z = Math.round(zoom * 100);
    setCookie("zoom", zoom);
    menuZoomText.setTitle("ZOOM " + z + "%");
    menuZoomText.redraw();
}, 50);
menuZoomText = new Menu("ZOOM " + Math.round(zoom * 100) + "%", appScene, undefined, 180);
menuZoom.addHorizontal(menuZoomText);
menuZoom.addHorizontal(new Menu("  -  ", appScene, function() {
    zoom = zoom / 1.05;
    z = Math.round(zoom * 100);
    setCookie("zoom", zoom);
    menuZoomText.setTitle("ZOOM " + z + "%");
    menuZoomText.redraw();
}, 50));
menu.addVertical(menuZoom);

menuVel = new Menu("  +  ", appScene, function() {
    _fps++;
    setCookie("_fps", _fps);
    menuVelText.setTitle(" VEL " + _fps);
    menuVelText.redraw();
}, 50);
menuVelText = new Menu("VEL " + _fps, appScene, undefined, 180);
menuVel.addHorizontal(menuVelText);
menuVel.addHorizontal(new Menu("  -  ", appScene, function() {
    _fps--;
    setCookie("_fps", _fps);
    menuVelText.setTitle("VEL " + _fps);
    menuVelText.redraw();
}, 50));
menu.addVertical(menuVel);

menuFPS = new Menu("  +  ", appScene, function() {
    fps++;
    setCookie("FPS", fps);
    _delay = 1000 / fps;
    menuFPSText.setTitle("FPS " + fps);
    menuFPSText.redraw();
}, 50);
menuFPSText = new Menu("FPS " + fps, appScene, undefined, 180);
menuFPS.addHorizontal(menuFPSText);
menuFPS.addHorizontal(new Menu("  -  ", appScene, function() {
    fps--;
    setCookie("FPS", fps);
    _delay = 1000 / fps;
    menuFPSText.setTitle("FPS " + fps);
    menuFPSText.redraw();
}, 50));
menu.addVertical(menuFPS);

menuSNDCANCHA = new Menu("SND 1", appScene, function() {
    this.setSelected(!this.isSelected());
    setSND1(this.isSelected());
    this.redraw();
}, 80);

menuSNDAMBIENTE = new Menu("SND 2", appScene, function() {
    this.setSelected(!this.isSelected());
    setSND2(this.isSelected());
    this.redraw();
}, 80);
menuAUTOZOOM = new Menu("AUTO ZOOM", appScene, function() {
    this.setSelected(!this.isSelected());
    setAutoZoom(this.isSelected());
    this.redraw();
}, 120);
menuSNDCANCHA.addHorizontal(menuSNDAMBIENTE);
menuSNDCANCHA.addHorizontal(menuAUTOZOOM);
menu.addVertical(menuSNDCANCHA);



/*menuRes = new Menu("  +  ", appScene, function() {
 menuResText.setTitle("  +RES   ");
 menuRes.redraw();
 }, 50);
 menuResText = new Menu(" 800x600 ", appScene, undefined, 180);
 menuRes.addHorizontal(menuResText);
 menuRes.addHorizontal(new Menu("  -  ", appScene, function() {
 
 menuResText.setTitle("  -RES   ");
 menuRes.redraw();
 }, 50));
 menu.addVertical(menuRes);
 
 
 menuAu1 = new Menu("  +  ", appScene, function() {
 
 menuAu1Text.setTitle("SND1+100%");
 menuAu1.redraw();
 }, 50);
 menuAu1Text = new Menu("SND1 100%", appScene, undefined, 180);
 menuAu1.addHorizontal(menuAu1Text);
 menuAu1.addHorizontal(new Menu("  -  ", appScene, function() {
 
 menuAu1Text.setTitle("SND1-100%");
 menuAu1.redraw();
 }, 50));
 menu.addVertical(menuAu1);
 
 
 menuAu2 = new Menu("  +  ", appScene, function() {
 
 menuAu2Text.setTitle("SND2+100%");
 menuAu2.redraw();
 }, 50);
 menuAu2Text = new Menu("SND2 100%", appScene, undefined, 180);
 menuAu2.addHorizontal(menuAu2Text);
 menuAu2.addHorizontal(new Menu("  -  ", appScene, function() {
 
 menuAu2Text.setTitle("SND2-100%");
 menuAu2.redraw();
 }, 50));
 menu.addVertical(menuAu2);
 
 menuRes.setVisible(false);
 menuAu1.setVisible(false);
 menuAu2.setVisible(false);*/

//menuTiempo.setVisible(false);
menuZoom.setVisible(false);
menuVel.setVisible(false);
menuFPS.setVisible(false);
menuSNDCANCHA.setVisible(false);

menu.redraw();




//Escala inicial
cam.scale.x = .5;
cam.scale.y = .5;
var escala = .5;

//Sonidos
var sndCount = 0, sndLoad = 0;
function audio2Load(snd) {
    sndCount++;
    snd.bind("loadeddata", function(e) {
        sndLoad++;
    });
    return snd;
}

var ambientSound = new Array(5);
for (var i = 0; i < 5; i++) {
    ambientSound[i] = audio2Load(new buzz.sound("audio/ambient-0" + i, {formats: ["ogg", "mp3", "aac", "wav"]}));
}
var sound = [];
sound.push(audio2Load(new buzz.sound("audio/gol", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/ovacion1", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/ovacion2", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/poste1", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/poste2", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/rebote", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/remate1", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/remate2", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/remate1", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/remate2", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/silbato", {formats: ["ogg", "mp3", "aac", "wav"]})));
sound.push(audio2Load(new buzz.sound("audio/gol", {formats: ["ogg", "mp3", "aac", "wav"]})));

function setSND1(play) {
    vol = play ? 100 : 0;
    for (var i = 0; i < ambientSound.length; i++) {
        ambientSound[i].setVolume(vol);
    }
    sound[0].setVolume(vol);
    sound[1].setVolume(vol);
    sound[2].setVolume(vol);
    sound[sound.length - 1].setVolume(vol);
    setCookie("snd1", play);
    menuSNDCANCHA.setSelected(play);
}
function setSND2(play) {
    vol = play ? 100 : 0;
    for (var i = 3; i < sound.length - 1; i++) {
        sound[i].setVolume(vol);
    }
    setCookie("snd2", play);
    menuSNDAMBIENTE.setSelected(play);
}
var _snd1 = true, _snd2 = true;
var _autoZoom = true;
function setAutoZoom(autoZoom) {
    _autoZoom = autoZoom;
    setCookie("autoZoom", autoZoom);
    menuAUTOZOOM.setSelected(autoZoom);
}
_snd1 = "true" === getCookie("snd1") || "" === getCookie("snd1");
_snd2 = "true" === getCookie("snd2") || "" === getCookie("snd2");
_autoZoom = "true" === getCookie("autoZoom") || "" === getCookie("autoZoom");
setSND1(_snd1);
setSND2(_snd2);
setAutoZoom(_autoZoom);

_ambientIdx = 0;
function playAmbient() {
    newIdx = _ambientIdx;
    do {
        newIdx = random(5);
    } while (newIdx === _ambientIdx);
    _ambientIdx = newIdx;
    snd = ambientSound[_ambientIdx];
    snd.setTime(0);
    snd.play();
}

function updateAmbient() {
    snd = ambientSound[_ambientIdx];
    restTime = snd.getDuration() - snd.getTime();
    if (restTime < 1)
        playAmbient();
}

function reset() {
    playAmbient();
    _setTime(0);
}

function random(num) {
    return Math.floor(Math.random() * num);
}

//Rota la pantalla
rotate = false;
if (rotate)
    cam.rotation = -Math.PI / 2;

//Variables
var d = null;
var t, t0;
var maxvel = 0;
var ballSpeedRotation = 0;
var ballRotation = 0;
var ballAngle = 0;
var initTime;
var initIdx = -1;
var oldidx;
var remateSndIdx = 0;

var _gol = false;
var _poste = false;
var _rebotando = false;
var _ovacionando = false;
var _rematando = false;
var _sacando = false;
var _sacando0 = false;
var _silbando = false;
var _cambioSaque = false;
var _fueraJuego = false;
var _libreIndirecto = false;
var _golFlip = false;
var iterMsg = 0;

score = new PIXI.Graphics();
appScene.addChild(score);

marcadorText = new PIXI.Text("", {font: " 18px Arial", fill: "rgb(255,255,0)"});
marcadorText.anchor.x = 1;
marcadorText.anchor.y = 0;
marcadorText2 = new PIXI.Text("", {font: " 18px Arial", fill: "rgb(255,255,0)"});
marcadorText2.anchor.x = 0;
marcadorText2.anchor.y = 0;
marcadorText.position.x = 80;
marcadorText2.position.x = 80;
marcadorText.position.y = 24;
marcadorText2.position.y = 24;
marcadorText3 = new PIXI.Text("", {font: " 18px Arial", fill: "rgb(255,255,255)"});
marcadorText3.position.x = 80;
marcadorText3.position.y = 5;
marcadorText3.anchor.x = .5;
marcadorText3.anchor.y = 0;

marcadorText4 = new PIXI.Text("", {font: " 18px Arial", fill: "rgb(255,255,255)"});
marcadorText3.position.x = 80;
marcadorText3.position.y = 5;

marcadorSprite = PIXI.Sprite.fromImage("img/marcador.png");

score.beginFill(0, 1);
score.drawRect(0, 0, 160, 70);

spriteBarra = PIXI.Sprite.fromImage("img/barra.png");
spriteBarra.scale.x = 0;
spriteBarra.position.x = 81;
spriteBarra.position.y = 47;
score.addChild(spriteBarra);

score.addChild(marcadorText);
score.addChild(marcadorText2);
score.addChild(marcadorText3);
score.addChild(marcadorSprite);



//tipo de camara
function setCam(speed) {
    if (!rotate) {
        cam.position.x = cam.position.x + (_w / 2 - iteracion.posBalon.x * escala * zoom - cam.position.x) * speed;
        cam.position.y = cam.position.y + (_h / 2 - iteracion.posBalon.y * escala * zoom - cam.position.y) * speed;
    } else {
        cam.position.x = cam.position.y + (_h / 2 - iteracion.posBalon.y * escala * zoom - cam.position.y) * speed;
        cam.position.y = cam.position.x + (_w / 2 - iteracion.posBalon.x * escala * zoom + cam.position.x) * speed;
    }
}

//Animacion del partido
text1 = "";
text2 = "";
text3 = "";
golVisible = true;
firstGolText = true;
function anim() {

    if (partido !== null && iteraciones > 0 && iteraciones === iterLoaded) {

        if (cam.alpha < 1) {
            cam.alpha += 0.02;
        } else if (cam.alpha > 1) {
            cam.alpha = 1;
        }
        if (oldidx === undefined) {
            reset();
        }
        oldidx = idx;
        idx = _frame % iteraciones;

        if (idx > oldidx) {
            if (firstGolText) {
                setGolesText(partido.detailVisita.name + " V/S " + partido.detailLocal.name);
                firstGolText = false;
            }
            updateAmbient();
            iteracion0 = idx === 0 ? null : partido.iteracion[idx - 1];
            iteracion = partido.iteracion[idx];
            if (iteracion !== undefined) {

                posecionLocal = Math.round((iteracion.posecionBalonLocal / 256) * 100);
                posecionVisita = 100 - posecionLocal;
                seconds = Math.floor(Math.min(iteracion.idxTime, 3600) / 20);
                min = Math.floor(seconds / 60);
                seconds = seconds % 60;
                text = "0" + min + ":" + (seconds < 10 ? "0" : "") + seconds;
                if (text !== text3) {
                    text3 = text;
                    marcadorText3.setText(text3);
                }
                text = partido.detailVisita.miniName + " " + (iteracion.golesLocal < 10 ? "0" : "") + iteracion.golesLocal + " ";
                if (text !== text2) {
                    text2 = text;
                    marcadorText.setText(text2);
                }
                text = " " + (iteracion.golesVisita < 10 ? "0" : "") + iteracion.golesVisita + " " + partido.detailLocal.miniName;
                if (text !== text3) {
                    text3 = text;
                    marcadorText2.setText(text3);
                }
                _sacando0 = _sacando;
                _gol = false;
                _poste = false;
                _rebotando = false;
                _ovacionando = false;
                _rematando = false;
                _sacando = false;
                _silbando = false;
                _cambioSaque = false;
                _libreIndirecto = false;
                _fueraJuego = false;
                if (idx - oldidx < 20)
                    for (var i = oldidx; i < idx; i++) {
                        flags = partido.iteracion[i].flags;
                        _gol = _gol || getFlag(0, flags);
                        _poste = _poste || getFlag(1, flags);
                        _rebotando = _rebotando || getFlag(2, flags);
                        _ovacionando = _ovacionando || getFlag(3, flags);
                        _rematando = _rematando || getFlag(4, flags);
                        _sacando = _sacando || getFlag(5, flags);
                        _silbando = _silbando || getFlag(6, flags);
                        _cambioSaque = _cambioSaque || getFlag(7, flags);
                        _libreIndirecto = _libreIndirecto || getFlag(8, flags);
                        _fueraJuego = _fueraJuego || getFlag(9, flags);
                    }

                if (_gol) {
                    setGolesText(partido.detailVisita.name + " " + iteracion.golesLocal
                            + "    " + partido.detailLocal.name + " " + iteracion.golesVisita);
                    golVisible = true;
                }

                messages.visible = (_fueraJuego || _cambioSaque || _gol || _libreIndirecto || iterMsg > 0);
                golesText.visible = golVisible;
                golesText2.visible = golVisible;
                if (messages.visible) {
                    if (iterMsg === 0)
                        iterMsg = 50;

                    iterMsg--;
                    if (_fueraJuego)
                        messages.gotoAndStop(1);
                    else if (_cambioSaque)
                        messages.gotoAndStop(2);
                    else if (_gol)
                        messages.gotoAndStop(0);
                    else if (_libreIndirecto)
                        messages.gotoAndStop(3);
                }

                if (_gol) {
                    _golFlip = !_golFlip;
                    sound[_golFlip ? 0 : 11].play();
                    ballSpeedRotation = 0;
                }
                if (_poste) {
                    sound[3 + random(2)].play();
                }
                if (_rematando) {
                    golVisible = false;
                    remateSndIdx = (remateSndIdx + 1) % 4;
                    sound[6 + remateSndIdx].play();
                }
                if (_ovacionando) {
                    sound[1 + random(2)].play();
                }
                if (_rebotando) {
                    sound[5].play();
                }
                if (_sacando && !_sacando0) {
                    sound[10].play();
                }
                if (_silbando) {
                    sound[10].play();
                }
                if (_cambioSaque) {
                    sound[10].play();
                }
                if (_fueraJuego) {
                }

                velBall = idx === 0 ? 0 : Math.abs(iteracion.posBalon.x - iteracion0.posBalon.x) + Math.abs(iteracion.posBalon.y - iteracion0.posBalon.y);
                if (iteracion.alturaBalon < 128 && idx > 0) {
                    ballSpeedRotation = Math.min(((256 - iteracion.alturaBalon) / 128) * velBall / 6);
                    ballAngle = Math.atan2(
                            iteracion.posVisibleBalon.y - iteracion0.posVisibleBalon.y,
                            iteracion.posVisibleBalon.x - iteracion0.posVisibleBalon.x) + Math.PI / 2;
                } else if (_rematando) {
                    ballSpeedRotation = Math.random() * 2;
                    ballAngle = Math.random() * Math.PI * 2;
                }
                escala = _autoZoom ? (escala + ((1 + velBall / 20) - escala) * .1) : 1;

                ballRotation = ballRotation + ballSpeedRotation;
                ballSpriteIdx = iteracion.alturaBalon < 1280 ? 0 : 1;
                ballSprite[0].visible = 0 === ballSpriteIdx;
                ballSprite[1].visible = 1 === ballSpriteIdx;
                if (_sacando) {
                    marcaSaque.position.x = iteracion.posBalon.x;
                    marcaSaque.position.y = iteracion.posBalon.y;
                    marcaSaque.rotation = marcaSaque.rotation + 0.2;
                    marcaSaque.visible = true;
                    ballSprite[ballSpriteIdx].alpha = ballSprite[ballSpriteIdx].alpha * 0.96;
                    ballShadowSprite.alpha = ballSprite[ballSpriteIdx].alpha;
                } else {
                    marcaSaque.visible = false;
                    if (ballSprite[ballSpriteIdx].alpha < 1) {
                        ballSprite[ballSpriteIdx].alpha = 1;
                        ballShadowSprite.alpha = 1;
                    }
                }
                ballSprite[ballSpriteIdx].gotoAndStop(Math.floor(ballRotation) % 6);
                ballSprite[ballSpriteIdx].rotation = ballAngle;
                ballSprite[ballSpriteIdx].position.x = iteracion.posVisibleBalon.x;
                ballSprite[ballSpriteIdx].position.y = iteracion.posVisibleBalon.y;
                scaleBall = .15 + Math.min(300, iteracion.alturaBalon / 64) / 300;
                ballSprite[ballSpriteIdx].scale.x = scaleBall;
                ballSprite[ballSpriteIdx].scale.y = scaleBall;

                ballShadowSprite.position.x = iteracion.posVisibleBalon.x + 2 + iteracion.alturaBalon / 60;
                ballShadowSprite.position.y = iteracion.posVisibleBalon.y + 2 + iteracion.alturaBalon / 90;

                setCam(.15);

                for (i = 0; i < 2; i++) {
                    for (j = 0; j < 11; j++) {
                        vel = idx === 0 ? 0 : i === 0 ?
                                (Math.abs(iteracion.posLocal[j].x - iteracion0.posLocal[j].x)
                                        + Math.abs(iteracion.posLocal[j].y - iteracion0.posLocal[j].y)) :
                                (Math.abs(iteracion.posVisita[j].x - iteracion0.posVisita[j].x)
                                        + Math.abs(iteracion.posVisita[j].y - iteracion0.posVisita[j].y));
                        if (vel === 0) {
                            if (Math.abs(74 - step[i][j]) < Math.abs(36 - step[i][j]))
                                step[i][j] = step[i][j] + (74 - step[i][j]) * .5;
                            else
                                step[i][j] = step[i][j] + (36 - step[i][j]) * .5;
                        } else {
                            step[i][j] = step[i][j] + vel * (.75 + Math.random() * .35);
                        }

                        if (step[i][j] > 80) {
                            step[i][j] - 80;
                        }
                        setFrame(Math.floor(step[i][j]), i, j);
                    }
                }
                for (player = 0; player < 11; player++) {
                    ang = getAngLocal(partido, idx, 8, player);
                    localSprite[player].rotation = ang + Math.PI / 2;
                    localSprite[player].position.x = iteracion.posLocal[player].x;
                    localSprite[player].position.y = iteracion.posLocal[player].y;
                    ang = getAngVisita(partido, idx, 8, player);
                    visitaSprite[player].rotation = ang + Math.PI / 2;
                    visitaSprite[player].position.x = iteracion.posVisita[player].x;
                    visitaSprite[player].position.y = iteracion.posVisita[player].y;

                    shadow[0][player].position.x = iteracion.posLocal[player].x - 4;
                    shadow[0][player].position.y = iteracion.posLocal[player].y - 7;
                    shadow[1][player].position.x = iteracion.posVisita[player].x - 4;
                    shadow[1][player].position.y = iteracion.posVisita[player].y - 7;
                }
                cam.scale.x = escala * zoom;
                cam.scale.y = escala * zoom;
                drawScore();
            } else {
                d = null;
                idx = 0;
            }
        }
    }
}

function drawScore() {
    if (iteracion !== undefined) {
        value = -(iteracion.posecionBalonLocal - 128) / 256;
        spriteBarra.scale.x = value;
    }
}


loadScene = new scene(0, animLoad);
loadText = new PIXI.Text(".", {font: "bold 18px Arial", fill: "rgba(255,255,255,1)"});
loadText.position.x = 20;
loadText.position.y = 20;
loadText2 = new PIXI.Text("DESARROLLADO POR KREADI", {font: "bold 18px Arial", fill: "rgba(255,255,255,1)"});
loadText2.position.x = 20;
loadText2.position.y = 50;
loadScene.addChild(loadText);
loadScene.addChild(loadText2);
logoKreadi = PIXI.Sprite.fromImage("img/logo.png");
loadScene.addChild(logoKreadi);

setGame('gameCanvas', loadScene, _fps);
console.log(document.location.search.substring(1));
//loadPartido('/imageTransform?file=' + document.location.search.substring(1));
loadPartido('/serveBinMock?file=' + document.location.search.substring(1));

function animLoad() {
    loadText2.position.y = _h - 33;
    logoKreadi.position.x = _w - 122;
    logoKreadi.position.y = _h - 63;
    loadText.setText("VISOR JAVACUP WEBGL\n\n" +
            "DATA:" + iterLoaded + "/" + iteraciones + "\n" +
            "IMAGES:" + item + "/" + resources.length + "\n" +
            "AUDIO:" + sndLoad + "/" + sndCount);
    if (iteraciones > 0) {
        if (iterLoaded < iteraciones || item < resources.length || sndCount === 0 || sndLoad < sndCount) {
            val = true;
            for (var n = 0; val && n < 200; n++)
                val = getIter();
        } else {
            setAnim();
            addSprites();
            setScene(appScene);
        }
    }
}

//Funcion pause
_timePause = 0;
function pause() {
    oldPause = isPaused();
    if (oldPause) {
        _setTime(_timePause);
    } else {
        _timePause = _getTime();
    }
    setPause(!oldPause);
}

function setGolesText(text) {
    text=text.toUpperCase();
    if (golesText !== undefined) {
        var size = Math.floor(_w * 1.4 / text.length);
        golesText.setStyle({font:  size + "px monospace", fill: "rgb(255,255,0)", stroke: "#000000", strokeThickness: 4});
        golesText.setText(text);
        golesText.position.x = _w / 2;
        golesText.position.y = _h / 1.25;
        golesText2.setStyle({font: size + "px monospace", fill: "#000000", stroke: "#000000", strokeThickness: 4});
        golesText2.setText(text);
        golesText2.position.x = (_w / 2 + 2);
        golesText2.position.y = (_h / 1.25) - 4;
    }
}

function resize() {
    if (messages !== undefined) {
        scale = Math.min(_w / 1000, _h / 600);
        if (scale < 1) {
            messages.scale.x = scale;
            messages.scale.y = scale;
        } else {
            messages.scale.x = 1;
            messages.scale.y = 1;
        }
        messages.position.x = _w / 2;
        messages.position.y = _h / 4;
    }
    try {
        menu.x = _w - menu.w;
        menu.redraw();
    } catch (e) {
    }
}