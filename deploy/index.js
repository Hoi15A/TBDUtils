const ftp = require("basic-ftp")
const fs = require("fs")

const nameRegex = /TBDUtils.+\.jar/

async function main() {
    const client = new ftp.Client()

    const newPluginFile = fs.readdirSync("./").find(fileName => nameRegex.test(fileName))
    if (!newPluginFile) throw Error("Plugin jar not found!")

    console.log(`Found new plugin file: ${newPluginFile}`)

    await client.access({
        host: process.env.FTP_HOST,
        user: process.env.FTP_USER,
        password: process.env.FTP_PASSWORD
    })

    await client.cd("plugins");
    const oldPluginFile = (await client.list()).find(file => file.isFile && nameRegex.test(file.name))
    console.log(`Found old plugin file: ${oldPluginFile}`)

    await client.uploadFrom(newPluginFile, newPluginFile)
    console.log("Uploaded new plugin")
    await client.remove(oldPluginFile.name)
    console.log("Deleted old plugin")

    client.close()
}

main();
