const audioList = [], isFixed = "fixed" == Global.theme_config.plugins.aplayer.type,
  isMini = "mini" == Global.theme_config.plugins.aplayer.type;
for (const a of Global.theme_config.plugins.aplayer.audios) {
  const b = {name: a.name, artist: a.artist, url: a.url, cover: a.cover, lrc: a.lrc, theme: a.theme};
  audioList.push(b)
}
if (isMini) {
  const c = new APlayer({container: document.getElementById("aplayer"), mini: !0, audio: audioList})
} else if (isFixed) {
  const d = new APlayer({container: document.getElementById("aplayer"), fixed: !0, audio: audioList})
}