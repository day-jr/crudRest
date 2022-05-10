const del = document.getElementById("del");
const add = document.getElementById("add");
const mod = document.getElementById("mod");
const list = document.getElementById("list");
const aluno = document.getElementById("aluno");
const prof = document.getElementById("prof");
const turma = document.getElementById("turma");

const mapChoice = {
    1: "professor/",
    2: "aluno/",
    3: "turma/",

    4: "turma/prof/",
    5: "turma/aluno/"
}


function handleOnClick(choice) {



    switch (choice) {
        case 1:

            prof.style.background = "white"
            aluno.style.background = "black"
            turma.style.background = "black"

            prof.style.color = "black"
            aluno.style.color = "#363636"
            turma.style.color = "#363636"

            break;

        case 2:

            prof.style.background = "black"
            aluno.style.background = "white"
            turma.style.background = "black"

            prof.style.color = "#363636"
            aluno.style.color = "black"
            turma.style.color = "#363636"
            break;

        case 3:

            prof.style.background = "black"
            aluno.style.background = "black"
            turma.style.background = "white"

            prof.style.color = "#363636"
            aluno.style.color = "#363636"
            turma.style.color = "black"
            break;
    }

}

async function showList(choice) {
    const textBox1 = document.getElementById("texto1");
    const textBox2 = document.getElementById("texto2");
    const textBox3 = document.getElementById("texto3");
    const textBox4 = document.getElementById("texto4");

    textBox1.innerHTML = "";
    textBox2.innerHTML = "";
    textBox3.innerHTML = "";
    textBox4.innerHTML = "";

    var response = await fetch("http://localhost:8080/" + mapChoice[choice]);
    var response_parsed = await response.json();
    var length = response_parsed.length;



    if (choice == 3) {////////////TURMA
        for (k = 0; k < length; k++) {
            var entity = response_parsed[k];
            textBox1.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'codigo')">${entity.codigo}</div><br>`
            textBox2.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'turno')">${entity.turno}</div><br>`
            textBox3.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'inicio')">${entity.inicio}</div><br>`
            textBox4.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'duracao')">${entity.duracao}</div><br>`

        }
    } else {//////////////////////ALUNO
        for (k = 0; k < length; k++) {
            var entity = response_parsed[k];
            console.log(entity);
            textBox1.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'id')">${entity.id}</div>`
            textBox2.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'nome')">${entity.nome}</div>`
            textBox3.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'email')">${entity.email}</div>`
            textBox4.innerHTML += `<div onclick="Mod('${choice}','${entity.id}', 'turmas')">+${entity.turmas}</div>`

        }
    }


}

async function Add(choice) {
    switch (choice) {

        case 1://professor

            var CPF = prompt(">>>Professor<<<\nInsira CPF");
            var nome = prompt(">>>Professor<<\nInsira nome");
            var email = prompt(">>>Professor<<\n>Insira email");

            await fetch(
                "http://localhost:8080/professor",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        id: CPF,
                        nome: nome,
                        email: email
                    })
                }
            );

           
            break;




        case 2://Aluno

            var matricula = prompt(">>>Aluno<<<\nInsira Matricula");
            var nome = prompt(">>>Aluno<<<\nInsira Nome");
            var email = prompt(">>>Aluno<<<\nInsira Email");

            await fetch(
                "http://localhost:8080/aluno",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        id: matricula,
                        nome: nome,
                        email: email
                    })
                }
            );

            await fetch(
                "http://localhost:8080/turma/aluno",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        id: matricula,

                    })
                }
            );
            break;



        case 3://Turma

            var codigo = prompt(">>>Turma<<<\nInsira Codigo");
            var turno = prompt(">>>Turma<<<\nInsira Turno");
            var inicio = prompt(">>>Turma<<<\nInsira Horario de Inicio da Aula");
            var duracao = prompt(">>>Turma<<<\nInsira a Duracao da Aula");

            await fetch(
                "http://localhost:8080/turma",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        codigo: parseInt(codigo),
                        turno: turno,
                        inicio: inicio,
                        duracao: duracao
                    })
                }
            );



            break;

    }

    showList(choice);
}

async function Del(choice) {
    switch (choice) {
        case 1://professor

            var id = prompt("Insira o CPF do professor que deseja deletar")
            response = await fetch("http://localhost:8080/professor/" + id);
            var response_parsed = await response.json();
            var text = JSON.stringify(response_parsed);
            var del = confirm("Deseja deletar " + response_parsed.nome + "? CPF(" + response_parsed.id + ")");
            if (del == true) {
                fetch(
                    "http://localhost:8080/" + mapChoice[choice] + id,
                    {
                        method: "DELETE",
                        headers: { "Content-Type": "application/json" }
                    }
                );

                alert("Usuario deletado!");
                showList(choice);
            }
            if (del == false) {
                alert("Operacao cancelada");
                showList(choice);
            }
            break;


        case 2://aluno

            var id = prompt("Insira a matricula do aluno que deseja deletar")
            response = await fetch("http://localhost:8080/aluno/" + id);
            var response_parsed = await response.json();
            var text = JSON.stringify(response_parsed);
            var del = confirm("Deseja deletar " + response_parsed.nome + "? Matricula(" + response_parsed.id + ")");
            if (del == true) {
                fetch(
                    "http://localhost:8080/" + mapChoice[choice] + id,
                    {
                        method: "DELETE",
                        headers: { "Content-Type": "application/json" }
                    }
                );

                alert("Usuario deletado!");
                showList(choice);
            }
            if (del == false) {
                alert("Operacao cancelada");
                showList(choice);
            }
            break;


        case 3://turma

            var id = prompt("Insira o ID do professor que deseja deletar")
            response = await fetch("http://localhost:8080/turma/" + id);
            var response_parsed = await response.json();
            var text = JSON.stringify(response_parsed);
            var del = confirm("Deseja deletar " + response_parsed.id + "? codigo(" + response_parsed.codigo + ")");
            if (del == true) {
                fetch(
                    "http://localhost:8080/" + mapChoice[choice] + id,
                    {
                        method: "DELETE",
                        headers: { "Content-Type": "application/json" }
                    }
                );

                alert("Usuario deletado!");
                showList(choice);
            }
            if (del == false) {
                alert("Operacao cancelada");
                showList(choice);
            }
            break;



    }
}

async function Mod(choice, id, field) {



    if (field == "turmas" && choice == 1) {
        choice = 4;
        var turma = prompt("turma");
        var body = {
            "id": id,
            "cpf": { "id": id },
            "codigo": { "id": turma }
        };
    } else

        if (field == "turmas" && choice == 2) {
            choice = 5;
            var turma = prompt("turma");
            var body = {
                "id": id,
                "matricula": { "id": id },
                "codigo": { "id": turma }
            };
        } else {
            body[field] = prompt("Entre com o(a) " + field);
        }


    await fetch(
        "http://localhost:8080/" + mapChoice[choice] + id,
        {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        }
    );

    if (choice == 4) choice = 1;
    if (choice == 5) choice = 2;
    showList(choice);


}



