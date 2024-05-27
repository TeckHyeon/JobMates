<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>회원가입 유형 선택</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .wrap {
            width: 95%;
            margin: 0 auto;
        }

        main {
            background: #fff;
            padding-bottom: 50px;
        }

        .category_box .box {
            max-width: 1000px;
            width: 90%;
            margin: 0 auto;
            padding-top: 30px;
        }

        .category {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }

        .category li {
            width: calc(50% - 20px); 
            margin: 10px; 
            text-align: center;
        }

        .category li button {
            border: none;
            background: none;
            padding: 0;
            cursor: pointer;
        }

        .category li img {
            width: 200px; 
            height: auto;
            border-radius: 10px;
            transition: transform 0.3s ease;
            padding : 0;
           
        }

        .category li img:hover {
            transform: scale(1.05);
        }

     
        h2 {
            color: black; 
            text-align: center;
            margin-bottom: 20px; 
        }


        li{
        	list-style: none;
        }
        
    </style>
</head>

<body>
    <div class="wrap">
        <h2>회원가입을 환영합니다</h2>
        <section class="category_box">
            <div class="box">
                <ul class="category">
                    <li>
                        <button type="button" class="btm_image" onclick="location.href='/personjoin'">
                            <img src="/images/user1.png" alt="개인 회원가입">
                        </button>
                    </li>
                    <li>
                        <button type="button" class="btm_image" onclick="location.href='/companyjoin'">
                            <img src="/images/person.png" alt="기업 회원가입">
                        </button>
                    </li>
                </ul>
            </div>
        </section>
    </div>
</body>

</html>