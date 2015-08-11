# Gradle Cloudinit Plugin

This plugin assists with the creation and installation of a cloudinit configuration.

## Example:

```
cloudConfig {
    packages = ['ufw', 'ssh', 'fail2ban']
    
    timezone = 'Australia/Melbourne'
    
    users = [
        [name: 'fortuna', ssh-authorized-keys: System.getenv('SSH_KEY'), groups: 'sudo', shell: '/bin/bash']
    ]
    
    runcmd = [
        // configure ssh to deny root access and password login..
        'sed -i -e '/^PermitRootLogin/s/^.*$/PermitRootLogin no/' /etc/ssh/sshd_config',
        'sed -i -e '/^PasswordAuthentication/s/^.*$/PasswordAuthentication no/' /etc/ssh/sshd_config',
        'restart ssh',
        
        // enable firewall
        'ufw --force enable'
    ]
}
```