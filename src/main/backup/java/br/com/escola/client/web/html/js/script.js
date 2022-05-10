const del = document.getElementById("del");
const add = document.getElementById("add");
const mod = document.getElementById("mod");
const list = document.getElementById("list");
const aluno = document.getElementById("aluno");
const prof = document.getElementById("prof");
const turma = document.getElementById("turma");



function handleOnClick(choice) {



    switch (choice) {
        case 1:
            list.href = "/prof/get";
            add.href = "/prof/post";
            mod.href = "/prof/put";
            del.href = "/prof/delete";

            
            prof.style.background="white"
            aluno.style.background="black"
            turma.style.background="black"

            prof.style.color="black"
            aluno.style.color="#363636"
            turma.style.color="#363636"

            break;

        case 2:
            list.href = "/aluno/get";
            add.href = "/aluno/post";
            mod.href = "/aluno/put";
            del.href = "/aluno/delete";

            prof.style.background="black"
            aluno.style.background="white"
            turma.style.background="black"

            prof.style.color="#363636"
            aluno.style.color="black"
            turma.style.color="#363636"
            break;

        case 3:
            list.href = "/turma/get";
            add.href = "/turma/post";
            mod.href = "/turma/put";
            del.href = "/turma/delete";

            prof.style.background="black"
            aluno.style.background="black"
            turma.style.background="white"

            prof.style.color="#363636"
            aluno.style.color="#363636"
            turma.style.color="black"
            break;

    }

}

