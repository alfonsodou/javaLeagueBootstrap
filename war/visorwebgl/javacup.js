function getString(bin) {
    var val, s = "";
    do {
        val = bin.read('uint8');
        s = s + String.fromCharCode(val);
    } while (val !== 0)
    return s;
}

function getShort(bin) {
    val = bin.read('int16');
    return val;
}

_flagAmp = [1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768];
function getFlag(idx, byte) {
    return byte % _flagAmp[idx + 1] / _flagAmp[idx] >= 1;
}

function getByte(bin) {
    return bin.read('int8');
}

function getuByte(bin) {
    return bin.read('uint8');
}

function miniName(name) {
    name = name.replace(/\s+/, " ");
    ss = name.split(" ");
    if (ss.length === 1) {
        if (name.length > 3) {
            name = ss[0].substring(0, 3);
        }
    } else {
        name = "";
        for (var i = 0; i < ss.length; i++) {
            s = ss[i];
            if (name.length < 3) {
                name = name + s.charAt(0);
            }
        }
    }
    return name.toUpperCase();
}

function getTacticDetail(bin) {
    detail = {
        name: getString(bin),
        miniName: "",
        coach: getString(bin),
        country: getString(bin),
        style1: getByte(bin),
        style2: getByte(bin),
        colorGoalKeeper: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorGoalKeeper2: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorShirt: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorShirt2: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorShirtLine: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorShirtLine2: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorShorts: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorShorts2: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorSocks: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        colorSocks2: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
        player: [],
        goalKeeper: -1
    };
    detail.miniName = miniName(detail.name);
    for (i = 0; i < 11; i++) {
        detail.player.push({number: getByte(bin), name: getString(bin),
            colorSkin: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)},
            colorHair: {r: getuByte(bin), g: getuByte(bin), b: getuByte(bin)}});
    }
    detail.goalKeeper = getuByte(bin);
    return detail;
}

function getPosition(bin) {
    return {
        x: getShort(bin) / 32,
        y: getShort(bin) / 32
    };
}

out = true;
idxTime = 0;
function getIteracion(bin) {
    iter = {
        flags: getShort(bin),
        alturaBalon: getShort(bin),
        posecionBalonLocal: getShort(bin),
        posVisibleBalon: getPosition(bin),
        posBalon: getPosition(bin),
        golesLocal: getByte(bin),
        golesVisita: getByte(bin),
        posLocal: [],
        posVisita: [],
        idxTime: 0
    };
    if (out && getFlag(4, iter.flags)) {
        out = false;
    }
    if (getFlag(5, iter.flags) || getFlag(0, iter.flags)) {
        out = true;
    }
    iter.idxTime = idxTime;
    if (!out)
        idxTime++;
    for (i = 0; i < 11; i++) {
        iter.posLocal.push(getPosition(bin));
    }
    for (i = 0; i < 11; i++) {
        iter.posVisita.push(getPosition(bin));
    }
    return iter;
}

function getPartido(bin) {
    partido = {
        loaded: false,
        detailVisita: getTacticDetail(bin), //OJO ESTA ALREVEZ
        detailLocal: getTacticDetail(bin),
        iteracion: []
    };
    iteraciones = getShort(bin);
    iterLoaded = 0;
    return partido;
}

function getIter() {
    if (iteraciones > iterLoaded) {
        iter = getIteracion(binary);
        if (iter !== null) {
            iterLoaded++;
            partido.iteracion.push(iter);
        }
        return true;
    } else
        return false;
}

partido = null;
idx = 0;
iteraciones = 0;
iterLoaded = 0;
binary = null;
function loadPartido(url) {
    partido = null;
    jBinary.load(url, function(err, bin) {
        binary = bin;
        partido = getPartido(binary);
    });
}

//Obtiene el angulo de movimiento de un jugador, como un promedio de los angulos anteriores y siguientes
function getAngLocal(partido, index, delta, player) {

    sum = 0;
    count = 0;
    ang = undefined;
    for (i = delta; i > 0; i--) {
        if (index + i < iteraciones) {
            ang0 = ang;
            if (partido.iteracion[index + i].posLocal[player].y === partido.iteracion[index - 1 + i].posLocal[player].y &&
                    partido.iteracion[index + i].posLocal[player].x === partido.iteracion[index - 1 + i].posLocal[player].x)
                ang = Math.atan2(
                        -partido.iteracion[index - 1 + i].posLocal[player].y,
                        -partido.iteracion[index - 1 + i].posLocal[player].x);
            else
                ang = Math.atan2(
                        partido.iteracion[index + i].posLocal[player].y - partido.iteracion[index - 1 + i].posLocal[player].y,
                        partido.iteracion[index + i].posLocal[player].x - partido.iteracion[index - 1 + i].posLocal[player].x);
            if (ang0 !== undefined && Math.abs(ang - ang0) > Math.PI) {
                if (ang0 < ang)
                    ang = ang - Math.PI * 2;
                else
                    ang = ang + Math.PI * 2;
            }
            sum = sum + ang;
            count++;
        }
    }
    for (i = 1; i < delta; i++) {
        if (index - i - 1 > 0) {
            ang0 = ang;
            if (partido.iteracion[index - i].posLocal[player].y === partido.iteracion[index - 1 - i].posLocal[player].y &&
                    partido.iteracion[index - i].posLocal[player].x === partido.iteracion[index - 1 - i].posLocal[player].x)
                ang = Math.atan2(
                        -partido.iteracion[index - i - 1].posLocal[player].y,
                        -partido.iteracion[index - i - 1].posLocal[player].x);
            else
                ang = Math.atan2(
                        partido.iteracion[index - i ].posLocal[player].y - partido.iteracion[index - i - 1].posLocal[player].y,
                        partido.iteracion[index - i ].posLocal[player].x - partido.iteracion[index - i - 1].posLocal[player].x);
            if (ang0 !== undefined && Math.abs(ang - ang0) > Math.PI) {
                if (ang0 < ang)
                    ang = ang - Math.PI * 2;
                else
                    ang = ang + Math.PI * 2;
            }
            sum = sum + ang;
            count++;
        }
    }

    return sum / count;
}

//Obtiene el angulo de movimiento de un jugador, como un promedio de los angulos anteriores y siguientes
function getAngVisita(partido, index, delta, player) {

    sum = 0;
    count = 0;
    ang = undefined;
    for (i = delta; i > 0; i--) {
        if (index + i < iteraciones) {
            ang0 = ang;
            if (partido.iteracion[index + i].posVisita[player].y === partido.iteracion[index - 1 + i].posVisita[player].y &&
                    partido.iteracion[index + i].posVisita[player].x === partido.iteracion[index - 1 + i].posVisita[player].x)
                ang = Math.atan2(
                        -partido.iteracion[index - 1 + i].posVisita[player].y,
                        -partido.iteracion[index - 1 + i].posVisita[player].x);
            else
                ang = Math.atan2(
                        partido.iteracion[index + i].posVisita[player].y - partido.iteracion[index - 1 + i].posVisita[player].y,
                        partido.iteracion[index + i].posVisita[player].x - partido.iteracion[index - 1 + i].posVisita[player].x);
            if (ang0 !== undefined && Math.abs(ang - ang0) > Math.PI) {
                if (ang0 < ang)
                    ang = ang - Math.PI * 2;
                else
                    ang = ang + Math.PI * 2;
            }
            sum = sum + ang;
            count++;
        }
    }
    for (i = 1; i < delta; i++) {
        if (index - i - 1 > 0) {
            ang0 = ang;
            if (partido.iteracion[index - i].posVisita[player].y === partido.iteracion[index - 1 - i].posVisita[player].y &&
                    partido.iteracion[index - i].posVisita[player].x === partido.iteracion[index - 1 - i].posVisita[player].x)
                ang = Math.atan2(
                        -partido.iteracion[index - i - 1].posVisita[player].y,
                        -partido.iteracion[index - i - 1].posVisita[player].x);
            else
                ang = Math.atan2(
                        partido.iteracion[index - i ].posVisita[player].y - partido.iteracion[index - i - 1].posVisita[player].y,
                        partido.iteracion[index - i ].posVisita[player].x - partido.iteracion[index - i - 1].posVisita[player].x);
            if (ang0 !== undefined && Math.abs(ang - ang0) > Math.PI) {
                if (ang0 < ang)
                    ang = ang - Math.PI * 2;
                else
                    ang = ang + Math.PI * 2;
            }
            sum = sum + ang;
            count++;
        }
    }
    return sum / count;
}

function mesclarColor(c1, c2, p1) {
    p2 = 1 - p1;
    return {r: c1.r * p1 + c2.r * p2, g: c1.g * p1 + c2.g * p2, b: c1.b * p1 + c2.b * p2};
}

function useAlternativeColors(local, visita) {
    var cl1, cv1, cv2;
    cl1 = mesclarColor(local.colorShirt, local.colorShirtLine, getP1(local.style1));
    cv1 = mesclarColor(visita.colorShirt, visita.colorShirtLine, getP1(visita.style1));
    cv2 = mesclarColor(visita.colorShirt2, visita.colorShirtLine2, getP1(visita.style2));
    d1 = distancia(cl1, cv1);
    d2 = distancia(cl1, cv2);
    return d1 < 150 && d2 > d1;
}

function distancia(c1, c2) {
    return Math.sqrt((c1.r - c2.r) * (c1.r - c2.r) + (c1.g - c2.g) * (c1.g - c2.g) + (c1.b - c2.b) * (c1.b - c2.b));
}

function getP1(st) {
    if (st === 4 || st === 5)
        return .5;
    if (st === 1 || st === 2 || st === 3)
        return .8;
    if (st === 6)
        return 1;
    return 0;
}



