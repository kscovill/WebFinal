$ ->
  $.get "/scores", (data) ->
    $.each data, (scoreScreen, score) ->
      $("#users").append $("<li>").text score.user
      $("#times").append $("<li>").text score.time 
  $.get "/yourScores", (data) ->
    $.each data, (scoreScreen, score) ->
      $("#yourTimes").append $("<li>").text score.time 
      
      