const buildify = require('buildify');
const fileset = require('fileset');

const source = './build/static';
const target = '../target/classes/static/';
const fileName = 'main';

['js', 'css'].forEach(fileType =>
    fileset(`${source}/${fileType}/*.${fileType}`, (err, files) => {
        if (err) {
            console.log('Fileset encountered error', err);
            throw new Error(err);
        }

        console.log("Bundling webpack's chunks\n", files);

        buildify()
            .concat(files)
            .save(`${target}/${fileType}/${fileName}.${fileType}`);
    }));
