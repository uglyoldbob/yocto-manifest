**Initialize the yocto sources**
repo init -u https://github.com/uglyoldbob/yocto-manifest.git -m whatever.xml -b master
repo sync

**Obtain or create keys**

Obtain or create keys for encrypting swu update files.

**Creating keys**

In the build directory of your yocto setup, run this sequence of commands.
```
mkdir ./conf/signing
dd if=/dev/urandom bs=16 count=1 | base64 > ./conf/signing/pass
openssl genrsa -aes256 -out ./conf/signing/priv.pem -passout file:./conf/signing/pass
openssl rsa -in ./conf/signing/priv.pem -out ./conf/signing/public.pem -outform PEM -pubout -passin file:./conf/signing/pass
echo key=$(openssl rand -hex 64) > ./conf/keys.conf
echo iv =$(openssl rand -hex 32) >> ./conf/keys.conf
```

**Obtaining keys**

Obtain the public key, private key, and pass file used for signing swu images. 

Place them in 

* `build/conf/signing/public.pem`
* `build/conf/signing/priv.pem`
* `build/conf/signing/pass`

Obtain the encryption keys used for encrypting the swu images. Place the file in `build/conf/keys.conf`.

**Add key data to local configuration**

Run these commands to add the key configuration to your local build
```
echo SWUPDATE_PASSWORD_FILE=\"$(pwd)/conf/signing/pass\" >> ./conf/local.conf
echo SWUPDATE_AES_FILE = \"$(pwd)/conf/keys.conf\" >> ./conf/local.conf
echo SWUPDATE_PRIVATE_KEY = \"$(pwd)/conf/signing/priv.pem\" >> ./conf/local.conf
```
