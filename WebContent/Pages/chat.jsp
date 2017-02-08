<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Chat</title>
    <style>
    	.ser {
    		color: red;
    	}
    </style>
  </head>
  <body>
        <form>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" id="name"/></td>
                </tr>
                <tr>
                    <td>Message:</td>
                    <td><input type="text" name="msg" id="msg"/></td>
                </tr>
            </table>
            <input type="button" value="Shout" onclick="postMessage();"/>
        </form>
        <div id="message">
            <% if(application.getAttribute("message")!=null){%>
            <%=application.getAttribute("message")%>
            <%}%>
        </div>
        
        <script>
        var waitingMsg = false;
        function getMessage(){ 
            if(!waitingMsg){
                waitingMsg = true;
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function(){
                    if(xhr.readyState == 4){
                        if(xhr.status == 200){
                            var elem = document.getElementById('message');
                            elem.innerHTML = xhr.responseText + elem.innerHTML;
                        }
                        waitingMsg = false;
                    }
                };
                xhr.open('get', 'http://localhost:8080/BANK-APP/ChatServlet', true);
                xhr.send();
            }
        }
        setInterval(getMessage, 1000);
        
        function postMessage(){ //alert(989);
            var xhr = new XMLHttpRequest();
            xhr.open('post','http://localhost:8080/BANK-APP/ChatServlet', false);
            xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            var name = escape(document.getElementById('name').value);
            var msg = escape(document.getElementById('msg').value);
            document.getElementById('msg').value='';
            //xhr.send('name='+name+'&msg='+msg+'&t='+(new Date()).toLocaleTimeString());
            xhr.send('name='+name+'&msg='+msg+'&t='+(new Date()).getHours()+":"+(new Date()).getMinutes());

        }
        </script>
  </body>
</html>