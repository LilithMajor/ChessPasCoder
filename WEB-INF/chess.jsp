<!DOCTYPE html>
<html>
    <head>
	<meta charset="utf-8" />
        <title>Chess</title>
        <link type="text/css" rel="stylesheet" href="./css/chessboard-0.3.0.min.css" />
    </head>
	<body>
    <div id="gameBoard" style="width: 400px"></div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/chess.js/0.10.2/chess.min.js"></script>
    <script src="./js/chessboard-0.3.0.min.js"></script>
	<script
  src="https://code.jquery.com/jquery-3.2.1.js"
  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
  crossorigin="anonymous"></script>
	</body>
	<script>
	$(document).ready(function(){
		var initGame = function () {
			var cfg = {
				draggable: true,
				position: 'start',
				onDrop: handleMove,
			};
			board = new ChessBoard('gameBoard', cfg);	
			game = new Chess();
		}
		var handleMove = function(source, target) {
			var move = game.move({from: source, to: target});
		}
		initGame();		
	});
	
	</script>
</html>