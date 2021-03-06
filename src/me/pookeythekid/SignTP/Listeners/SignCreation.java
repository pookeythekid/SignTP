package me.pookeythekid.SignTP.Listeners;


import me.pookeythekid.SignTP.Main;
import me.pookeythekid.SignTP.Permissions.Permissions;
import me.pookeythekid.SignTP.api.Colors;
import me.pookeythekid.SignTP.api.Msgs;
import me.pookeythekid.SignTP.api.Signs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreation implements Listener {

	public Main M;

	private Signs Signs;

	private Permissions Permissions;

	private Colors Colors;

	private Msgs Msgs;

	public SignCreation(Main plugin) {

		M = plugin;

		Signs = new Signs(plugin);

		Permissions = new Permissions();

		Colors = new Colors();

		Msgs = new Msgs(plugin);

	}


	@EventHandler
	public void onSignCreate(SignChangeEvent e) {

		Player p = e.getPlayer();

		if (Signs.signIsValid(e)) {

			if (p.hasPermission(Permissions.createSign)) {

				Signs.markSignUsable(e);

				if (!M.economyIsOn && Signs.signHasPrice(e)) {

					if (M.getConfig().getBoolean("useEconomy"))
						p.sendMessage(Msgs.EcoOffError());

					else
						p.sendMessage(Msgs.EcoOffWarn());

				}

				if (M.economyIsOn && Signs.signHasPrice(e)) {

					if (Signs.signPriceInvalid(e)) {

						Signs.markPriceInvalid(e);

						p.sendMessage(Msgs.InvalidPrice());

					}

					else

						Signs.formatPrice(e);

				}

			} else if (!p.hasPermission(Permissions.createSign))

				e.setLine(0, ChatColor.BLACK + Colors.noChatColors(M.getConfig().getString("signPrefix")));

		} else if (Signs.signHasPrefix(e) && p.hasPermission(Permissions.createSign))

			if (!Signs.markSignInvalid(e, true))

				p.sendMessage(Msgs.SpecifyWarp());

	}




}
