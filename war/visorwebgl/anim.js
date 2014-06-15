

function setFrame(frame) {
    _frame_anim_player_1.setFrame(frame);
    _frame_anim_player_2.setFrame(frame);
    _frame_anim_player_3.setFrame(frame);
    _frame_anim_player_4.setFrame(frame);
    _frame_anim_player_5.setFrame(frame);
    _frame_anim_player_6.setFrame(frame);
    _frame_anim_player_7.setFrame(frame);
    _frame_anim_player_8.setFrame(frame);
    _frame_anim_player_9.setFrame(frame);
    _frame_anim_player_10.setFrame(frame);
    _frame_anim_player_11_1.setFrame(frame);
    _frame_anim_player_11.setFrame(frame);
    _frame_anim_player_12.setFrame(frame);
    _frame_anim_player_13.setFrame(frame);
    _frame_anim_player_14.setFrame(frame);
    _frame_anim_player_15.setFrame(frame);
    _frame_anim_player.setFrame(frame);
    ;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

_baseTexture = [];
player_new2_png_1 = null;
player_new2_png_2 = null;
player_new2_png_3 = null;
player_new2_png_4 = null;
player_new2_png_5 = null;
player_new2_png_6 = null;
player_new2_png_7 = null;
player_new2_png_8 = null;
player_new2_png_9 = null;
player_new2_png_10 = null;
player_new2_png_11 = null;
player_new2_png_12 = null;
player_new2_png_13 = null;
player_new2_png_14 = null;
player_new2_png_15 = null;
player_new2_png_16 = null;
player_new2_png_17 = null;
player_new2_png_18 = null;
player_new2_png_19 = null;
player_new2_png_20 = null;

function setColors(colors) {
    var url = '/imageTransform?path=/visorwebgl/img/player2.png&nocache&from=0.5666666666,0.15833333333,0,0.4222222222222,0.202777,0.7083333&error=.1,.1,.1,.1,.1,.1&to=';
    url = url + colors[0] + "." + colors[1] + "." + colors[2] + ",";//short
    url = url + colors[3] + "." + colors[4] + "." + colors[5] + ",";//polera1
    url = url + colors[6] + "." + colors[7] + "." + colors[8] + ",";//polera2
    url = url + colors[9] + "." + colors[10] + "." + colors[11] + ",";//pelo
    url = url + colors[12] + "." + colors[13] + "." + colors[14] + ",";//piel
    url = url + colors[15] + "." + colors[16] + "." + colors[17];//calcetas

    //url='img/player.png';

    _baseTexture = PIXI.BaseTexture.fromImage(url);
    player_new2_png_1 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(10, 12, 79, 47));
    player_new2_png_2 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(124, 16, 19, 22));
    player_new2_png_3 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(154, 16, 19, 22));
    player_new2_png_4 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(10, 75, 79, 47));
    player_new2_png_5 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(109, 53, 33, 35));
    player_new2_png_6 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(150, 55, 34, 31));
    player_new2_png_7 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(105, 104, 34, 30));
    player_new2_png_8 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(147, 100, 34, 38));
    player_new2_png_9 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(191, 83, 26, 40));
    player_new2_png_10 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(224, 82, 26, 41));
    player_new2_png_11 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(10, 141, 79, 47));
    player_new2_png_12 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(101, 141, 79, 47));
    player_new2_png_13 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(188, 141, 79, 46));
    player_new2_png_14 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(16, 204, 13, 23));
    player_new2_png_15 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(37, 204, 13, 23));
    player_new2_png_16 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(102, 196, 35, 37));
    player_new2_png_17 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(153, 196, 18, 35));
    player_new2_png_18 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(178, 196, 18, 35));
    player_new2_png_19 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(223, 204, 10, 18));
    player_new2_png_20 = new PIXI.Texture(_baseTexture, new PIXI.Rectangle(246, 205, 10, 18));
}

_anim_player = new Array(2);
_frame_anim_player = new Array(2);
_anim_player_1 = new Array(2);
_frame_anim_player_1 = new Array(2);
_anim_player_2 = new Array(2);
_frame_anim_player_2 = new Array(2);
_anim_player_3 = new Array(2);
_frame_anim_player_3 = new Array(2);
_anim_player_4 = new Array(2);
_frame_anim_player_4 = new Array(2);
_anim_player_5 = new Array(2);
_frame_anim_player_5 = new Array(2);
_anim_player_6 = new Array(2);
_frame_anim_player_6 = new Array(2);
_anim_player_7 = new Array(2);
_frame_anim_player_7 = new Array(2);
_anim_player_8 = new Array(2);
_frame_anim_player_8 = new Array(2);
_anim_player_9 = new Array(2);
_frame_anim_player_9 = new Array(2);
_anim_player_10 = new Array(2);
_frame_anim_player_10 = new Array(2);
_anim_player_11 = new Array(2);
_frame_anim_player_11 = new Array(2);
_anim_player_11_1 = new Array(2);
_frame_anim_player_11_1 = new Array(2);
_anim_player_12 = new Array(2);
_frame_anim_player_12 = new Array(2);
_anim_player_13 = new Array(2);
_frame_anim_player_13 = new Array(2);
_anim_player_14 = new Array(2);
_frame_anim_player_14 = new Array(2);
_anim_player_15 = new Array(2);
_frame_anim_player_15 = new Array(2);
_anim_player_16 = new Array(2);
_frame_anim_player_16 = new Array(2);
_anim_player_17 = new Array(2);
_frame_anim_player_17 = new Array(2);
_anim_player_18 = new Array(2);
_frame_anim_player_18 = new Array(2);
_anim_player_19 = new Array(2);
_frame_anim_player_19 = new Array(2);
_anim_player_20 = new Array(2);
_frame_anim_player_20 = new Array(2);

function setAnim() {
    altColor = useAlternativeColors(partido.detailVisita, partido.detailLocal);//OJO ESTA ALREVES
    for (i = 0; i < 2; i++) {
        _anim_player[i] = new Array(11);
        _frame_anim_player[i] = new Array(11);
        _anim_player_1[i] = new Array(11);
        _frame_anim_player_1[i] = new Array(11);
        _anim_player_2[i] = new Array(11);
        _frame_anim_player_2[i] = new Array(11);
        _anim_player_3[i] = new Array(11);
        _frame_anim_player_3[i] = new Array(11);
        _anim_player_4[i] = new Array(11);
        _frame_anim_player_4[i] = new Array(11);
        _anim_player_5[i] = new Array(11);
        _frame_anim_player_5[i] = new Array(11);
        _anim_player_6[i] = new Array(11);
        _frame_anim_player_6[i] = new Array(11);
        _anim_player_7[i] = new Array(11);
        _frame_anim_player_7[i] = new Array(11);
        _anim_player_8[i] = new Array(11);
        _frame_anim_player_8[i] = new Array(11);
        _anim_player_9[i] = new Array(11);
        _frame_anim_player_9[i] = new Array(11);
        _anim_player_10[i] = new Array(11);
        _frame_anim_player_10[i] = new Array(11);
        _anim_player_11[i] = new Array(11);
        _frame_anim_player_11[i] = new Array(11);
        _anim_player_11_1[i] = new Array(11);
        _frame_anim_player_11_1[i] = new Array(11);
        _anim_player_12[i] = new Array(11);
        _frame_anim_player_12[i] = new Array(11);
        _anim_player_13[i] = new Array(11);
        _frame_anim_player_13[i] = new Array(11);
        _anim_player_14[i] = new Array(11);
        _frame_anim_player_14[i] = new Array(11);
        _anim_player_15[i] = new Array(11);
        _frame_anim_player_15[i] = new Array(11);
        _anim_player_16[i] = new Array(11);
        _frame_anim_player_16[i] = new Array(11);
        _anim_player_17[i] = new Array(11);
        _frame_anim_player_17[i] = new Array(11);
        _anim_player_18[i] = new Array(11);
        _frame_anim_player_18[i] = new Array(11);
        _anim_player_19[i] = new Array(11);
        _frame_anim_player_19[i] = new Array(11);
        _anim_player_20[i] = new Array(11);
        _frame_anim_player_20[i] = new Array(11);
        for (j = 0; j < 11; j++) {
            tact = i === 1 ? partido.detailLocal : partido.detailVisita;
            player = tact.player[j];
            if (i === 1 && altColor) {
                if (tact.goalKeeper === j)
                    setColors([tact.colorGoalKeeper2.r, tact.colorGoalKeeper2.g, tact.colorGoalKeeper2.b,
                        tact.colorGoalKeeper2.r, tact.colorGoalKeeper2.g, tact.colorGoalKeeper2.b,
                        tact.colorGoalKeeper2.r, tact.colorGoalKeeper2.g, tact.colorGoalKeeper2.b,
                        player.colorHair.r, player.colorHair.g, player.colorHair.b,
                        player.colorSkin.r, player.colorSkin.g, player.colorSkin.b,
                        tact.colorGoalKeeper2.r, tact.colorGoalKeeper2.g, tact.colorGoalKeeper2.b]);
                else
                    setColors([tact.colorShorts2.r, tact.colorShorts2.g, tact.colorShorts2.b,
                        tact.colorShirt2.r, tact.colorShirt2.g, tact.colorShirt2.b,
                        tact.colorShirtLine2.r, tact.colorShirtLine2.g, tact.colorShirtLine2.b,
                        player.colorHair.r, player.colorHair.g, player.colorHair.b,
                        player.colorSkin.r, player.colorSkin.g, player.colorSkin.b,
                        tact.colorSocks2.r, tact.colorSocks2.g, tact.colorSocks2.b]);
                polera = tact.goalKeeper === j || tact.style2 === 6 ? player_new2_png_1 : tact.style2 === 1 || tact.style2 === 4 ? player_new2_png_12 :
                        tact.style2 === 2 ? player_new2_png_4 : tact.style2 === 3 ? player_new2_png_13 : player_new2_png_11;
            } else {
                if (tact.goalKeeper === j)
                    setColors([tact.colorGoalKeeper.r, tact.colorGoalKeeper.g, tact.colorGoalKeeper.b,
                        tact.colorGoalKeeper.r, tact.colorGoalKeeper.g, tact.colorGoalKeeper.b,
                        tact.colorGoalKeeper.r, tact.colorGoalKeeper.g, tact.colorGoalKeeper.b,
                        player.colorHair.r, player.colorHair.g, player.colorHair.b,
                        player.colorSkin.r, player.colorSkin.g, player.colorSkin.b,
                        tact.colorGoalKeeper.r, tact.colorGoalKeeper.g, tact.colorGoalKeeper.b]);
                else
                    setColors([tact.colorShorts.r, tact.colorShorts.g, tact.colorShorts.b,
                        tact.colorShirt.r, tact.colorShirt.g, tact.colorShirt.b,
                        tact.colorShirtLine.r, tact.colorShirtLine.g, tact.colorShirtLine.b,
                        player.colorHair.r, player.colorHair.g, player.colorHair.b,
                        player.colorSkin.r, player.colorSkin.g, player.colorSkin.b,
                        tact.colorSocks.r, tact.colorSocks.g, tact.colorSocks.b]);
                polera = tact.goalKeeper === j || tact.style1 === 6 ? player_new2_png_1 : tact.style1 === 1 || tact.style1 === 4 ? player_new2_png_12 :
                        tact.style1 === 2 ? player_new2_png_4 : tact.style1 === 3 ? player_new2_png_13 : player_new2_png_11;
            }
            _anim_player[i][j] = new PIXI.DisplayObjectContainer();
            _anim_player[i][j].position.x = 0.0;
            _anim_player[i][j].position.y = -9.0;
            _anim_player[i][j].rotation = 0.0;
            _anim_player_1[i][j] = new PIXI.Sprite(player_new2_png_19);
            _anim_player_1[i][j].anchor.x = 0.5;
            _anim_player_1[i][j].anchor.y = 0.5;
            _anim_player_1[i][j].position.x = -17.5;
            _anim_player_1[i][j].position.y = -27.5;
            _anim_player_1[i][j].rotation = -3.097959422289935;
            _frame_anim_player_1[i][j] = new Animation(_anim_player_1[i][j], 80, [
                {t: 0, r: -3.097959422289935, x: -17.5, y: -27.5},
                {t: 10, r: -3.141592653589793, x: -18.0, y: -34.5},
                {t: 20, r: -3.141592653589793, x: -19.5, y: -39.0},
                {t: 30, r: -3.1852258848896517, x: -23.5, y: -28.0},
                {t: 40, r: -3.141592653589793, x: -15.25, y: 42.5},
                {t: 50, r: -3.141592653589793, x: -20.25, y: 46.5},
                {t: 60, r: -3.097959422289935, x: -26.0, y: 51.5},
                {t: 70, r: -3.141592653589793, x: -25.5, y: 33.25},
                {t: 80, r: -3.097959422289935, x: -17.5, y: -27.5}]);
            _anim_player[i][j].addChild(_anim_player_1[i][j]);
            _anim_player_2[i][j] = new PIXI.Sprite(player_new2_png_20);
            _anim_player_2[i][j].anchor.x = 0.5;
            _anim_player_2[i][j].anchor.y = 0.5;
            _anim_player_2[i][j].position.x = 23.25;
            _anim_player_2[i][j].position.y = 41.0;
            _anim_player_2[i][j].rotation = 0.0;
            _frame_anim_player_2[i][j] = new Animation(_anim_player_2[i][j], 80, [
                {t: 0, r: 0.0, x: 23.25, y: 41.0},
                {t: 10, r: 0.0, x: 20.5, y: 46.5},
                {t: 20, r: -0.04363323129985824, x: 25.0, y: 50.75},
                {t: 30, r: 0.0, x: 24.0, y: 33.75},
                {t: 40, r: 0.08726646259971647, x: 20.0, y: -29.5},
                {t: 50, r: 0.0, x: 23.5, y: -28.0},
                {t: 60, r: 0.0, x: 23.5, y: -37.0},
                {t: 70, r: 0.0, x: 24.0, y: -28.5},
                {t: 80, r: 0.0, x: 23.5, y: 48.5}]);
            _anim_player[i][j].addChild(_anim_player_2[i][j]);
            _anim_player_3[i][j] = new PIXI.Sprite(player_new2_png_17);
            _anim_player_3[i][j].anchor.x = 0.5;
            _anim_player_3[i][j].anchor.y = 0.5;
            _anim_player_3[i][j].position.x = -18.5;
            _anim_player_3[i][j].position.y = -16.0;
            _anim_player_3[i][j].rotation = -0.17453292519943295;
            _frame_anim_player_3[i][j] = new Animation(_anim_player_3[i][j], 80, [
                {t: 0, r: -0.17453292519943295, x: -18.5, y: -16.0},
                {t: 10, r: -0.2617993877991494, x: -19.0, y: -22.0},
                {t: 20, r: -0.2181661564992912, x: -20.0, y: -29.5},
                {t: 30, r: -0.2181661564992912, x: -23.5, y: -18.5},
                {t: 40, r: -0.2181661564992912, x: -15.25, y: 30.0},
                {t: 50, r: -0.08726646259971647, x: -18.25, y: 33.0},
                {t: 60, r: -0.1308996938995747, x: -24.5, y: 39.75},
                {t: 70, r: -0.1308996938995747, x: -23.5, y: 20.0},
                {t: 80, r: -0.17453292519943295, x: -18.5, y: -16.0}]);
            _anim_player[i][j].addChild(_anim_player_3[i][j]);
            _anim_player_4[i][j] = new PIXI.Sprite(player_new2_png_18);
            _anim_player_4[i][j].anchor.x = 0.5;
            _anim_player_4[i][j].anchor.y = 0.5;
            _anim_player_4[i][j].position.x = 21.75;
            _anim_player_4[i][j].position.y = 27.25;
            _anim_player_4[i][j].rotation = 0.2617993877991494;
            _frame_anim_player_4[i][j] = new Animation(_anim_player_4[i][j], 80, [
                {t: 0, r: 0.2617993877991494, x: 21.75, y: 27.25},
                {t: 10, r: 0.2181661564992912, x: 19.75, y: 34.0},
                {t: 20, r: 0.1308996938995747, x: 22.5, y: 38.75},
                {t: 30, r: 0.2181661564992912, x: 23.25, y: 22.0},
                {t: 40, r: 0.3490658503988659, x: 18.5, y: -17.0},
                {t: 50, r: 0.2181661564992912, x: 22.0, y: -18.0},
                {t: 60, r: 0.17453292519943295, x: 23.5, y: -26.5},
                {t: 70, r: 0.1308996938995747, x: 24.5, y: -18.0},
                {t: 80, r: 0.2617993877991494, x: 21.5, y: 34.5}]);
            _anim_player[i][j].addChild(_anim_player_4[i][j]);
            _anim_player_5[i][j] = new PIXI.Sprite(player_new2_png_2);
            _anim_player_5[i][j].anchor.x = 0.5;
            _anim_player_5[i][j].anchor.y = 0.5;
            _anim_player_5[i][j].position.x = -17.0;
            _anim_player_5[i][j].position.y = -16.0;
            _anim_player_5[i][j].rotation = -0.1308996938995747;
            _frame_anim_player_5[i][j] = new Animation(_anim_player_5[i][j], 80, [
                {t: 0, r: -0.1308996938995747, x: -17.0, y: -16.0},
                {t: 10, r: -0.08726646259971647, x: -18.5, y: -23.0},
                {t: 20, r: -0.1308996938995747, x: -20.5, y: -27.0},
                {t: 30, r: -0.2617993877991494, x: -22.5, y: -19.0},
                {t: 40, r: -0.3490658503988659, x: -15.5, y: 25.75},
                {t: 50, r: 0.08726646259971647, x: -17.75, y: 29.75},
                {t: 60, r: -0.1308996938995747, x: -23.75, y: 30.5},
                {t: 70, r: -0.1308996938995747, x: -23.0, y: 16.0},
                {t: 80, r: -0.1308996938995747, x: -17.0, y: -16.0}]);
            _anim_player[i][j].addChild(_anim_player_5[i][j]);
            _anim_player_6[i][j] = new PIXI.Sprite(player_new2_png_3);
            _anim_player_6[i][j].anchor.x = 0.5;
            _anim_player_6[i][j].anchor.y = 0.5;
            _anim_player_6[i][j].position.x = 20.0;
            _anim_player_6[i][j].position.y = 24.5;
            _anim_player_6[i][j].rotation = 0.0;
            _frame_anim_player_6[i][j] = new Animation(_anim_player_6[i][j], 80, [
                {t: 0, r: 0.0, x: 20.0, y: 24.5},
                {t: 10, r: 0.1308996938995747, x: 19.0, y: 29.25},
                {t: 20, r: -0.04363323129985824, x: 21.0, y: 27.0},
                {t: 30, r: 0.1308996938995747, x: 22.75, y: 20.25},
                {t: 40, r: 0.5235987755982988, x: 18.0, y: -18.0},
                {t: 50, r: 0.17453292519943295, x: 21.0, y: -19.0},
                {t: 60, r: 0.1308996938995747, x: 23.0, y: -25.0},
                {t: 70, r: 0.1308996938995747, x: 23.0, y: -21.5},
                {t: 80, r: 0.1308996938995747, x: 22.5, y: 31.0}]);
            _anim_player[i][j].addChild(_anim_player_6[i][j]);
            _anim_player_7[i][j] = new PIXI.Sprite(player_new2_png_5);
            _anim_player_7[i][j].anchor.x = 0.5;
            _anim_player_7[i][j].anchor.y = 0.5;
            _anim_player_7[i][j].position.x = -13.0;
            _anim_player_7[i][j].position.y = -5.5;
            _anim_player_7[i][j].rotation = 0.17453292519943295;
            _frame_anim_player_7[i][j] = new Animation(_anim_player_7[i][j], 80, [
                {t: 0, r: 0.17453292519943295, x: -13.0, y: -5.5},
                {t: 10, r: 0.1308996938995747, x: -15.0, y: -9.5},
                {t: 20, r: 0.1308996938995747, x: -16.5, y: -13.0},
                {t: 30, r: 0.17453292519943295, x: -18.5, y: -9.5},
                {t: 40, r: 0.08726646259971647, x: -12.0, y: -2.0},
                {t: 50, r: 0.08726646259971647, x: -12.0, y: -2.0},
                {t: 60, r: 0.08726646259971647, x: -12.0, y: -2.0},
                {t: 70, r: 0.08726646259971647, x: -12.0, y: -2.0},
                {t: 80, r: 0.17453292519943295, x: -13.0, y: -5.5}]);
            _anim_player[i][j].addChild(_anim_player_7[i][j]);
            _anim_player_8[i][j] = new PIXI.Sprite(player_new2_png_8);
            _anim_player_8[i][j].anchor.x = 0.5;
            _anim_player_8[i][j].anchor.y = 0.5;
            _anim_player_8[i][j].position.x = 19.0;
            _anim_player_8[i][j].position.y = -2.0;
            _anim_player_8[i][j].rotation = -0.1308996938995747;
            _frame_anim_player_8[i][j] = new Animation(_anim_player_8[i][j], 80, [
                {t: 0, r: -0.1308996938995747, x: 19.0, y: -2.0},
                {t: 10, r: -0.1308996938995747, x: 15.0, y: -4.0},
                {t: 20, r: -0.1308996938995747, x: 12.0, y: -3.0},
                {t: 30, r: -0.1308996938995747, x: 13.0, y: -3.5},
                {t: 40, r: -0.1308996938995747, x: 13.5, y: -4.5},
                {t: 50, r: -0.1308996938995747, x: 17.5, y: -6.0},
                {t: 60, r: -0.17453292519943295, x: 20.0, y: -11.5},
                {t: 70, r: -0.08726646259971647, x: 19.0, y: -8.0},
                {t: 80, r: -0.1308996938995747, x: 19.0, y: -2.0}]);
            _anim_player[i][j].addChild(_anim_player_8[i][j]);
            _anim_player_9[i][j] = new PIXI.Sprite(player_new2_png_6);
            _anim_player_9[i][j].anchor.x = 0.5;
            _anim_player_9[i][j].anchor.y = 0.5;
            _anim_player_9[i][j].position.x = 16.5;
            _anim_player_9[i][j].position.y = 12.75;
            _anim_player_9[i][j].rotation = 0.1308996938995747;
            _frame_anim_player_9[i][j] = new Animation(_anim_player_9[i][j], 80, [
                {t: 0, r: 0.1308996938995747, x: 16.5, y: 12.75},
                {t: 10, r: 0.1308996938995747, x: 15.25, y: 15.25},
                {t: 20, r: 0.04363323129985824, x: 16.25, y: 15.0},
                {t: 30, r: 0.08726646259971647, x: 16.5, y: 11.0},
                {t: 40, r: 0.2617993877991494, x: 17.0, y: 0.0},
                {t: 50, r: 0.2617993877991494, x: 17.0, y: 0.0},
                {t: 60, r: 0.2617993877991494, x: 17.0, y: 0.0},
                {t: 70, r: 0.2617993877991494, x: 18.0, y: 0.0},
                {t: 80, r: 0.1308996938995747, x: 17.5, y: 19.0}]);
            _anim_player[i][j].addChild(_anim_player_9[i][j]);
            _anim_player_10[i][j] = new PIXI.Sprite(player_new2_png_7);
            _anim_player_10[i][j].anchor.x = 0.5;
            _anim_player_10[i][j].anchor.y = 0.5;
            _anim_player_10[i][j].position.x = -12.0;
            _anim_player_10[i][j].position.y = 2.0;
            _anim_player_10[i][j].rotation = 0.0;
            _frame_anim_player_10[i][j] = new Animation(_anim_player_10[i][j], 80, [
                {t: 0, r: 0.0, x: -12.0, y: 2.0},
                {t: 10, r: 0.0, x: -12.0, y: 2.0},
                {t: 20, r: 0.0, x: -12.0, y: 2.0},
                {t: 30, r: 0.0, x: -12.0, y: 2.0},
                {t: 40, r: -0.2181661564992912, x: -12.0, y: 13.5},
                {t: 50, r: -0.04363323129985824, x: -13.75, y: 17.0},
                {t: 60, r: -0.04363323129985824, x: -17.25, y: 17.0},
                {t: 70, r: 0.04363323129985824, x: -18.0, y: 8.0},
                {t: 80, r: 0.0, x: -12.0, y: 2.0}]);
            _anim_player[i][j].addChild(_anim_player_10[i][j]);
            _anim_player_11[i][j] = new PIXI.Sprite(polera);
            _anim_player_11[i][j].anchor.x = 0.5;
            _anim_player_11[i][j].anchor.y = 0.5;
            _anim_player_11[i][j].position.x = 0.0;
            _anim_player_11[i][j].position.y = 0.0;
            _anim_player_11[i][j].rotation = 0.0;
            _anim_player_11_1[i][j] = new PIXI.Sprite(player_new2_png_16);
            _anim_player_11_1[i][j].anchor.x = 0.5;
            _anim_player_11_1[i][j].anchor.y = 0.39400000267778523;
            _anim_player_11_1[i][j].position.x = 0.0;
            _anim_player_11_1[i][j].position.y = 0.0;
            _anim_player_11_1[i][j].rotation = 3.141592653589793;
            _frame_anim_player_11_1[i][j] = new Animation(_anim_player_11_1[i][j], 80, [
                {t: 0, r: 3.141592653589793, x: 0.0, y: 0.0},
                {t: 10, r: 3.141592653589793, x: 1.0, y: -3.0},
                {t: 20, r: 3.141592653589793, x: 0.0, y: -7.0},
                {t: 30, r: 3.141592653589793, x: 0.0, y: 1.0},
                {t: 40, r: 3.141592653589793, x: 0.0, y: 0.0},
                {t: 50, r: 3.141592653589793, x: 1.0, y: -3.0},
                {t: 60, r: 3.141592653589793, x: 0.0, y: -6.5},
                {t: 70, r: 3.141592653589793, x: 0.5, y: 3.0},
                {t: 80, r: 3.141592653589793, x: 0.0, y: 0.0}]);
            _anim_player_11[i][j].addChild(_anim_player_11_1[i][j]);
            _frame_anim_player_11[i][j] = new Animation(_anim_player_11[i][j], 80, [
                {t: 0, r: 0.0, x: 0.0, y: 0.0},
                {t: 10, r: -0.08726646259971647, x: 0.0, y: 0.0},
                {t: 20, r: -0.17453292519943295, x: 0.0, y: 0.0},
                {t: 30, r: 0.0, x: 0.0, y: 0.0},
                {t: 40, r: 0.0, x: 0.0, y: 0.0},
                {t: 50, r: 0.08726646259971647, x: 0.0, y: 0.0},
                {t: 60, r: 0.17453292519943295, x: 0.0, y: 0.0},
                {t: 70, r: 0.0, x: 0.0, y: 0.0},
                {t: 80, r: 0.0, x: 0.0, y: 0.0}]);
            _anim_player[i][j].addChild(_anim_player_11[i][j]);
            _anim_player_12[i][j] = new PIXI.Sprite(player_new2_png_10);
            _anim_player_12[i][j].anchor.x = 0.8398999914134038;
            _anim_player_12[i][j].anchor.y = 0.9479999886825681;
            _anim_player_12[i][j].position.x = -40.0;
            _anim_player_12[i][j].position.y = 11.5;
            _anim_player_12[i][j].rotation = -2.443460952792061;
            _frame_anim_player_12[i][j] = new Animation(_anim_player_12[i][j], 80, [
                {t: 0, r: -2.443460952792061, x: -40.0, y: 11.5},
                {t: 10, r: -2.356194490192345, x: -37.0, y: 18.5},
                {t: 20, r: -2.443460952792061, x: -32.0, y: 20.5},
                {t: 30, r: -0.30543261909900765, x: -36.0, y: 9.5},
                {t: 40, r: 1.0035643198967394, x: -47.0, y: -13.5},
                {t: 50, r: 1.2653637076958888, x: -42.0, y: -21.5},
                {t: 60, r: 0.9599310885968813, x: -48.0, y: -2.5},
                {t: 70, r: 0.6981317007977318, x: -50.0, y: 2.5},
                {t: 80, r: -2.443460952792061, x: -40.0, y: 11.5}]);
            _anim_player[i][j].addChild(_anim_player_12[i][j]);
            _anim_player_13[i][j] = new PIXI.Sprite(player_new2_png_9);
            _anim_player_13[i][j].anchor.x = 0.19660000766452868;
            _anim_player_13[i][j].anchor.y = 0.9482999886749894;
            _anim_player_13[i][j].position.x = 41.0;
            _anim_player_13[i][j].position.y = -15.5;
            _anim_player_13[i][j].rotation = -0.9162978572970231;
            _frame_anim_player_13[i][j] = new Animation(_anim_player_13[i][j], 80, [
                {t: 0, r: -0.9162978572970231, x: 41.0, y: -15.5},
                {t: 10, r: -1.1780972450961724, x: 39.5, y: -21.0},
                {t: 20, r: -0.7417649320975901, x: 44.0, y: -15.5},
                {t: 30, r: 1.6144295580947547, x: 40.0, y: -12.5},
                {t: 40, r: 2.2689280275926285, x: 44.0, y: 12.5},
                {t: 50, r: 2.356194490192345, x: 36.0, y: 20.5},
                {t: 60, r: 2.1816615649929116, x: 36.0, y: 11.5},
                {t: 70, r: 1.1344640137963142, x: 36.0, y: 7.5},
                {t: 80, r: -0.9162978572970231, x: 41.0, y: -15.5}]);
            _anim_player[i][j].addChild(_anim_player_13[i][j]);
            _anim_player_14[i][j] = new PIXI.Sprite(player_new2_png_14);
            _anim_player_14[i][j].anchor.x = 0.09890001013263827;
            _anim_player_14[i][j].anchor.y = 0.5;
            _anim_player_14[i][j].position.x = -31.0;
            _anim_player_14[i][j].position.y = 7.5;
            _anim_player_14[i][j].rotation = -3.70882466048795;
            _frame_anim_player_14[i][j] = new Animation(_anim_player_14[i][j], 80, [
                {t: 0, r: -3.70882466048795, x: -31.0, y: 7.5},
                {t: 10, r: -3.9706240482870996, x: -29.0, y: 12.5},
                {t: 20, r: -4.232423436086249, x: -26.0, y: 12.5},
                {t: 30, r: -3.3597588100890845, x: -33.0, y: 2.5},
                {t: 40, r: -2.9234264970905017, x: -33.0, y: -8.5},
                {t: 50, r: -2.5743606466916362, x: -32.0, y: -11.5},
                {t: 60, r: -2.748893571891069, x: -34.0, y: -2.5},
                {t: 70, r: -3.1852258848896517, x: -37.0, y: -2.5},
                {t: 80, r: -3.70882466048795, x: -31.0, y: 7.5}]);
            _anim_player[i][j].addChild(_anim_player_14[i][j]);
            _anim_player_15[i][j] = new PIXI.Sprite(player_new2_png_15);
            _anim_player_15[i][j].anchor.x = 1.0075999871769454;
            _anim_player_15[i][j].anchor.y = 0.5;
            _anim_player_15[i][j].position.x = 33.5;
            _anim_player_15[i][j].position.y = -14.5;
            _anim_player_15[i][j].rotation = -4.101523742186674;
            _frame_anim_player_15[i][j] = new Animation(_anim_player_15[i][j], 80, [
                {t: 0, r: -4.101523742186674, x: 33.5, y: -14.5},
                {t: 10, r: -4.101523742186674, x: 31.5, y: -17.5},
                {t: 20, r: -4.101523742186674, x: 32.5, y: -13.5},
                {t: 30, r: -3.4033920413889422, x: 35.5, y: -8.5},
                {t: 40, r: -2.5307274153917776, x: 34.5, y: 9.5},
                {t: 50, r: -2.2252947962927703, x: 30.5, y: 13.5},
                {t: 60, r: -2.399827721492203, x: 31.5, y: 9.5},
                {t: 70, r: -2.705260340591211, x: 35.5, y: 4.5},
                {t: 80, r: -4.101523742186674, x: 33.5, y: -14.5}]);
            _anim_player[i][j].addChild(_anim_player_15[i][j]);
            _frame_anim_player[i][j] = new Animation(_anim_player[i][j], 80, [
                {t: 0, r: 0.0, x: 0.0, y: -9.0},
                {t: 10, r: 0.0, x: 1.5, y: -10.5},
                {t: 20, r: 0.0, x: 0.0, y: -9.0},
                {t: 30, r: 0.0, x: 0.0, y: -9.0},
                {t: 40, r: 0.0, x: 0.0, y: -9.0},
                {t: 50, r: 0.0, x: 0.0, y: -9.0},
                {t: 60, r: 0.0, x: 0.0, y: -9.0},
                {t: 70, r: 0.0, x: 0.0, y: -9.0},
                {t: 80, r: 0.0, x: 0.0, y: -9.0}]);
        }
    }
}

function setFrame(frame, equipo, idx) {
    _frame_anim_player_1[equipo][idx].setFrame(frame);
    _frame_anim_player_2[equipo][idx].setFrame(frame);
    _frame_anim_player_3[equipo][idx].setFrame(frame);
    _frame_anim_player_4[equipo][idx].setFrame(frame);
    _frame_anim_player_5[equipo][idx].setFrame(frame);
    _frame_anim_player_6[equipo][idx].setFrame(frame);
    _frame_anim_player_7[equipo][idx].setFrame(frame);
    _frame_anim_player_8[equipo][idx].setFrame(frame);
    _frame_anim_player_9[equipo][idx].setFrame(frame);
    _frame_anim_player_10[equipo][idx].setFrame(frame);
    _frame_anim_player_11_1[equipo][idx].setFrame(frame);
    _frame_anim_player_11[equipo][idx].setFrame(frame);
    _frame_anim_player_12[equipo][idx].setFrame(frame);
    _frame_anim_player_13[equipo][idx].setFrame(frame);
    _frame_anim_player_14[equipo][idx].setFrame(frame);
    _frame_anim_player_15[equipo][idx].setFrame(frame);
    _frame_anim_player[equipo][idx].setFrame(frame);
    ;
}
