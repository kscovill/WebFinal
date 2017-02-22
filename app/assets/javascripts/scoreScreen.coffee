$ ->
  $.get "/users", (data) ->
    $.each data, (scoreScreen, score) ->
      $("#users").append $("<li>").text score.user
      $("#times").append $("<li>").text score.time 
      