$(document).ready(function () {
    getMovieList();
});
function getMovieList() {
    getRequest(
        '/movie/all',
        function (res) {
            renderMovieList(res.content);
            console.log("成功！");
        },
        function (error) {
            console.log("失败！");
            alert(error);
        }
    )
}
function renderMovieList(list) {
    $("#starsIF_images").empty();
    var movieListDomStr="";
    list.forEach(function (movieItem) {
        movieListDomStr+="<img src='"+movieItem.posterUrl+"' longdesc='http://localhost:8080/user/movieDetail?id="+movieItem.id+"' width='280' height='300' alt='hhh'/>"
    });
    list.forEach(function (movieItem) {
        movieListDomStr+="<img src='"+movieItem.posterUrl+"' longdesc='http://localhost:8080/user/movieDetail?id="+movieItem.id+"' width='280' height='300' alt='hhh'/>"
    });
    movieListDomStr+="<img src='"+list[0].posterUrl+"' longdesc='http://localhost:8080/user/movieDetail?id="+list[0].id+"' width='280' height='300' alt='hhh'/>"
    $("#starsIF_images").append(movieListDomStr);
}